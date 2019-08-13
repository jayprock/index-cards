package com.bitbus.indexcards.user.pw;

import org.springframework.stereotype.Component;

@Component
public class MinLengthPasswordPolicy implements PasswordPolicy {

    private static final int MIN_LENGTH = 8;

    @Override
    public String getPolicyName() {
        return "Minimum Length Policy (" + MIN_LENGTH + ")";
    }

    @Override
    public void assertCompliance(String rawTestPassword) throws PasswordPolicyException {
        if (rawTestPassword != null && rawTestPassword.length() < MIN_LENGTH) {
            throw new PasswordPolicyException(getPolicyName());
        }
    }

}
