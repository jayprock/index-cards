package com.bitbus.indexcards.card;

import com.bitbus.indexcards.studyguide.StudyGuide;

import lombok.Data;

@Data
public class FlashCardDto {

    private String front;
    private String back;

    public static FlashCardDto get(IndexCard indexCard) {
        FlashCardDto dto = new FlashCardDto();
        dto.setFront(indexCard.getFront());
        dto.setBack(indexCard.getBack());
        return dto;
    }

    public IndexCard toFlashCard(StudyGuide studyGuide) {
        IndexCard card = new IndexCard();
        card.setFront(front);
        card.setBack(back);
        card.setStudyGuide(studyGuide);
        return card;
    }

}
