package com.example.postsapi.bean;

public class CommentBean {
    private int id;
    private String text;
    private int postId;
    private int userId;

    public CommentBean() { }

    public CommentBean(int id, String text, int postId, int userId) {
        this.id = id;
        this.text = text;
        this.postId = postId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
