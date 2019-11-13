package com.bitbus.indexcards.session;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.bitbus.indexcards.BaseSecuredControllerTest;
import com.bitbus.indexcards.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SessionController.class)
public class SessionControllerTest extends BaseSecuredControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @MockBean
    private UserService userService;

    //@formatter:off
    @Test
    public void testLogin_valid_200() throws Exception {
        when(userService.loginUser(anyString(), anyString())).thenReturn(new UsernamePasswordAuthenticationToken("test", null));
        mvc.perform(
                post("/api/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(dummyLoginDto())))
            .andExpect(status().isOk());
    }
    
    @Test
    public void testLogin_invalidPassword_401() throws Exception {
        when(userService.loginUser(anyString(), anyString())).thenThrow(new AuthenticationException());
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
