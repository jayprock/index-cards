package com.bitbus.indexcards.user.pw;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bitbus.indexcards.session.AuthenticationException;

@Service
public class PasswordService {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private List<PasswordPolicy> passwordPolicies;

    public void assertPasswordInCompliance(String rawTextPassword) throws PasswordPolicyException {
        for (PasswordPolicy passwordPolicy : passwordPolicies) {
            passwordPolicy.assertCompliance(rawTextPassword);
        }
    }

    public String encodePassword(String rawTextPassword) {
        return PASSWORD_ENCODER.encode(rawTextPassword);
    }

    public boolean isPasswordCorrect(String rawTextPassword, String encodedPassword) throws AuthenticationException {
        return PASSWORD_ENCODER.matches(rawTextPassword, encodedPassword);
    }


}
