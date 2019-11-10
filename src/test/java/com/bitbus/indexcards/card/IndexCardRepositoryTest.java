package com.bitbus.indexcards.card;

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
@Sql("index-card-data.sql")
public class IndexCardRepositoryTest {

    @Autowired
    private IndexCardRepository indexCardRepo;

    @Test
    public void testFindByStudyGuideId() {
        List<IndexCard> cards = indexCardRepo.findByStudyGuideId(1);
        Assert.assertEquals(51, cards.size());
    }

    @Test
    public void testFindByStudyGuideIdAndTagsName() {
        List<IndexCard> cards = indexCardRepo.findByStudyGuideIdAndTagsName(1, "Test");
        Assert.assertEquals(1, cards.size());
        Assert.assertEquals("Washington DC", cards.get(0).getFront());
    }
}
