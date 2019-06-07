package com.bitbus.indexcards.studyguide;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudyGuideRepository extends JpaRepository<StudyGuide, Long> {

    @Query("select s from StudyGuide s join s.tags t "
            + "where lower(s.name) like lower(concat('%', concat(?1, '%'))) or "
            + "lower(t.name) like lower(concat('%', concat(?1, '%')))")
    List<StudyGuide> search(String searchParam);

}
