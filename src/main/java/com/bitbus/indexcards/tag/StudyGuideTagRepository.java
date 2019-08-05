package com.bitbus.indexcards.tag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudyGuideTagRepository extends JpaRepository<StudyGuideTag, Long> {

    Optional<StudyGuideTag> findByName(String name);

    @Query("select tag from StudyGuideTag tag where lower(tag.name) like lower(concat('%', ?1, '%'))")
    List<StudyGuideTag> searchByName(String name);

    @Query("select tag from StudyGuideTag tag where lower(tag.name) like lower(concat('%', ?1, '%')) "
            + "and tag.name not in ?2")
    List<StudyGuideTag> searchByNameWithExclusions(String name, List<String> exclusions);

}
