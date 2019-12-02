package com.bitbus.indexcards.studyguide;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.bitbus.indexcards.card.IndexCard;
import com.bitbus.indexcards.tag.StudyGuideTag;
import com.bitbus.indexcards.user.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class StudyGuide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_guide_id", nullable = false)
    private long id;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "studyGuide", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Size(min = 1, message = "A study guide must have at least 1 flash card")
    private List<IndexCard> indexCards;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "map_study_guide_tag", joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "studyGuideTagId"),
            indexes = {@Index(name = "map_study_guide_tag_unq", columnList = "id, studyGuideTagId", unique = true)})
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Size(min = 1, message = "At least one study guide tag must be provided")
    private List<StudyGuideTag> tags;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User createdBy;


}
