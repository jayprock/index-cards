package com.bitbus.indexcards.user.pw;

import lombok.Data;

@Data
public class ForgotPasswordRequest {

    private String email;
    private String recaptchaResponse;

}
