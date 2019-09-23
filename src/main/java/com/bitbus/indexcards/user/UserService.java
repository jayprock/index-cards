package com.bitbus.indexcards.user;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitbus.indexcards.email.EmailService;
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


    public User create(CreateUserDto dto) throws PasswordPolicyException {
        passwordService.assertPasswordInCompliance(dto.getPassword());
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordService.encodePassword(dto.getPassword()));
        return userRepo.save(user);
    }

    public boolean isUsernameAvailable(String username) {
        return !userRepo.findOptionalByUsername(username).isPresent();
    }

    public boolean isEmailAvailable(String email) {
        return !userRepo.findOptionalByEmail(email).isPresent();
    }

    public User findByLogin(String login) throws UserNotFoundException {
        return userRepo.findOptionalByUsernameOrEmail(login, login).orElseThrow(() -> new UserNotFoundException());
    }

    @Transactional
    public void sendPasswordResetEmail(String email, String baseUrl) throws EmailDoesNotExistException {
        User user = userRepo.findOptionalByEmail(email).orElseThrow(() -> new EmailDoesNotExistException());
        String token = UUID.randomUUID().toString();

        passwordService.deleteExistingPasswordResetTokens(user);
        passwordService.savePasswordResetToken(token, user);

        StringBuilder message = new StringBuilder();
        message.append("Please use the following link to reset your password. This link is only valid for 10 minutes.");
        message.append(System.lineSeparator());
        message.append(System.lineSeparator());
        String url = UrlUtil.appendToUrl(baseUrl, "password-reset", token);
        message.append(url);
        emailService.sendTextOnly(email, "Reset your password", message.toString());
    }

}
