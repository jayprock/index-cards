package com.bitbus.indexcards.user;

import lombok.Data;

@Data
public class UserRegistrationDto {

    private CreateUserDto user;
    private String recaptchaResponse;

}
