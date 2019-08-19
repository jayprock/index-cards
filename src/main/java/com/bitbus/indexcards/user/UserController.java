package com.bitbus.indexcards.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitbus.indexcards.user.pw.PasswordPolicyException;

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

    @GetMapping(path = "username/{username}")
    boolean isUsernameAvailable(@PathVariable("username") String username) {
        log.debug("Checking if username {} exists", username);
        boolean usernameAvailable = userService.isUsernameAvailable(username);
        log.debug("Username {} is available: {}", username, usernameAvailable);
        return usernameAvailable;
    }
}
