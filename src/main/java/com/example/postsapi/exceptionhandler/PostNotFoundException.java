package com.example.postsapi.exceptionhandler;

public class PostNotFoundException extends Exception {
    public PostNotFoundException(String msg) {
        super(msg);
    }
}
