package com.example.postsapi.exceptionhandler;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String msg) { super(msg); }
}
