package com.sparta.springresttemplateclient.entity;

import lombok.Getter;

@Getter
public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}