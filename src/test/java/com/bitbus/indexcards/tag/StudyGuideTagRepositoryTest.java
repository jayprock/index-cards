package com.bitbus.indexcards.tag;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class StudyGuideTagRepositoryTest {

    @Autowired
    private StudyGuideTagRepository studyGuideTagRepo;

    @Test
    public void testCreateDuplicateNameFails() {
        // Geography tag is preloaded
        StudyGuideTag tag = new StudyGuideTag();
        tag.setName("Geography");
        try {
            studyGuideTagRepo.save(tag);
            Assert.fail("A unique index should be violated and an exception is expected");
        } catch (Exception e) {
            log.info("Received an expected Exception after violating the unique name constraint", e);
        }
    }

    @Test
    public void testFindByName() {
        // Geography tag is preloaded
        Optional<StudyGuideTag> tag = studyGuideTagRepo.findByName("Geography");
        Assert.assertTrue(tag.isPresent());
        tag = studyGuideTagRepo.findByName("NameDoesNotExist");
        Assert.assertFalse(tag.isPresent());
    }
}
