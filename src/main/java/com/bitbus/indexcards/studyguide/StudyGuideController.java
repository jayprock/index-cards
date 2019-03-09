package com.bitbus.indexcards.studyguide;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitbus.indexcards.IndexCard;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("api/studyguides")
@RestController
@Slf4j
public class StudyGuideController {

    @GetMapping("{studyGuideId}")
    public StudyGuide findStudyGuide(@PathVariable String studyGuideId) {
        log.info("Looking up study-guide with ID {}", studyGuideId);
        StudyGuide studyGuide = new StudyGuide();
        studyGuide.setName("US Capitals");
        List<IndexCard> indexCards = new ArrayList<>();
        {
            IndexCard indexCard = new IndexCard();
            indexCard.setFront("Virginia");
            indexCard.setBack("Richmond");
            indexCards.add(indexCard);
        }
        {
            IndexCard indexCard = new IndexCard();
            indexCard.setFront("Maryland");
            indexCard.setBack("Annapolis");
            indexCards.add(indexCard);
        }
        studyGuide.setIndexCards(indexCards);

        return studyGuide;
    }


}
