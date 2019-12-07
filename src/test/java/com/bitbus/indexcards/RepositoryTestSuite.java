package com.bitbus.indexcards;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.bitbus.indexcards.card.IndexCardRepositoryTest;
import com.bitbus.indexcards.studyguide.StudyGuideRepositoryTest;
import com.bitbus.indexcards.tag.StudyGuideTagRepositoryTest;
import com.bitbus.indexcards.user.UserRepositoryTest;
import com.bitbus.indexcards.user.pw.PasswordResetTokenRepositoryTest;

@RunWith(Suite.class)
@SuiteClasses({ //
        StudyGuideRepositoryTest.class, //
        IndexCardRepositoryTest.class, //
        StudyGuideTagRepositoryTest.class, //
        PasswordResetTokenRepositoryTest.class, //
        UserRepositoryTest.class})
public class RepositoryTestSuite {

}
