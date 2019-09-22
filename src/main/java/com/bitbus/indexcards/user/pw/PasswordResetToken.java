package com.bitbus.indexcards.user.pw;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bitbus.indexcards.user.User;

import lombok.Data;

@Entity
@Table(indexes = {@Index(name = "idx_pw_reset_token_user", columnList = "user_id")})
@Data
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_reset_token_id")
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "CHAR(60) NOT NULL")
    private String tokenHash;

    private LocalDateTime generationTime;

}
