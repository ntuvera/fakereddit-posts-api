package com.example.postsapi.controller;

import com.example.postsapi.bean.CommentBean;
import com.example.postsapi.bean.UserBean;
import com.example.postsapi.feign.CommentClient;
import com.example.postsapi.model.Post;
import com.example.postsapi.service.PostServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
public class PostsApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostServiceImpl postService;

    @MockBean
    private CommentClient commentClient;

    @InjectMocks
    private Post post;

    @InjectMocks
    private UserBean user;

    @InjectMocks
    private CommentBean comment;

    private List<Post> postList;

    private List<CommentBean> commentList;

    @Before
    public void init() {
        user.setUsername("testUser");
        user.setId(1);

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
    public void createPost_ReturnPost_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "")
                .header("userId", "1")
                .content("{\"title\":\"Test Post Title\",\"description\":\"Test Post Description\"}");

        when(postService.createPost(any(), anyInt())).thenReturn(post);

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"title\":\"Test Post Title\",\"description\":\"Test Post Description\",\"user_id\":1,\"user\":{\"username\":\"testUser\"}}\n"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getAllPosts_ReturnPostList_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/list");

        when(postService.listPosts()).thenReturn(postList);

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"title\":\"Test Post Title\",\"description\":\"Test Post Description\",\"user_id\":1,\"user\":{\"username\":\"testUser\"}}]"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getPostsByUserId_ReturnPostList_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/post")
                .header("Authorization", "")
                .header("userId", "1");

        when(postService.getPostByUserId(anyInt())).thenReturn(postList);

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"title\":\"Test Post Title\",\"description\":\"Test Post Description\",\"user_id\":1,\"user\":{\"username\":\"testUser\"}}]"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void deletePost_ReturnStringMsg_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/{postId}", 1)
                .header("userId", user.getId());

        when(postService.deletePost(anyInt(), anyInt())).thenReturn("Post 1 Deleted");

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("Post 1 Deleted"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getCommentsByPostId_ReturnsCommentsList_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/{postId}/comment", 1)
                .header("userId", "1");;

        when(commentClient.getCommentsByPostId(anyInt())).thenReturn(commentList);

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"text\":\"Test Comment Text\",\"postId\":1,\"userId\":1}]"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void findPostById_ReturnsPost_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/identify/{postId}", 1);

        when(postService.findById(anyInt())).thenReturn(java.util.Optional.of(post));

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"title\":\"Test Post Title\",\"description\":\"Test Post Description\",\"user_id\":1,\"user\":{\"username\":\"testUser\"}}"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}
