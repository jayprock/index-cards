package com.bitbus.indexcards.user.recaptcha;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecaptchaApiResponse {

    @JsonProperty
    private boolean success;

    @JsonProperty("error-codes")
    private RecaptchaErrorCode[] errorCodes;

    public static enum RecaptchaErrorCode {
        MISSING_INPUT_SECRET,
        INVALID_INPUT_SECRET,
        MISSING_INPUT_RESPONSE,
        INVALID_INPUT_RESPONSE,
        BAD_REQUEST,
        TIMEOUT_OR_DUPLICATE;

        private static Map<String, RecaptchaErrorCode> errorCodeMap = new HashMap<>();

        static {
            errorCodeMap.put("missing-input-secret", MISSING_INPUT_SECRET);
            errorCodeMap.put("invalid-input-secret", INVALID_INPUT_SECRET);
            errorCodeMap.put("missing-input-response", MISSING_INPUT_RESPONSE);
            errorCodeMap.put("invalid-input-response", INVALID_INPUT_RESPONSE);
            errorCodeMap.put("bad-request", BAD_REQUEST);
            errorCodeMap.put("timeout-or-duplicate", TIMEOUT_OR_DUPLICATE);
        }

        @JsonCreator
        public static RecaptchaErrorCode fromValue(String value) {
            return errorCodeMap.get(value);
        }

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public RecaptchaErrorCode[] getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(RecaptchaErrorCode[] errorCodes) {
        this.errorCodes = errorCodes;
    }

}
