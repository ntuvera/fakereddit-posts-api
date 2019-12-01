package com.example.postsapi.service;

import com.example.postsapi.exceptionhandler.NoPostTitleException;
import com.example.postsapi.exceptionhandler.PostNotFoundException;
import com.example.postsapi.exceptionhandler.PostsNotFoundException;
import com.example.postsapi.exceptionhandler.UserNotFoundException;
import com.example.postsapi.model.Post;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

public interface PostService {
    public Post createPost(Post newPost, @RequestHeader("id") int userId) throws NoPostTitleException;
    public String deletePost(int postId) throws PostNotFoundException;
    public Iterable<Post> listPosts() throws PostsNotFoundException;
    public Iterable<Post> getPostByUserId(int userId) throws PostsNotFoundException, UserNotFoundException;
    public Optional<Post> findById(int postId) throws PostNotFoundException;
}
