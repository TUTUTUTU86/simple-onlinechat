package com.chat.entities;


import lombok.Data;

@Data
public class SimpleUser {
    private long userID;
    private String nickname;
    private String avatarURL;

    public static SimpleUser UserToSimpleUser(User user){
        SimpleUser simpleUser = new SimpleUser();
        simpleUser.avatarURL = user.getAvatarURL();
        simpleUser.userID = user.getId();
        simpleUser.nickname = user.getNickname();
        return simpleUser;
    }
}
