package com.bitbus.indexcards.card;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import com.bitbus.indexcards.studyguide.StudyGuide;
import com.bitbus.indexcards.tag.FlashCardTag;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class IndexCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long indexCardId;

    @NotEmpty
    private String front;

    @NotEmpty
    private String back;

    @ManyToOne
    @JoinColumn(name = "studyGuideId")
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private StudyGuide studyGuide;

    @ManyToMany
    @JoinTable(name = "flash_card_tags", joinColumns = @JoinColumn(name = "indexCardId"),
            inverseJoinColumns = @JoinColumn(name = "flashCardTagId"))
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<FlashCardTag> tags;
}
