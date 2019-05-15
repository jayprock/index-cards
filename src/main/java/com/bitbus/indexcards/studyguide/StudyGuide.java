package com.bitbus.indexcards.studyguide;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.bitbus.indexcards.card.IndexCard;
import com.bitbus.indexcards.tag.StudyGuideTag;
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

    @ManyToMany
    @JoinTable(name = "tag_study_guide", joinColumns = @JoinColumn(name = "studyGuideId"),
            inverseJoinColumns = @JoinColumn(name = "studyGuideTagId"))
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Size(min = 1)
    private List<StudyGuideTag> tags;

}
