package com.bitbus.indexcards.studyguide;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.bitbus.indexcards.card.IndexCard;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class StudyGuide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studyGuideId;

    private String name;

    @OneToMany(mappedBy = "studyGuide")
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<IndexCard> indexCards;

}
