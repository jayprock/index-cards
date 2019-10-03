package com.bitbus.indexcards.user;

import org.springframework.http.HttpStatus;

import com.bitbus.indexcards.error.ErrorCodeException;

@SuppressWarnings("serial")
public class UserNotFoundException extends ErrorCodeException {

    @Override
    public HttpStatus getErrorCode() {
        return HttpStatus.NOT_FOUND;
    }

}
