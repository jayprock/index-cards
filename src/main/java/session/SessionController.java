package session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitbus.indexcards.user.User;
import com.bitbus.indexcards.user.UserNotFoundException;
import com.bitbus.indexcards.user.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/session")
@Slf4j
public class SessionController {

    @Autowired
    private UserService userService;

    @PostMapping
    User login(@RequestBody LoginDto loginDto) throws AuthenticationException {
        throttleLogin(loginDto.getLogin());
        try {
            return userService.findByLogin(loginDto.getLogin());
        } catch (UserNotFoundException e) {
            throw new AuthenticationException();
        }
    }

    // TODO - Throttle login attempts for brute force protection
    private void throttleLogin(String login) {
        log.warn("Login throttling not implemented");
    }
}
