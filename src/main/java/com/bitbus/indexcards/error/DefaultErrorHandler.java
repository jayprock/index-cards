package com.bitbus.indexcards.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class DefaultErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Error> handleControllerException(Throwable ex) {
        HttpStatus status = getHttpStatus(ex);
        String errorMessage = getErrorMessage(ex);
        log.error("Caught error with code " + status.value() + " and message " + errorMessage, ex);
        return new ResponseEntity<Error>(new Error(status.value(), errorMessage), status);
    }

    private HttpStatus getHttpStatus(Throwable ex) {
        if (ex instanceof HasErrorCode) {
            return ((HasErrorCode) ex).getErrorCode();
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
