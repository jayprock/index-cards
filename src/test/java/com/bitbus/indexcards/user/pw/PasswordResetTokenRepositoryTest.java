package com.bitbus.indexcards.user.pw;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.bitbus.indexcards.user.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql("password-reset-token-data.sql")
public class PasswordResetTokenRepositoryTest {

    @Autowired
    private PasswordResetTokenRepository pwResetTokenRepo;

    @Test
    public void test() {
        assertEquals(2, pwResetTokenRepo.findAll().size());

        User user = new User(1);
        assertEquals(101, pwResetTokenRepo.findByUser(user).getId());

        long deleteCount = pwResetTokenRepo.deleteByUser(user);
        assertEquals(1, deleteCount);

        assertEquals(1, pwResetTokenRepo.findAll().size());
    }
}
