package com.bitbus.indexcards.error;

import com.bitbus.indexcards.user.EmailDoesNotExistException;
import com.bitbus.indexcards.user.UserNotFoundException;

/**
 * Helper class primarily for Exception suppliers
 */
public class Exceptions {

    private Exceptions() {}

    public static UserNotFoundException userNotFoundException() {
        return new UserNotFoundException();
    }

    public static EmailDoesNotExistException emailDoesNotExistException() {
        return new EmailDoesNotExistException();
    }
}
