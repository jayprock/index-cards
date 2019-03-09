package com.bitbus.indexcards.studyguide;

import java.util.List;

import com.bitbus.indexcards.IndexCard;

import lombok.Data;

@Data
public class StudyGuide {

    private String name;

    private List<IndexCard> indexCards;

}
