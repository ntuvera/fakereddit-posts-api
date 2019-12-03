package com.example.postsapi.service;

import com.example.postsapi.bean.UserBean;
import com.example.postsapi.exceptionhandler.*;
import com.example.postsapi.feign.CommentClient;
import com.example.postsapi.feign.UserClient;
import com.example.postsapi.model.Post;
import com.example.postsapi.mq.Sender;
import com.example.postsapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserClient userClient;

    @Autowired
    CommentClient commentClient;

    @Autowired
    Sender sender;

    @Override
    public Post createPost(Post newPost, int userId) throws NoPostTitleException {
        if(newPost.getTitle().trim().length() > 0) {
            newPost.setUser_id(userId);
            newPost.setUser(userClient.getUserById(userId));
            return postRepository.save(newPost);
        }
        throw new NoPostTitleException("Title is missing. Title is required Post body.");
    }

    @Override
    public String deletePost(int postId, int userId) throws PostNotFoundException, UnauthorizedActionException {
        if (!postRepository.findById(postId).isPresent())
            throw new PostNotFoundException("The post cannot be deleted. No post of post Id: " + postId + " exists.");

        Optional<Post> targetPost = postRepository.findById(postId);
        if (targetPost.isPresent() && targetPost.get().getUser().getId() == userId) {
            postRepository.deleteById(postId);
            sender.sendPostId(String.valueOf(postId));
            return "post: " + postId + " successfully deleted";
        }

        throw new UnauthorizedActionException(HttpStatus.UNAUTHORIZED, "You can only delete your own posts");
    }

    @Override
    public Iterable<Post> listPosts() throws PostsNotFoundException {
        Iterable<Post> allPosts = new ArrayList<Post>();
        allPosts = postRepository.findAll();
        allPosts.forEach((post) -> {
            UserBean fetchedUser = userClient.getUserById(post.getUser_id());
            if(fetchedUser != null) {
                post.setUser(fetchedUser);
            } else {
                post.setUser(new UserBean("User has been Deleted"));
            }
        });
        if (allPosts.iterator().hasNext())
            return allPosts;
        throw new PostsNotFoundException("No posts were found");
    }

    @Override
    public Iterable<Post> getPostByUserId(int userId) throws PostsNotFoundException, UserNotFoundException {
        UserBean fetchedUser;

        if (userClient.getUserById(userId) == null)
            throw new UserNotFoundException("No user found with user ID: " + userId);

        fetchedUser = userClient.getUserById(userId);

        Iterable<Post> foundUserPosts = postRepository.findAll();

        foundUserPosts.forEach((post) -> {
            post.setUser(fetchedUser);
        });

        if (foundUserPosts.iterator().hasNext()) {
            return postRepository.findByUserId(userId);
        } else {
            throw new PostsNotFoundException("No posts were found for user ID: " + userId);
        }

    }

    @Override
    public Optional<Post> findById(int postId) throws PostNotFoundException {
        Optional<Post> foundPost = postRepository.findById(postId);

        if (foundPost.isPresent())
            return foundPost;

        throw new PostNotFoundException("Unable to find post with ID: " + postId);
    }
}
