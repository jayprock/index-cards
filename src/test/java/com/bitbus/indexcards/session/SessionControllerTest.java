package com.bitbus.indexcards.session;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbus.indexcards.user.User;
import com.bitbus.indexcards.user.UserService;
import com.bitbus.indexcards.user.pw.PasswordService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    @MockBean
    private PasswordService pwService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    //@formatter:off
    @Test
    public void testLogin_valid_200() throws Exception {
        when(userService.findByLogin(anyString())).thenReturn(dummyUser());
        when(pwService.isMatchesHash(anyString(), anyString())).thenReturn(true);
        
        mvc.perform(
                post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(dummyLoginDto())))
            .andExpect(status().isOk());
    }
    
    @Test
    public void testLogin_invalidPassword_401() throws Exception {
        when(userService.findByLogin(anyString())).thenReturn(dummyUser());
        when(pwService.isMatchesHash(anyString(), anyString())).thenReturn(false);
        
        mvc.perform(
                post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(dummyLoginDto())))
            .andExpect(status().isUnauthorized());
    }

    private LoginDto dummyLoginDto() {
        LoginDto dto = new LoginDto();
        dto.setLogin("mylogin");
        dto.setPassword("password");
        return dto;
    }
    
    private User dummyUser() {
        User user = new User(1);
        user.setEmail("myemail@gmail.com");
        user.setUsername("myusername");
        user.setPassword("password");
        return user;
    }

}
