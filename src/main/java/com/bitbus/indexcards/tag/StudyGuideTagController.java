package com.bitbus.indexcards.tag;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("api/categories")
@RestController
@Slf4j
public class StudyGuideTagController {

    @Autowired
    private StudyGuideTagService studyGuideTagService;

    @GetMapping
    public List<String> searchByName(@RequestParam(name = "search", required = true) String searchParam,
            @RequestParam(name = "exclude", required = false) List<String> exclusions) {
        log.debug("Looking up study guide categories for \"{}\"", searchParam);
        List<StudyGuideTag> results;
        if (CollectionUtils.isEmpty(exclusions)) {
            results = studyGuideTagService.searchByName(searchParam);
        } else {
            results = studyGuideTagService.searchByNameWithExclusions(searchParam, exclusions);
        }
        log.debug("Found {} study guide categories from search", results.size());
        return results.stream().map(tagObj -> tagObj.getName()).collect(Collectors.toList());
    }

}
