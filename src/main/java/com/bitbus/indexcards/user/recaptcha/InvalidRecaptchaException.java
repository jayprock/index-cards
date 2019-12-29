package com.bitbus.indexcards.user.recaptcha;

import org.springframework.http.HttpStatus;

import com.bitbus.indexcards.error.ErrorCodeException;
import com.bitbus.indexcards.error.HasSafeErrorMessage;

@SuppressWarnings("serial")
public class InvalidRecaptchaException extends ErrorCodeException implements HasSafeErrorMessage {

    public InvalidRecaptchaException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getErrorCode() {
        return HttpStatus.BAD_REQUEST;
    }

}
