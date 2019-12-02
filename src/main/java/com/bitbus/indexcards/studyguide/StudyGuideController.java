package com.bitbus.indexcards.studyguide;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bitbus.indexcards.error.ErrorCodeException;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("api/studyguides")
@RestController
@Slf4j
public class StudyGuideController {

    @Autowired
    private StudyGuideService studyGuideService;

    @GetMapping("{studyGuideId}")
    public StudyGuideDto findStudyGuide(@PathVariable long studyGuideId) throws ErrorCodeException {
        log.info("Looking up study-guide with ID {}", studyGuideId);
        StudyGuide studyGuide = studyGuideService.findWithAllChildren(studyGuideId);
        log.info("Found the {} study guide", studyGuide.getName());

        return StudyGuideDto.get(studyGuide, studyGuide.getIndexCards(), studyGuide.getTags());
    }

    @PostMapping
    public StudyGuideDto createStudyGuide(@RequestBody StudyGuideDto studyGuideDto) {
        log.info("Creating study guide with name {}", studyGuideDto.getStudyGuideName());
        StudyGuide studyGuide = studyGuideService.create(studyGuideDto.toStudyGuide(), studyGuideDto.getCategories());
        log.info("Created study guide {}:{} with {} flash cards", studyGuide.getId(), studyGuide.getName(),
                studyGuide.getIndexCards().size());
        return StudyGuideDto.get(studyGuide, studyGuide.getIndexCards());
    }

    @GetMapping
    public List<StudyGuideDto> searchStudyGuides(@RequestParam(name = "search", required = true) String searchParam) {
        // TODO - Is there any risk logging a string without checking it first?
        log.info("Performing a search against \"{}\"", searchParam);
        List<StudyGuide> studyGuides = studyGuideService.search(searchParam);
        log.info("Found {} study guides", studyGuides.size());
        return StudyGuideDto.get(studyGuides);
    }


}
