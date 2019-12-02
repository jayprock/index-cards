package com.bitbus.indexcards.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.bitbus.indexcards.studyguide.StudyGuide;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class User {

    public User() {}

    public User(long userId) {
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Size(min = 3, max = 50, message = "Username length must be 3-50 characters")
    @Pattern(regexp = "[a-zA-Z0-9]*$")
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Email(message = "Valid email format is required")
    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @JsonIgnore
    @Column(nullable = false, columnDefinition = "CHAR(60) NOT NULL")
    private String password;

    @OneToMany(mappedBy = "createdBy")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<StudyGuide> studyGuidesCreated;

}
