package com.bitbus.indexcards.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitbus.indexcards.user.User;
import com.bitbus.indexcards.user.UserNotFoundException;
import com.bitbus.indexcards.user.UserService;
import com.bitbus.indexcards.user.pw.PasswordService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/session")
@Slf4j
public class SessionController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordService passwordService;


    @PostMapping
    User login(@RequestBody LoginDto loginDto) throws AuthenticationException {
        log.debug("Attempting login on user {}", loginDto.getLogin());
        throttleLogin(loginDto.getLogin());
        try {
            User user = userService.findByLogin(loginDto.getLogin());
            log.debug("User {} exists, checking password", user.getUsername());
            boolean passwordCorrect = passwordService.isPasswordCorrect(loginDto.getPassword(), user.getPassword());
            log.debug("User {} password match: {}", user.getUsername(), passwordCorrect);
            if (!passwordCorrect) {
                throw new AuthenticationException();
            }
            return user;
        } catch (UserNotFoundException e) {
            throw new AuthenticationException();
        }
    }

    // TODO - Throttle login attempts for brute force protection
    private void throttleLogin(String login) {
        log.warn("Login throttling not implemented");
    }

}
