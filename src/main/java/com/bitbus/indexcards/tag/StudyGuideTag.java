package com.bitbus.indexcards.tag;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.bitbus.indexcards.studyguide.StudyGuide;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "study_guide_tag",
        indexes = {@Index(name = "study_guide_tag_name_idx", columnList = "name", unique = true)})
@Data
public class StudyGuideTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studyGuideTagId;

    @Pattern(regexp = "^[a-z][a-z0-9-]*$")
    private String name;

    @ManyToMany(mappedBy = "tags")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<StudyGuide> studyGuides;

}
