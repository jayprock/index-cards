package com.bitbus.indexcards.error;

import lombok.Data;

@Data
public class Error {

    private final int code;
    private final String message;

}
