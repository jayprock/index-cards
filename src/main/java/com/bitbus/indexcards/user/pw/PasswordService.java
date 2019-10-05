package com.bitbus.indexcards.user.pw;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bitbus.indexcards.user.User;

@Service
public class PasswordService {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Value("${token.validity-minutes:60}")
    private int tokenValidityMinutes;

    @Autowired
    private PasswordResetTokenRepository pwResetTokenRepo;

    @Autowired
    private List<PasswordPolicy> passwordPolicies;

    public void assertPasswordInCompliance(String rawTextPassword) throws PasswordPolicyException {
        for (PasswordPolicy passwordPolicy : passwordPolicies) {
            passwordPolicy.assertCompliance(rawTextPassword);
        }
    }

    public String encode(String rawTextPassword) {
        return PASSWORD_ENCODER.encode(rawTextPassword);
    }

    public boolean isMatchesHash(String rawTextPassword, String encodedPassword) {
        return PASSWORD_ENCODER.matches(rawTextPassword, encodedPassword);
    }

    @Transactional
    public PasswordResetToken createNewPasswordResetToken(User user, String token) {
        pwResetTokenRepo.deleteByUser(user);
        PasswordResetToken pwResetToken = new PasswordResetToken();
        pwResetToken.setGenerationTime(LocalDateTime.now());
        pwResetToken.setTokenHash(encode(token));
        pwResetToken.setUser(user);
        return pwResetTokenRepo.save(pwResetToken);
    }

    public void assertPasswordResetTokenValid(User user, String token) throws InvalidPasswordResetException {
        PasswordResetToken pwResetToken = pwResetTokenRepo.findByUser(user);
        if (pwResetToken == null) {
            throw new InvalidPasswordResetException("No password reset token exists, was a link sent?");
        }
        long elapsedMinutes = pwResetToken.getGenerationTime().until(LocalDateTime.now(), ChronoUnit.MINUTES);
        if (elapsedMinutes > tokenValidityMinutes) {
            throw new InvalidPasswordResetException(
                    "The password reset time has expired. A new reset token is required.");
        }
        if (!isMatchesHash(token, pwResetToken.getTokenHash())) {
            throw new InvalidPasswordResetException("Password reset token is invalid");
        }
    }

}
