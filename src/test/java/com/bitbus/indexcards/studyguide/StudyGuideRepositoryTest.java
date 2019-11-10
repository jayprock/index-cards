package com.bitbus.indexcards.studyguide;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql("studyguide-data.sql")
public class StudyGuideRepositoryTest {

    @Autowired
    private StudyGuideRepository studyGuideRepo;

    @Test
    public void testSearch() {
        {
            List<StudyGuide> studyGuides = studyGuideRepo.search("mAtH");
            Assert.assertEquals(2, studyGuides.size());
        }
        {
            List<StudyGuide> studyGuides = studyGuideRepo.search("Zach");
            Assert.assertEquals(1, studyGuides.size());
        }
    }
}
