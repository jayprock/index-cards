package com.bitbus.indexcards;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.bitbus.indexcards.session.SessionControllerTest;
import com.bitbus.indexcards.studyguide.StudyGuideControllerTest;
import com.bitbus.indexcards.tag.StudyGuideTagControllerTest;
import com.bitbus.indexcards.user.UserControllerTest;

@RunWith(Suite.class)
@SuiteClasses({ //
        SessionControllerTest.class, //
        StudyGuideControllerTest.class, //
        StudyGuideTagControllerTest.class, //
        UserControllerTest.class //
})
public class ControllerTests {

}
