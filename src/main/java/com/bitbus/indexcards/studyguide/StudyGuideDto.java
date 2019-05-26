package com.bitbus.indexcards.studyguide;

import java.util.List;
import java.util.stream.Collectors;

import com.bitbus.indexcards.card.FlashCardDto;
import com.bitbus.indexcards.card.IndexCard;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudyGuideDto {

    private long studyGuideId;
    private String studyGuideName;
    private String description;
    private List<FlashCardDto> flashCards;

    public StudyGuideDto(StudyGuide studyGuide, List<IndexCard> flashCards) {
        studyGuideId = studyGuide.getId();
        studyGuideName = studyGuide.getName();
        this.flashCards = flashCards.stream() //
                .map(card -> new FlashCardDto(card)) //
                .collect(Collectors.toList());
    }

    public StudyGuide toStudyGuide() {
        StudyGuide studyGuide = new StudyGuide();
        studyGuide.setId(studyGuideId);
        studyGuide.setName(studyGuideName);
        studyGuide.setDescription(description);
        studyGuide.setIndexCards(flashCards.stream() //
                .map(card -> card.toFlashCard(studyGuide)) //
                .collect(Collectors.toList()));
        return studyGuide;
    }
}
