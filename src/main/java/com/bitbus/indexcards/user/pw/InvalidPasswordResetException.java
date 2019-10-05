package com.bitbus.indexcards.user.pw;

import org.springframework.http.HttpStatus;

import com.bitbus.indexcards.error.ErrorCodeException;
import com.bitbus.indexcards.error.HasSafeErrorMessage;

@SuppressWarnings("serial")
public class InvalidPasswordResetException extends ErrorCodeException implements HasSafeErrorMessage {

    public InvalidPasswordResetException(String safeErrorMessage) {
        super(safeErrorMessage);
    }

    @Override
    public HttpStatus getErrorCode() {
        return HttpStatus.BAD_REQUEST;
    }

}
