package com.bitbus.indexcards;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
public abstract class BaseSecuredControllerTest {

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected AuthenticationProvider authProvider;
}
