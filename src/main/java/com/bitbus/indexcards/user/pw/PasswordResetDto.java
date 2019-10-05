package com.bitbus.indexcards.user.pw;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetDto {

    private long userId;
    private String token;
    private String oldPassword;
    private String newPassword;

}
