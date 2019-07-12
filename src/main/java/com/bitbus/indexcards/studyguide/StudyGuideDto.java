package com.bitbus.indexcards.studyguide;

import java.util.List;
import java.util.stream.Collectors;

import com.bitbus.indexcards.card.FlashCardDto;
import com.bitbus.indexcards.card.IndexCard;

import lombok.Data;

@Data
public class StudyGuideDto {

    private long studyGuideId;
    private String studyGuideName;
    private String description;
    private List<String> categories;
    private List<FlashCardDto> flashCards;

    public static StudyGuideDto get(StudyGuide studyGuide, List<IndexCard> flashCards) {
        StudyGuideDto dto = new StudyGuideDto();
        dto.setStudyGuideId(studyGuide.getId());
        dto.setStudyGuideName(studyGuide.getName());
        dto.setDescription(studyGuide.getDescription());
        if (flashCards != null) {
            dto.setFlashCards(flashCards.stream() //
                    .map(card -> FlashCardDto.get(card)) //
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public static List<StudyGuideDto> get(List<StudyGuide> studyGuides) {
        return studyGuides.stream() //
                .map(guide -> get(guide, null)) //
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
