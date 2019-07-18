package com.bitbus.indexcards.tag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyGuideTagRepository extends JpaRepository<StudyGuideTag, Long> {

    Optional<StudyGuideTag> findByName(String name);

    List<StudyGuideTag> findByNameIgnoreCaseContaining(String name);

}
