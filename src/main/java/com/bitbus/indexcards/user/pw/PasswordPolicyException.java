package com.bitbus.indexcards.user.pw;

import org.springframework.http.HttpStatus;

import com.bitbus.indexcards.error.HasErrorCode;
import com.bitbus.indexcards.error.HasSafeErrorMessage;

@SuppressWarnings("serial")
public class PasswordPolicyException extends Exception implements HasSafeErrorMessage, HasErrorCode {

    public PasswordPolicyException(String policyName) {
        super("The provided password violates the password policy: " + policyName);
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.BAD_REQUEST.value();
    }

}
