package com.example.postsapi.controller;

import com.example.postsapi.bean.CommentBean;
import com.example.postsapi.feign.CommentClient;
import com.example.postsapi.model.Post;
import com.example.postsapi.service.PostServiceImpl;
import io.swagger.annotations.*;
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
    @ApiOperation(
            value = "Create a post",
            notes = "Allows a user to create a post. Only logged in users can create posts")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "The title of a post. This field is required", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "description", value = "The content of a post. This field is not required", required = false, dataType = "string", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a post"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Post createPost(
            @ApiParam(
                    name = "Params",
                    value = "The body of the post that the user wants to create. Expects JSON as format",
                    example = "{\"title\": \"Hi, I'm a fake post title\", \"description\": \"Hi, I'm a fake post description\"}")
            @RequestBody Post newPost,
            @RequestHeader("userId") int userId) {
        if(newPost.getTitle().trim().length() >0) {
            return postService.createPost(newPost, userId);
        }
        return null;
    }

    @ApiOperation(
            value = "Show all available posts",
            notes = "Allows a user to see a list of all posts found in the database. Users do not need to be logged in to access all posts",
            response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of posts")
    })
    @GetMapping("/list")
    public Iterable<Post> getAllPosts() {
        return postService.listPosts();
    }

    @ApiOperation(
            value = "Get posts by user ID",
            notes = "Allows a user to see all posts created by a given user's ID",
            response = Iterable.class
    )
    @GetMapping("/user/post")
    public Iterable<Post> getPostsByUserId(@RequestHeader("userId") Integer userId) {
        return postService.getPostByUserId(userId);
    }
    // TODO: reactivate when we find a way to go around swagger-ui.html
//    @DeleteMapping("/{postId}")
//    @RequestMapping(value = "/{postId}", method = RequestMethod.DELETE)
//    public String deletePost(@PathVariable int postId) {
//        postService.deletePost(postId);
//        return "Post " + postId + " Deleted";
//    }

    @ApiOperation(
            value = "Gets comments by post ID",
            notes = "Allows a user to see all comments from a given post's ID",
            response = Iterable.class
    )
    @GetMapping("/{postId}/comment")
    public List<CommentBean> getCommentsByPostId(@PathVariable int postId, @RequestHeader("userId") Integer userId) {
        return commentClient.getCommentsByPostId(postId);
    }

    @ApiOperation(
            value = "Find a post by post ID",
            notes = "A feign endpoint that allows the client to find a post by post ID if it exists",
            response = Optional.class
    )
    @GetMapping("identify/{postId}")
    public Optional<Post> findPostById(@PathVariable int postId) {
        return postService.findById(postId);
    }
}
