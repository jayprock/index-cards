package com.bitbus.indexcards.studyguide;

import java.util.Optional;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudyGuideService {

    @Autowired
    private StudyGuideRepository studyGuideRepo;

    @Transactional
    public StudyGuide findById(long id) {
        Optional<StudyGuide> optStudyGuide = studyGuideRepo.findById(id);
        if (optStudyGuide.isPresent()) {
            StudyGuide studyGuide = optStudyGuide.get();
            Hibernate.initialize(studyGuide.getIndexCards());
            return studyGuide;
        }
        return null;
    }

}
