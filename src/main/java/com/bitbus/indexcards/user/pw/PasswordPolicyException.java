package com.bitbus.indexcards.user.pw;

import org.springframework.http.HttpStatus;

import com.bitbus.indexcards.error.ErrorCodeException;
import com.bitbus.indexcards.error.HasSafeErrorMessage;

@SuppressWarnings("serial")
public class PasswordPolicyException extends ErrorCodeException implements HasSafeErrorMessage {

    public PasswordPolicyException(String policyName) {
        super("The provided password violates the password policy: " + policyName);
    }

    @Override
    public HttpStatus getErrorCode() {
        return HttpStatus.BAD_REQUEST;
    }

}
