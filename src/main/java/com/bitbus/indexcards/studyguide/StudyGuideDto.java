package com.bitbus.indexcards.studyguide;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import com.bitbus.indexcards.card.FlashCardDto;
import com.bitbus.indexcards.card.IndexCard;
import com.bitbus.indexcards.tag.StudyGuideTag;

import lombok.Data;

@Data
public class StudyGuideDto {

    private long studyGuideId;
    private String studyGuideName;
    private String description;
    private List<FlashCardDto> flashCards;
    private List<String> categories;

    public static StudyGuideDto get(StudyGuide studyGuide) {
        return get(studyGuide, null, null);
    }

    public static StudyGuideDto get(StudyGuide studyGuide, List<IndexCard> flashCards) {
        return get(studyGuide, flashCards, null);
    }

    public static StudyGuideDto get(StudyGuide studyGuide, List<IndexCard> flashCards, List<StudyGuideTag> tags) {
        StudyGuideDto dto = new StudyGuideDto();
        dto.setStudyGuideId(studyGuide.getId());
        dto.setStudyGuideName(studyGuide.getName());
        dto.setDescription(studyGuide.getDescription());
        if (flashCards != null) {
            dto.setFlashCards(flashCards.stream() //
                    .map(card -> FlashCardDto.get(card)) //
                    .collect(toList()));
        }
        if (tags != null) {
            dto.setCategories(tags.stream() //
                    .map(tag -> tag.getName()) //
                    .collect(toList()));
        }
        return dto;
    }

    public static List<StudyGuideDto> get(List<StudyGuide> studyGuides) {
        return studyGuides.stream() //
                .map(guide -> get(guide)) //
                .collect(toList());
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
