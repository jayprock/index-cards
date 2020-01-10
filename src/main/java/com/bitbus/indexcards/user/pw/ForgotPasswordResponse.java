package com.bitbus.indexcards.user.pw;

import com.bitbus.indexcards.user.recaptcha.RecaptchaApiResponse;

import lombok.Data;

@Data
public class ForgotPasswordResponse {

    private Boolean emailExists;
    private RecaptchaApiResponse recaptchaApiResponse;

    public ForgotPasswordResponse() {}

    public ForgotPasswordResponse(RecaptchaApiResponse recaptchaApiResponse) {
        this.recaptchaApiResponse = recaptchaApiResponse;
    }

    public ForgotPasswordResponse(Boolean emailExists, RecaptchaApiResponse recaptchaApiResponse) {
        this.emailExists = emailExists;
        this.recaptchaApiResponse = recaptchaApiResponse;
    }


}
