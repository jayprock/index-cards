package com.bitbus.indexcards.studyguide;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("api/studyguides")
@RestController
@Slf4j
public class StudyGuideController {

    @Autowired
    private StudyGuideService studyGuideService;

    @GetMapping("{studyGuideId}")
    public StudyGuide findStudyGuide(@PathVariable long studyGuideId) {
        log.info("Looking up study-guide with ID {}", studyGuideId);
        return studyGuideService.findById(studyGuideId);
    }

    @PostMapping
    public StudyGuide createStudyGuide(@RequestBody StudyGuide studyGuide) {
        log.info("Creating study guide with name {}", studyGuide.getName());
        return studyGuideService.create(studyGuide);
    }


}
