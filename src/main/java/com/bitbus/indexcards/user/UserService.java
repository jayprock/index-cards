package com.bitbus.indexcards.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bitbus.indexcards.user.pw.PasswordPolicy;
import com.bitbus.indexcards.user.pw.PasswordPolicyException;

@Service
public class UserService {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private List<PasswordPolicy> passwordPolicies;

    @Autowired
    private UserRepository userRepo;


    public User create(CreateUserDto dto) throws PasswordPolicyException {
        assertPasswordInCompliance(dto.getPassword());
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(PASSWORD_ENCODER.encode(dto.getPassword()));
        return userRepo.save(user);
    }

    private void assertPasswordInCompliance(String rawTextPassword) throws PasswordPolicyException {
        for (PasswordPolicy passwordPolicy : passwordPolicies) {
            passwordPolicy.assertCompliance(rawTextPassword);
        }
    }

    public boolean isUsernameAvailable(String username) {
        return !userRepo.findOptionalByUsername(username).isPresent();
    }
}
