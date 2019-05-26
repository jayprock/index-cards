package com.bitbus.indexcards.studyguide;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudyGuideService {

    @Autowired
    private StudyGuideRepository studyGuideRepo;

    @Transactional
    public StudyGuide findById(long id) {
        Optional<StudyGuide> optStudyGuide = studyGuideRepo.findById(id);
        return optStudyGuide.orElseThrow(() -> new StudyGuideNotFoundException(id));
    }

    public StudyGuide create(StudyGuide studyGuide) {
        return studyGuideRepo.save(studyGuide);
    }

}
