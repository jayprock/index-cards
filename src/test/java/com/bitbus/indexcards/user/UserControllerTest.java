package com.bitbus.indexcards.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbus.indexcards.user.pw.InvalidPasswordResetException;
import com.bitbus.indexcards.user.pw.PasswordResetDto;
import com.fasterxml.jackson.databind.ObjectMapper;

// UserController is publicly accessible
// @formatter:off
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateUser_200() throws Exception {
        when(userService.create(any(CreateUserDto.class))).thenReturn(dummyUser());
        mvc.perform(
                post("/api/users")
                .content(toJson(dummyCreateUserDto()))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    
    @Test
    public void testIsUsernameAvailable_200() throws Exception {
        mvc.perform(
                get("/api/users/username/myuser")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    
    @Test
    public void testIsEmailAvailable_200() throws Exception {
        mvc.perform(
                get("/api/users/email/myemail@test.com")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    
    @Test
    public void testHandleForgotPassword_202() throws Exception {
        mvc.perform(
                put("/api/users/password-forgot")
                .content("myemail@test.com")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isAccepted());
    }
    
    @Test
    public void testHandlePasswordReset_204() throws Exception {
        // TODO - test in live application - see if CSRF is generated
        mvc.perform(
                post("/api/users/password-reset")
                .content(toJson(dummyPasswordResetDto()))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
            .andExpect(status().isNoContent());
    }
    
    @Test
    public void testHandlePasswordReset_invalidToken_400() throws Exception {
        doThrow(new InvalidPasswordResetException("bad"))
            .when(userService).resetUnauthenticatedUserPassword(anyLong(), anyString(), anyString());
        mvc.perform(
                post("/api/users/password-reset")
                .content(toJson(dummyPasswordResetDto()))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
            .andExpect(status().isBadRequest());
    }
    
    private CreateUserDto dummyCreateUserDto() throws Exception {
        CreateUserDto dto = new CreateUserDto();
        dto.setUsername("uname");
        dto.setPassword("pword");
        dto.setEmail("email@test.com");
        return dto;
    }

    private User dummyUser() {
        User user = new User();
        user.setUsername("unitTest");
        user.setId(1);
        user.setEmail("unitTest@test.com");
        return user;
    }
    
    private PasswordResetDto dummyPasswordResetDto() {
        PasswordResetDto dto = new PasswordResetDto();
        dto.setUserId(1);
        dto.setNewPassword("pwNew");
        dto.setOldPassword("pwOld");
        dto.setToken("myToken1234");
        return dto;
    }
    
    private String toJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }
}
