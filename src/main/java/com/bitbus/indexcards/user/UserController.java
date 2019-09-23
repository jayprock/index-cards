package com.bitbus.indexcards.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bitbus.indexcards.user.pw.PasswordPolicyException;
import com.bitbus.indexcards.util.UrlUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    User create(@RequestBody CreateUserDto userDto) throws PasswordPolicyException {
        log.info("Attempting to create user with username {}", userDto.getUsername());
        User user = userService.create(userDto);
        log.info("User {} successfully created with user ID {}", userDto.getUsername(), user.getId());
        return user;
    }

    @GetMapping("username/{username}")
    boolean isUsernameAvailable(@PathVariable("username") String username) {
        log.debug("Checking if username {} exists", username);
        boolean usernameAvailable = userService.isUsernameAvailable(username);
        log.debug("Username {} is available: {}", username, usernameAvailable);
        return usernameAvailable;
    }

    @GetMapping("email/{email}")
    boolean isEmailAvailable(@PathVariable("email") String email) {
        log.debug("Checking if email {} is available", email);
        boolean emailAvailable = userService.isEmailAvailable(email);
        log.debug("Email {} is available: {}", emailAvailable);
        return emailAvailable;
    }

    @PutMapping("password")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    void handleForgotPassword(@RequestBody String email, HttpServletRequest request) throws EmailDoesNotExistException {
        log.debug("Handling forgot password for email {}", email);
        userService.sendPasswordResetEmail(email, UrlUtil.getBaseUrl(request));
        log.debug("Initiated password reset email for {}", email);
    }
}
