package com.bitbus.indexcards.error;

import org.springframework.http.HttpStatus;

public interface HasErrorCode {

    HttpStatus getErrorCode();

}
