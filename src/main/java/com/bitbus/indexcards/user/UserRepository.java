package com.bitbus.indexcards.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOptionalByUsername(String username);

    Optional<User> findOptionalByEmail(String email);

}
