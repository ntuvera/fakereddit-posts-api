package com.example.postsapi.bean;

public class UserBean {
    private int id;
    private String username;

    public UserBean() { }

    public UserBean(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
