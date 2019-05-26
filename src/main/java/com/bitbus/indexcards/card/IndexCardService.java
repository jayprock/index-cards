package com.bitbus.indexcards.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexCardService {

    @Autowired
    private IndexCardRepository indexCardRepo;

    public List<IndexCard> findByStudyGuideId(long studyGuideId) {
        return indexCardRepo.findByStudyGuideId(studyGuideId);
    }

    public List<IndexCard> findByStudyGuideIdAndCategory(long studyGuideId, String category) {
        return indexCardRepo.findByStudyGuideIdAndTagsName(studyGuideId, category);
    }


}
