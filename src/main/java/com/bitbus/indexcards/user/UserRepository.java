package com.bitbus.indexcards.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bitbus.indexcards.studyguide.StudyGuide;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOptionalByUsername(String username);

    Optional<User> findOptionalByEmail(String email);

    Optional<User> findOptionalByUsernameOrEmail(String username, String email);

    @Query("select s.createdBy from StudyGuide s where s = ?1")
    User findCreatedBy(StudyGuide studyGuide);

}
