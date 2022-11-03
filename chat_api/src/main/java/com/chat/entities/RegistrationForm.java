package com.chat.entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RegistrationForm {
    private String username;
    private String nickname;
    private String password;
    private String inviteCode;
}
