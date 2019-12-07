package com.bitbus.indexcards.user;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.bitbus.indexcards.studyguide.StudyGuide;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql("user-data.sql")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepo;

    @Test
    public void testFindByCreatedBy() {
        {
            StudyGuide studyGuide = new StudyGuide();
            studyGuide.setId(1);
            User user = userRepo.findCreatedBy(studyGuide);
            assertNotNull(user);
            assertEquals(1, user.getUserId());
        }
        {
            StudyGuide studyGuide = new StudyGuide();
            studyGuide.setId(2);
            User user = userRepo.findCreatedBy(studyGuide);
            assertNotNull(user);
            assertEquals(2, user.getUserId());
        }
        {
            StudyGuide studyGuide = new StudyGuide();
            studyGuide.setId(999999);
            User user = userRepo.findCreatedBy(studyGuide);
            assertNull(user);
        }
    }
}
