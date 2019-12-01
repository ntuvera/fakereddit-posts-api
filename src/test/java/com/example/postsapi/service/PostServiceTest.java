package com.example.postsapi.service;

import com.example.postsapi.bean.CommentBean;
import com.example.postsapi.bean.UserBean;
import com.example.postsapi.feign.CommentClient;
import com.example.postsapi.feign.UserClient;
import com.example.postsapi.model.Post;
import com.example.postsapi.repository.PostRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class PostServiceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    private PostServiceImpl postService;

    @InjectMocks
    private Post post;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserBean user;

    @Mock
    private UserClient userClient;

    @Mock
    private CommentBean comment;

    @Mock
    private CommentClient commentClient;

    private List<Post> postList;

    private List<CommentBean> commentList;


    @Before
    public void init() {
        user.setUsername("testUser");

        comment.setId(1);
        comment.setText("Test Comment Text");
        comment.setPostId(1);
        comment.setUserId(1);

        commentList = new ArrayList<>();
        commentList.add(comment);

        post.setId(1);
        post.setTitle("Test Post Title");
        post.setDescription("Test Post Description");
        post.setUser_id(1);
        post.setUser(user);

        postList = new ArrayList<>();
        postList.add(post);
    }

    @Test
    public void createPost_ReturnsPost_Success() throws Exception {}

    @Test
    public void deletePost_ReturnsStringMsg_Success() throws Exception {}

    @Test
    public void listPosts_ReturnsPostList_Success() throws Exception {}

    @Test
    public void getPostByUserId_ReturnsPostList_Success() throws Exception {}

    @Test
    public void findById_ReturnsPost_Success() throws Exception {}
}
