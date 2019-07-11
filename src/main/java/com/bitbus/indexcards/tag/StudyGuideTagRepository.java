package com.bitbus.indexcards.tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyGuideTagRepository extends JpaRepository<StudyGuideTag, Long> {

    StudyGuideTag findByName(String name);

}
