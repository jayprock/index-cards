package com.bitbus.indexcards.studyguide;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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

    @NotEmpty
    private String name;
    private String description;

    @OneToMany(mappedBy = "studyGuide", cascade = CascadeType.ALL)
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Size(min = 1)
    private List<IndexCard> indexCards;

}
