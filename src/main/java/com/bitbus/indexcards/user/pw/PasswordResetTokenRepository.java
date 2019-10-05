package com.bitbus.indexcards.user.pw;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitbus.indexcards.user.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByUser(User user);

    long deleteByUser(User user);

}
