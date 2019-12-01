package com.example.postsapi.exceptionhandler;

public class PostsNotFoundException extends Exception {
    public PostsNotFoundException(String msg) { super(msg); }
}
