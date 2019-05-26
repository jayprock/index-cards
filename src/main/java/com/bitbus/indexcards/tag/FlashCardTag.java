package com.bitbus.indexcards.tag;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.bitbus.indexcards.card.IndexCard;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class FlashCardTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long flashCardTagId;

    private String name;

    @ManyToMany(mappedBy = "tags")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<IndexCard> flashCards;
}
