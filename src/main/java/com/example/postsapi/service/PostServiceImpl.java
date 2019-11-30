package com.example.postsapi.service;

import com.example.postsapi.bean.UserBean;
import com.example.postsapi.exceptionhandler.NoPostTitleException;
import com.example.postsapi.exceptionhandler.PostNotFoundException;
import com.example.postsapi.exceptionhandler.PostsNotFoundException;
import com.example.postsapi.feign.CommentClient;
import com.example.postsapi.feign.UserClient;
import com.example.postsapi.model.Post;
import com.example.postsapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Post createPost(Post newPost, int userId) throws NoPostTitleException {
        if(newPost.getTitle().trim().length() > 0) {
            newPost.setUser_id(userId);
            newPost.setUser(userClient.getUserById(userId));
            return postRepository.save(newPost);
        }
        throw new NoPostTitleException("Title is missing. Title is required in newPost body.");
    }

    @Override
    public String deletePost(int postId) {
       postRepository.deleteById(postId);
       commentClient.deleteCommmentsByPostId(postId);
       return "post: " + postId + " successfully deleted";
       // TODO: how to sort out void response to check if post successfully deleted
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
    public Iterable<Post> getPostByUserId(int userId) throws PostsNotFoundException {
        Iterable<Post> foundUserPosts = postRepository.findAll();
        foundUserPosts.forEach((post) -> {
            UserBean fetchedUser = userClient.getUserById((post.getUser_id()));
            if(fetchedUser !=null) {
                post.setUser(fetchedUser);
            }
        });
        if (foundUserPosts.iterator().hasNext())
            return postRepository.findByUserId(userId);
        throw new PostsNotFoundException("No posts were found for user ID: " + userId);
    }

    @Override
    public Optional<Post> findById(int postId) throws PostNotFoundException {
        Optional<Post> foundPost = postRepository.findById(postId);

        if (foundPost.isPresent())
            return foundPost;

        throw new PostNotFoundException("Unable to find post with ID: " + postId);
    }
}
