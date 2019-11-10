package com.bitbus.indexcards.session;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bitbus.indexcards.user.User;
import com.bitbus.indexcards.user.UserNotFoundException;
import com.bitbus.indexcards.user.UserService;
import com.bitbus.indexcards.user.pw.PasswordService;

@Primary
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordService pwService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user;
        try {
            user = userService.findByLogin(authentication.getName());
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(authentication.getName());
        }
        boolean pwMatches = pwService.isMatchesHash((String) authentication.getCredentials(), user.getPassword());
        if (!pwMatches) {
            throw new BadCredentialsException("Bad Credentials for user " + authentication.getName());
        }
        return new UsernamePasswordAuthenticationToken(user.getUsername(), null,
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
