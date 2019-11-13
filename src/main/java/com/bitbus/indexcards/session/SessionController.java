package com.bitbus.indexcards.session;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitbus.indexcards.user.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/session")
@Slf4j
public class SessionController {

    @Autowired
    private UserService userService;


    @PostMapping
    Authentication login(HttpServletRequest request, @RequestBody LoginDto loginDto) throws AuthenticationException {
        log.debug("Attempting login on user {}", loginDto.getLogin());
        throttleLogin(loginDto.getLogin());
        Authentication authentication = userService.loginUser(loginDto.getLogin(), loginDto.getPassword());
        log.debug("Login successful for user {}", loginDto.getLogin());
        return authentication;
    }

    // TODO - Throttle login attempts for brute force protection
    private void throttleLogin(String login) {
        log.warn("Login throttling not implemented");
    }

}
