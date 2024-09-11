package com.interview.taskmanager.domain;

import lombok.Getter;

@Getter
public class User {

    private Integer id;

    private String email;

    private String avatarUrl;

    private String username;

    private String password;

    public User(String email, String avatarUrl, String username, String password) {
        this.email = checkCorrectEmail(email);
        this.avatarUrl = checkCorrectUrl(avatarUrl);
        this.username = checkCorrectUsername(username);
        this.password = password;
    }

    public User(Integer id, String email, String avatarUrl, String username, String password) {
        this.id = id;
        this.email = checkCorrectEmail(email);
        this.avatarUrl = checkCorrectUrl(avatarUrl);
        this.username = checkCorrectUsername(username);
        this.password = password;
    }

    private String checkCorrectEmail(String email) {
        String regex = "^[A-Za-z0-9]+@[a-z]+\\.[a-z]{2,}$";
        if (!email.matches(regex)) {
            throw new IllegalArgumentException("Incorrect email");
        }
        return email;
    }

    private String checkCorrectUrl(String url) {
        String regex = "^https://([a-zA-Z0-9.-]+\\.)+[a-zA-Z]{2,}(?:/[a-zA-Z0-9._?&=-]*)?$";
        if (!url.matches(regex)) {
            throw new IllegalArgumentException("Incorrect avatar url");
        }
        return url;
    }

    private String checkCorrectUsername(String username) {
        String regex = "^[A-Za-z0-9 -]$";
        if (!username.matches(regex) && username.length() != 5) {
            throw new IllegalArgumentException("Incorrect username");
        }
        return username;
    }

}
