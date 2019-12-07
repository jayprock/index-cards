package com.bitbus.indexcards.studyguide;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bitbus.indexcards.error.ErrorCodeException;
import com.bitbus.indexcards.user.User;
import com.bitbus.indexcards.user.UserNotFoundException;
import com.bitbus.indexcards.user.UserService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("api/studyguides")
@RestController
@Slf4j
public class StudyGuideController {

    @Autowired
    private StudyGuideService studyGuideService;
    @Autowired
    private UserService userService;


    @GetMapping("{studyGuideId}")
    public StudyGuideDto findStudyGuide(@PathVariable long studyGuideId) throws ErrorCodeException {
        log.info("Looking up study-guide with ID {}", studyGuideId);
        StudyGuide studyGuide = studyGuideService.findWithAllChildren(studyGuideId);
        log.info("Found the {} study guide", studyGuide.getName());

        return StudyGuideDto.get(studyGuide, studyGuide.getIndexCards(), studyGuide.getTags());
    }

    @PostMapping
    public StudyGuideDto createStudyGuide(@RequestBody StudyGuideDto studyGuideDto, Principal principal)
            throws UserNotFoundException {
        User createdBy = userService.findByLogin(principal.getName());
        log.info("User {} is creating study guide with name {}", createdBy.getUserId(),
                studyGuideDto.getStudyGuideName());
        StudyGuide studyGuide = studyGuideDto.toStudyGuide();
        studyGuide.setCreatedBy(createdBy);
        studyGuideService.save(studyGuide, studyGuideDto.getCategories());
        log.info("User {} created study guide {}:{} with {} flash cards", createdBy.getUserId(), studyGuide.getId(),
                studyGuide.getName(), studyGuide.getIndexCards().size());
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

    @PutMapping
    public StudyGuideDto updateStudyGuide(@RequestBody StudyGuideDto studyGuideDto, Principal principal)
            throws UserNotFoundException, ErrorCodeException {
        StudyGuide studyGuide = studyGuideDto.toStudyGuide();
        User user = userService.findCreatedBy(studyGuide);
        if (!principal.getName().equals(user.getUsername())) {
            throw new StudyGuideEditNotAllowedException(studyGuide.getId(), principal.getName());
        }
        log.info("User {} is editing study guide with id {}", user.getUserId(), studyGuide.getId());
        studyGuide.setCreatedBy(user);
        studyGuideService.save(studyGuide, studyGuideDto.getCategories());
        log.info("User {} modified study guide {}:{}. It now has {} flash cards", user.getUserId(), studyGuide.getId(),
                studyGuide.getName(), studyGuide.getIndexCards().size());
        return StudyGuideDto.get(studyGuide, studyGuide.getIndexCards());
    }

}
