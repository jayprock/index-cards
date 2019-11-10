package com.bitbus.indexcards.session;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/session")
@Slf4j
public class SessionController {

    @Autowired
    private AuthenticationManager authManager;


    @PostMapping
    Authentication login(HttpServletRequest request, @RequestBody LoginDto loginDto) throws AuthenticationException {
        log.debug("Attempting login on user {}", loginDto.getLogin());
        throttleLogin(loginDto.getLogin());
        try {
            Authentication authentication = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Login successful for user {}", loginDto.getLogin());
            return authentication;
        } catch (org.springframework.security.core.AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            throw new AuthenticationException();
        }
    }

    // TODO - Throttle login attempts for brute force protection
    private void throttleLogin(String login) {
        log.warn("Login throttling not implemented");
    }

}
