package com.bitbus.indexcards.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitbus.indexcards.user.pw.PasswordPolicyException;
import com.bitbus.indexcards.user.pw.PasswordService;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordService passwordService;


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
}
