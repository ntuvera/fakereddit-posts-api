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
    @ApiOperation(value = "",
            notes = "Only logged in users can create posts")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "Fake post title", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "description", value = "Fake post description", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "User ID", required = true, dataType = "long", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created a post"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public Post createPost(
            @ApiParam(
                    name = "params",
                    value = "set of login parameters in JSON format",
                    example = "{\"title\": \"Hi, I'm a fake post title\", \"description\": \"Hi, I'm a fake post description\"}")
            @RequestBody Post newPost,
            @RequestHeader("userId") int userId) {
        if(newPost.getTitle().trim().length() >0) {
            return postService.createPost(newPost, userId);
        }
        return null;
    }

    @ApiOperation(value = "Retrieves a list of all available posts", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of posts")
    })
    @GetMapping("/list")
    public Iterable<Post> getAllPosts() {
        return postService.listPosts();
    }

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

    // Feign Client Comment routing
    @GetMapping("/{postId}/comment")
    public List<CommentBean> addCommentToPost(@PathVariable int postId, @RequestHeader("userId") Integer userId) {
        return commentClient.getCommentsByPostId(postId);
    }

    // Feign Client Get Post By Id
    @GetMapping("identify/{postId}")
    public Optional<Post> findPostById(@PathVariable int postId) {
        return postService.findById(postId);
    }
}
