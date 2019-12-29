package com.bitbus.indexcards.user;

import java.util.Optional;

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

import com.bitbus.indexcards.error.ErrorCodeException;
import com.bitbus.indexcards.user.pw.InvalidPasswordResetException;
import com.bitbus.indexcards.user.pw.PasswordResetDto;
import com.bitbus.indexcards.user.recaptcha.RecaptchaApiResponse;
import com.bitbus.indexcards.user.recaptcha.RecaptchaService;
import com.bitbus.indexcards.util.UrlUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RecaptchaService recaptchaService;

    @PostMapping
    UserRegistrationResponse register(@RequestBody UserRegistrationDto registrationDto, HttpServletRequest request)
            throws ErrorCodeException {
        log.info("Processing request to register user with username {}", registrationDto.getUser().getUsername());

        log.debug("Validating recaptcha first...");
        RecaptchaApiResponse recaptchaApiResponse =
                recaptchaService.getRecaptchaApiResponse(registrationDto.getRecaptchaResponse());
        if (recaptchaApiResponse.isSuccess()) {
            log.debug("Recaptcha is valid");
            CreateUserDto userDto = registrationDto.getUser();
            log.debug("Attempting to create user {}", userDto.getUsername());
            User user = userService.create(userDto);
            log.debug("User {} successfully created with user ID {}", userDto.getUsername(), user.getUserId());

            log.debug("Performing automatic login for new user {}", user.getUsername());
            userService.loginUser(user.getUsername(), userDto.getPassword());
            log.debug("No authentication exception, therefore user {} was successfully logged in", user.getUsername());

        } else {
            log.warn("Received invalid recaptcha response from {}; response had the following error codes: {}",
                    request.getRemoteAddr(), recaptchaApiResponse.getErrorCodes());
        }

        UserRegistrationResponse response = new UserRegistrationResponse();
        response.setRecaptchaApiResponse(recaptchaApiResponse);
        return response;
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

    @PutMapping("password-forgot")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    void handleForgotPassword(@RequestBody String email, HttpServletRequest request) throws ErrorCodeException {
        log.debug("Handling forgot password for email {}", email);
        userService.sendPasswordResetEmail(email, UrlUtil.getBaseUrl(request));
        log.debug("Initiated password reset email for {}", email);
    }

    @PostMapping("password-reset")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void handleUnauthenticatedPasswordReset(@RequestBody PasswordResetDto pwResetDto) throws ErrorCodeException {
        log.debug("Handling password reset for unauthenticated user");
        long userId = Optional.ofNullable(pwResetDto.getUserId()) //
                .orElseThrow(() -> new InvalidPasswordResetException("Bad request, User ID is required"));
        String token = Optional.ofNullable(pwResetDto.getToken()) //
                .orElseThrow(() -> new InvalidPasswordResetException("Password reset token is required"));
        String newPassword = Optional.ofNullable(pwResetDto.getNewPassword()) //
                .orElseThrow(() -> new InvalidPasswordResetException("New password is required"));
        log.debug("Attempting password reset on user {}", userId);
        userService.resetUnauthenticatedUserPassword(userId, token, newPassword);
        log.debug("Password reset successful on user {}", userId);
    }
}
