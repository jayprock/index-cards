package com.bitbus.indexcards.session;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthenticationManager authManager;
    @MockBean
    private AuthenticationProvider authProvider;

    //@formatter:off
    @Test
    public void testLogin_valid_200() throws Exception {
        when(authManager.authenticate(any(Authentication.class))).thenReturn(new UsernamePasswordAuthenticationToken("test", null));
        mvc.perform(
                post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(dummyLoginDto())))
            .andExpect(status().isOk());
    }
    
    @Test
    public void testLogin_invalidPassword_401() throws Exception {
        when(authManager.authenticate(any(Authentication.class))).thenThrow(new BadCredentialsException("test"));       
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
    
}
