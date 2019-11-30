package com.example.postsapi.exceptionhandler;

public class NoPostTitleException extends Exception {
    public NoPostTitleException(String msg) { super(msg); }
}
