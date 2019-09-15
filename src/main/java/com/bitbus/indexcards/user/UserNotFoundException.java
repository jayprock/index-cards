package com.bitbus.indexcards.user;

import org.springframework.http.HttpStatus;

import com.bitbus.indexcards.error.HasErrorCode;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception implements HasErrorCode {

    @Override
    public HttpStatus getErrorCode() {
        return HttpStatus.NOT_FOUND;
    }

}
