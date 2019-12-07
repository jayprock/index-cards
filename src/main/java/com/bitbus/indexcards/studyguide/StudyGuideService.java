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
    public StudyGuide find(long id) throws StudyGuideNotFoundException {
        Optional<StudyGuide> optStudyGuide = studyGuideRepo.findById(id);
        return optStudyGuide.orElseThrow(() -> new StudyGuideNotFoundException(id));
    }

    @Transactional
    public StudyGuide findWithAllChildren(long id) throws StudyGuideNotFoundException {
        StudyGuide studyGuide = find(id);
        studyGuide.getTags().size();
        studyGuide.getIndexCards().size();
        return studyGuide;
    }

    @Transactional
    public StudyGuide save(StudyGuide studyGuide, List<String> categories) {
        List<StudyGuideTag> tags = toStudyGuideTags(categories);
        studyGuide.setTags(tags);
        return studyGuideRepo.save(studyGuide);
    }

    private List<StudyGuideTag> toStudyGuideTags(List<String> categories) {
        List<StudyGuideTag> tags = new ArrayList<>();
        for (String category : categories) {
            String lowerCaseCategory = category.toLowerCase();
            Optional<StudyGuideTag> optTag = tagRepo.findByName(lowerCaseCategory);
            StudyGuideTag tag = optTag.orElseGet(() -> {
                StudyGuideTag t = new StudyGuideTag();
                t.setName(lowerCaseCategory);
                return t;
            });
            tags.add(tag);
        }
        return tags;
    }


    public List<StudyGuide> search(String searchParam) {
        return studyGuideRepo.search(searchParam);
    }

}
