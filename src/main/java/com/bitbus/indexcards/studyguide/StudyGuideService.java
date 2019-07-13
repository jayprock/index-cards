package com.bitbus.indexcards.studyguide;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitbus.indexcards.tag.StudyGuideTag;
import com.bitbus.indexcards.tag.StudyGuideTagRepository;

@Service
public class StudyGuideService {

    @Autowired
    private StudyGuideRepository studyGuideRepo;

    @Autowired
    private StudyGuideTagRepository tagRepo;

    @Transactional
    public StudyGuide findById(long id) {
        Optional<StudyGuide> optStudyGuide = studyGuideRepo.findById(id);
        return optStudyGuide.orElseThrow(() -> new StudyGuideNotFoundException(id));
    }

    @Transactional
    public StudyGuide create(StudyGuide studyGuide, List<String> categories) {
        List<StudyGuideTag> tags = new ArrayList<>();
        for (String category : categories) {
            Optional<StudyGuideTag> optTag = tagRepo.findByName(category);
            StudyGuideTag tag = optTag.orElseGet(() -> {
                StudyGuideTag t = new StudyGuideTag();
                t.setName(category);
                return t;
            });
            tags.add(tag);
        }
        studyGuide.setTags(tags);
        return studyGuideRepo.save(studyGuide);
    }

    public List<StudyGuide> search(String searchParam) {
        return studyGuideRepo.search(searchParam);
    }

}
