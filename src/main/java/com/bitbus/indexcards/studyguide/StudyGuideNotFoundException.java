package com.bitbus.indexcards.studyguide;

import org.springframework.http.HttpStatus;

import com.bitbus.indexcards.error.ErrorCodeException;
import com.bitbus.indexcards.error.HasSafeErrorMessage;

@SuppressWarnings("serial")
public class StudyGuideNotFoundException extends ErrorCodeException implements HasSafeErrorMessage {

    public StudyGuideNotFoundException(long studyGuideId) {
        super("Could not find StudyGuide with ID " + studyGuideId);
    }

    @Override
    public HttpStatus getErrorCode() {
        return HttpStatus.NOT_FOUND;
    }

}
