package com.bitbus.indexcards.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class DefaultErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Error> handleControllerException(Throwable ex) {
        HttpStatus status = getHttpStatus(ex);
        String errorMessage = getErrorMessage(ex);
        return new ResponseEntity<Error>(new Error(status.value(), errorMessage), status);
    }

    private HttpStatus getHttpStatus(Throwable ex) {
        if (ex instanceof HasErrorCode) {
            return HttpStatus.valueOf(((HasErrorCode) ex).getErrorCode());
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String getErrorMessage(Throwable ex) {
        if (ex instanceof HasSafeErrorMessage) {
            return ex.getMessage();
        }
        return null;
    }
}
