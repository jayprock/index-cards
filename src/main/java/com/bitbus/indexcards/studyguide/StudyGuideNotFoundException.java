package com.bitbus.indexcards.studyguide;

import org.springframework.http.HttpStatus;

import com.bitbus.indexcards.error.HasErrorCode;
import com.bitbus.indexcards.error.HasSafeErrorMessage;

@SuppressWarnings("serial")
public class StudyGuideNotFoundException extends RuntimeException implements HasSafeErrorMessage, HasErrorCode {

    public StudyGuideNotFoundException(long studyGuideId) {
        super("Could not find StudyGuide with ID " + studyGuideId);
    }

    @Override
    public int getErrorCode() {
        return HttpStatus.NOT_FOUND.value();
    }

}
