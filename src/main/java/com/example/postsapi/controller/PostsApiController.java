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
            @ApiImplicitParam(
                    name = "newPost",
                    value = "The body of the post that the user wants to create. Expects JSON as format. The title of the post is required while the description may be left blank",
                    example =
                            "{\n" +
                            "  \"title\": \"Hi, I'm a sample post title\",\n" +
                            "  \"description\": \"Hi, I'm a sample post description. Leave me blank if you'd like.\"\n" +
                            "}",
                    required = true,
                    dataType = "string",
                    paramType = "body")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach was not found")})
    public Post createPost(
            @RequestBody Post newPost,
            @ApiParam(
                    name = "userId",
                    value = "The ID of the user creating the post. This value is extracted from the Bearer token of the incoming request's Authorization header.",
                    example = "1",
                    required = true)
            @RequestHeader("userId") int userId) {
        if(newPost.getTitle().trim().length() >0) {
            return postService.createPost(newPost, userId);
        }
        return null;
    }

    @ApiOperation(
            value = "Get all posts",
            notes = "Allows a user to see a list of all posts found in the database. Users do not need to be logged in to access all posts")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "OK",
                    response = Post.class,
                    responseContainer = "List"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach was not found")})
    @GetMapping("/list")
    public Iterable<Post> getAllPosts() {
        return postService.listPosts();
    }

    @ApiOperation(
            value = "Get posts by user ID",
            notes = "Allows a user to see all posts created by a given user's ID")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "OK",
                    response = Post.class,
                    responseContainer = "List",
                    examples = @Example(
                        value = {
                                @ExampleProperty(
                                        mediaType = "Example JSON",
                                        value = "Sample JSON")})),
            @ApiResponse(code = 404, message = "The resource you were trying to reach was not found")})
    @GetMapping("/user/post")
    public Iterable<Post> getPostsByUserId(
            @ApiParam(
                    value = "The ID of the user to check for authored posts. This value is extracted from the Bearer token of the incoming request's Authorization header.",
                    required = true,
                    example = "1")
            @RequestHeader("userId") Integer userId) {
        return postService.getPostByUserId(userId);
    }

    // TODO: reactivate when we find a way to go around swagger-ui.html
    //    @ApiOperation(
    //            value = "Delete post by post ID",
    //            notes = "Allows a user to delete a post using a a post's ID")
    //    @ApiResponses(value = {
    //            @ApiResponse(
    //                    code = 200,
    //                    message = "OK",
    //                    response = String.class),
    //            @ApiResponse(code = 404, message = "The resource you were trying to reach was not found")})
    //    @DeleteMapping("/{postId}")
    //    @RequestMapping(value = "/{postId}", method = RequestMethod.DELETE)
    //    public String deletePost(
    //            @ApiParam(
    //                    value = "The ID of the post to delete. This value is extracted from the {postId} path variable. This value is required.",
    //                    required = true,
    //                    example = "1")
    //            @PathVariable int postId) {
    //        postService.deletePost(postId);
    //        return "Post " + postId + " Deleted";
    //    }

    @ApiOperation(
            value = "Get comments by post ID",
            notes = "Allows a user to see all comments from a given post's ID")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "OK",
                    response = CommentBean.class,
                    responseContainer = "List"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach was not found")})
    @GetMapping("/{postId}/comment")
    public List<CommentBean> getCommentsByPostId(
            @ApiParam(
                    value = "The ID of the post to query for comments. This value is extracted from the {postId} path variable. This value is required.",
                    required = true,
                    example = "1")
            @PathVariable int postId,
            // TODO: Is userId parameter needed?
            @RequestHeader("userId") Integer userId) {
        return commentClient.getCommentsByPostId(postId);
    }

    @ApiOperation(
            value = "Find a post by post ID",
            notes = "A feign endpoint that allows the client to find a post by post ID if it exists")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "OK",
                    response = Post.class),
            @ApiResponse(code = 404, message = "The resource you were trying to reach was not found")})
    @GetMapping("identify/{postId}")
    public Optional<Post> findPostById(
            @ApiParam(
                    value = "The ID of the post to find. This value is extracted from the {postId} path variable. This value is required.",
                    required = true,
                    example = "1")
            @PathVariable int postId) {
        return postService.findById(postId);
    }
}
