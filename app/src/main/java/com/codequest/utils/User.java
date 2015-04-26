package com.codequest.utils;

/**
 * Created by Falcon on 4/25/2015.
 */
public class User {
    public long userId;
    public String login;
    public String password;

    public User (long userId, String login, String password) {
        this.userId = userId;
        this.login = login;
        this.password = password;
    }

}
