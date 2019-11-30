package com.example.postsapi.controller;

import com.example.postsapi.bean.CommentBean;
import com.example.postsapi.exceptionhandler.NoPostTitleException;
import com.example.postsapi.exceptionhandler.PostNotFoundException;
import com.example.postsapi.exceptionhandler.PostsNotFoundException;
import com.example.postsapi.feign.CommentClient;
import com.example.postsapi.model.Post;
import com.example.postsapi.service.PostServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "Post Management System")
public class PostsApiController {
    @Autowired
    PostServiceImpl postService;

    @Autowired
    CommentClient commentClient;

    @PostMapping("/")
    @ApiOperation(value = "AHHHHHHHHHHHHH")
    public Post createPost(@RequestBody Post newPost, @RequestHeader("userId") int userId) throws NoPostTitleException {
            return postService.createPost(newPost, userId);
    }

    @GetMapping("/list")
    public Iterable<Post> getAllPosts() throws PostsNotFoundException {
        return postService.listPosts();
    }

    @GetMapping("/user/post")
    public Iterable<Post> getPostsByUserId(@RequestHeader("userId") Integer userId) throws PostsNotFoundException {
        return postService.getPostByUserId(userId);
    }
    // TODO: reactivate when we find a way to go around swagger-ui.html
//    @DeleteMapping("/{postId}")
//    @RequestMapping(value = "/{postId}", method = RequestMethod.DELETE)
//    public String deletePost(@PathVariable int postId) {
//        postService.deletePost(postId);
//        return "Post " + postId + " Deleted";
//    }

    // Feign Client Comment routing
    @GetMapping("/{postId}/comment")
    public List<CommentBean> addCommentToPost(@PathVariable int postId, @RequestHeader("userId") Integer userId) {
        return commentClient.getCommentsByPostId(postId);
    }

    // Feign Client Get Post By Id
    @GetMapping("identify/{postId}")
    public Optional<Post> findPostById(@PathVariable int postId) throws PostNotFoundException {
        return postService.findById(postId);
    }
}
