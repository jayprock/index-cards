package com.bitbus.indexcards.tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudyGuideTagService {

    @Autowired
    private StudyGuideTagRepository tagRepo;

    public List<StudyGuideTag> searchByName(String name) {
        return tagRepo.searchByName(name);
    }

    public List<StudyGuideTag> searchByNameWithExclusions(String name, List<String> exclusions) {
        return tagRepo.searchByNameWithExclusions(name, exclusions);
    }
}
