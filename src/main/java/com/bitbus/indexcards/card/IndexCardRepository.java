package com.bitbus.indexcards.card;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IndexCardRepository extends JpaRepository<IndexCard, Long> {

    List<IndexCard> findByStudyGuideId(long id);

    List<IndexCard> findByStudyGuideIdAndTagsName(long id, String tagName);

}
