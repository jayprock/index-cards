package com.bitbus.indexcards.user;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bitbus.indexcards.email.EmailService;
import com.bitbus.indexcards.error.Exceptions;
import com.bitbus.indexcards.session.AuthenticationException;
import com.bitbus.indexcards.user.pw.InvalidPasswordResetException;
import com.bitbus.indexcards.user.pw.PasswordPolicyException;
import com.bitbus.indexcards.user.pw.PasswordService;
import com.bitbus.indexcards.util.UrlUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AuthenticationManager authManager;


    public User create(CreateUserDto dto) throws PasswordPolicyException {
        passwordService.assertPasswordInCompliance(dto.getPassword());
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordService.encode(dto.getPassword()));
        return userRepo.save(user);
    }

    public Authentication loginUser(String login, String rawPassword) throws AuthenticationException {
        try {
            Authentication authentication =
                    authManager.authenticate(new UsernamePasswordAuthenticationToken(login, rawPassword));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (org.springframework.security.core.AuthenticationException ex) {
            throw new AuthenticationException();
        }
    }

    public boolean isUsernameAvailable(String username) {
        return !userRepo.findOptionalByUsername(username).isPresent();
    }

    public boolean isEmailAvailable(String email) {
        return !findOptionalByEmail(email).isPresent();
    }

    public User findById(long userId) throws UserNotFoundException {
        return userRepo.findById(userId).orElseThrow(Exceptions::userNotFoundException);
    }

    public User findByEmail(String email) throws EmailDoesNotExistException {
        return findOptionalByEmail(email).orElseThrow(Exceptions::emailDoesNotExistException);
    }

    private Optional<User> findOptionalByEmail(String email) {
        return userRepo.findOptionalByEmail(email);
    }

    public User findByLogin(String login) throws UserNotFoundException {
        return userRepo.findOptionalByUsernameOrEmail(login, login).orElseThrow(Exceptions::userNotFoundException);
    }

    @Transactional
    public void sendPasswordResetEmail(String email, String baseUrl) throws EmailDoesNotExistException {
        User user = findByEmail(email);
        String token = UUID.randomUUID().toString();
        passwordService.createNewPasswordResetToken(user, token);

        StringBuilder message = new StringBuilder();
        message.append("Please use the following link to reset your password. This link is only valid for 10 minutes.");
        message.append(System.lineSeparator());
        message.append(System.lineSeparator());
        String url = UrlUtil.appendToUrl(baseUrl, "password-reset", token);
        message.append(url);
        message.append("?id=");
        message.append(user.getId());
        emailService.sendTextOnly(email, "Reset your password", message.toString());
    }

    @Transactional
    public void resetUnauthenticatedUserPassword(long userId, String token, String newPassword)
            throws InvalidPasswordResetException, UserNotFoundException {
        User user = findById(userId);
        passwordService.assertPasswordResetTokenValid(user, token);
        String newPasswordHash = passwordService.encode(newPassword);
        user.setPassword(newPasswordHash);
    }

}
