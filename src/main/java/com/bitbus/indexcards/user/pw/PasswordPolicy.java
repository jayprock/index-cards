package com.bitbus.indexcards.user.pw;

public interface PasswordPolicy {

    String getPolicyName();

    void assertCompliance(String rawTestPassword) throws PasswordPolicyException;

}
