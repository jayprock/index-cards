package com.bitbus.indexcards.session;

import org.springframework.http.HttpStatus;

import com.bitbus.indexcards.error.HasErrorCode;
import com.bitbus.indexcards.error.HasSafeErrorMessage;

@SuppressWarnings("serial")
public class AuthenticationException extends Exception implements HasSafeErrorMessage, HasErrorCode {

    public AuthenticationException() {}

    public AuthenticationException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getErrorCode() {
        return HttpStatus.UNAUTHORIZED;
    }

}
