package com.bitbus.indexcards.card;

import com.bitbus.indexcards.studyguide.StudyGuide;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FlashCardDto {

    private String front;
    private String back;

    public FlashCardDto(IndexCard indexCard) {
        front = indexCard.getFront();
        back = indexCard.getBack();
    }

    public IndexCard toFlashCard(StudyGuide studyGuide) {
        IndexCard card = new IndexCard();
        card.setFront(front);
        card.setBack(back);
        card.setStudyGuide(studyGuide);
        return card;
    }

}
