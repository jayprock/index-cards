package com.bitbus.indexcards.user.pw;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bitbus.indexcards.session.AuthenticationException;
import com.bitbus.indexcards.user.User;

@Service
public class PasswordService {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private PasswordResetTokenRepository pwResetTokenRepo;

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

    public long deleteExistingPasswordResetTokens(User user) {
        return pwResetTokenRepo.deleteByUser(user);
    }

    public PasswordResetToken savePasswordResetToken(String token, User user) {
        PasswordResetToken pwResetToken = new PasswordResetToken();
        pwResetToken.setGenerationTime(LocalDateTime.now());
        pwResetToken.setTokenHash(encodePassword(token));
        pwResetToken.setUser(user);
        return pwResetTokenRepo.save(pwResetToken);
    }

}
