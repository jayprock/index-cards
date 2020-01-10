package com.bitbus.indexcards.user;

import com.bitbus.indexcards.user.recaptcha.RecaptchaApiResponse;

import lombok.Data;

@Data
public class UserRegistrationResponse {

    private RecaptchaApiResponse recaptchaApiResponse;

}
