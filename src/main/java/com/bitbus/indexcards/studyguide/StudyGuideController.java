package com.bitbus.indexcards.studyguide;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bitbus.indexcards.card.IndexCard;
import com.bitbus.indexcards.card.IndexCardService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("api/studyguides")
@RestController
@Slf4j
public class StudyGuideController {

    @Autowired
    private StudyGuideService studyGuideService;

    @Autowired
    private IndexCardService indexCardService;

    @GetMapping("{studyGuideId}")
    public StudyGuideDto findStudyGuide(@PathVariable long studyGuideId,
            @RequestParam(name = "category", required = false) String category) {
        log.info("Looking up study-guide with ID {}", studyGuideId);
        StudyGuide studyGuide = studyGuideService.findById(studyGuideId);
        log.info("Found the {} study guide", studyGuide.getName());

        List<IndexCard> flashCards = new ArrayList<>();
        if (category == null) {
            log.info("Looking up flash cards for the study guide {}:{}", studyGuideId, studyGuide.getName());
            flashCards = indexCardService.findByStudyGuideId(studyGuideId);
        } else {
            log.info("Looking up flash cards for the study guide {}:{} with category {}", studyGuideId,
                    studyGuide.getName(), category);
            flashCards = indexCardService.findByStudyGuideIdAndCategory(studyGuideId, category);
        }
        log.info("Found {} flash cards", flashCards.size());

        return StudyGuideDto.get(studyGuide, flashCards);
    }

    @PostMapping
    public StudyGuideDto createStudyGuide(@RequestBody StudyGuideDto studyGuideDto) {
        log.info("Creating study guide with name {}", studyGuideDto.getStudyGuideName());
        StudyGuide studyGuide = studyGuideService.create(studyGuideDto.toStudyGuide());
        log.info("Created study guide {}:{} with {} flash cards", studyGuide.getId(), studyGuide.getName(),
                studyGuide.getIndexCards().size());
        return StudyGuideDto.get(studyGuide, studyGuide.getIndexCards());
    }

    @GetMapping
    public List<StudyGuideDto> searchStudyGuides(@RequestParam(name = "search", required = true) String searchParam) {
        log.info("Performing a search against \"{}\"", searchParam);
        List<StudyGuide> studyGuides = studyGuideService.search(searchParam);
        log.info("Found {} study guides", studyGuides.size());
        return StudyGuideDto.get(studyGuides);
    }


}
