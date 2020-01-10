package com.bitbus.indexcards.user.recaptcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.bitbus.indexcards.util.StringMatcher;

@Service
public class RecaptchaService {

    @Autowired
    private RecaptchaProperties properties;
    @Autowired
    private RestTemplate restTemplate;

    public RecaptchaApiResponse getRecaptchaApiResponse(String recaptchaResponse) throws InvalidRecaptchaException {
        if (StringUtils.isEmpty(recaptchaResponse)) {
            throw new InvalidRecaptchaException("Recaptcha response is missing");
        } else if (!StringMatcher.isAlphaNumericUnderscoreHyphen(recaptchaResponse)) {
            throw new InvalidRecaptchaException("Recaptcha response contains unexpected characters");
        }
        String apiUrl = String.format(properties.getApiUrl() + "?secret=%s&response=%s", properties.getSecretKey(),
                recaptchaResponse);
        RecaptchaApiResponse response =
                restTemplate.postForObject(apiUrl, HttpEntity.EMPTY, RecaptchaApiResponse.class);
        return response;
    }
}
