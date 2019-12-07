package com.bitbus.indexcards.studyguide;

import org.springframework.http.HttpStatus;

import com.bitbus.indexcards.error.ErrorCodeException;
import com.bitbus.indexcards.error.HasSafeErrorMessage;

@SuppressWarnings("serial")
public class StudyGuideEditNotAllowedException extends ErrorCodeException implements HasSafeErrorMessage {

    public StudyGuideEditNotAllowedException(long studyGuideId, String username) {
        super(String.format("User %s is not allowed to edit study guide %d", username, studyGuideId));
    }

    @Override
    public HttpStatus getErrorCode() {
        return HttpStatus.FORBIDDEN;
    }

}
