package com.bitbus.indexcards.session;

import org.springframework.http.HttpStatus;

import com.bitbus.indexcards.error.ErrorCodeException;
import com.bitbus.indexcards.error.HasSafeErrorMessage;

@SuppressWarnings("serial")
public class AuthenticationException extends ErrorCodeException implements HasSafeErrorMessage {

    public AuthenticationException() {}

    public AuthenticationException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getErrorCode() {
        return HttpStatus.UNAUTHORIZED;
    }

}
