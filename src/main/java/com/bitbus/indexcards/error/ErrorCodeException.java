package com.bitbus.indexcards.error;

@SuppressWarnings("serial")
public abstract class ErrorCodeException extends Exception implements HasErrorCode {

    public ErrorCodeException() {
        super();
    }

    public ErrorCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorCodeException(String message) {
        super(message);
    }

    public ErrorCodeException(Throwable cause) {
        super(cause);
    }


}
