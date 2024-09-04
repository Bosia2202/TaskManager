package com.interview.taskmanager.application.user;

public interface AuthenticationUser {

    void signUp(String email, String username, Character[] password);

    void signIn(String email, Character[] password);

}
