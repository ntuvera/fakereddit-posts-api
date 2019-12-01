package com.example.postsapi.service;

import com.example.postsapi.bean.CommentBean;
import com.example.postsapi.bean.UserBean;
import com.example.postsapi.exceptionhandler.NoPostTitleException;
import com.example.postsapi.exceptionhandler.PostNotFoundException;
import com.example.postsapi.exceptionhandler.PostsNotFoundException;
import com.example.postsapi.exceptionhandler.UserNotFoundException;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class PostServiceTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    private PostServiceImpl postService;

    @InjectMocks
    private Post post;

    @InjectMocks
    private Post badPost;

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
    public void createPost_ReturnsPost_Success() throws NoPostTitleException {
        when(postRepository.save(any())).thenReturn(post);

        Post newPost = postService.createPost(post, 1);

        assertNotNull(newPost);
        assertEquals(post.getTitle(), newPost.getTitle());
    }

    @Test(expected = NoPostTitleException.class)
    public void createPost_ThrewNoPostTitleException_Failure() throws NoPostTitleException {
        badPost.setTitle("");
        postService.createPost(badPost, 1);
    }

    @Test
    public void listPosts_ReturnsPostList_Success() throws PostsNotFoundException {
        when(postRepository.findAll()).thenReturn(postList);
        when(userClient.getUserById(anyInt())).thenReturn(user);

        Iterable<Post> foundPosts = postService.listPosts();

        assertNotNull(foundPosts);
        assertEquals(postList.get(0).getUser().getUsername(), foundPosts.iterator().next().getUser().getUsername());
    }

    @Test(expected = PostsNotFoundException.class)
    public void listPosts_ThrewNoPostsFoundException_Failure() throws PostsNotFoundException {
        postService.listPosts();
    }

    @Test
    public void getPostByUserId_ReturnsPostList_Success() throws PostsNotFoundException, UserNotFoundException {
        when(userClient.getUserById(anyInt())).thenReturn(user);
        when(postRepository.findAll()).thenReturn(postList);
        when(postRepository.findByUserId(anyInt())).thenReturn(postList);

        Iterable<Post> foundPosts = postService.getPostByUserId(1);

        assertNotNull(foundPosts);
        assertEquals(postList.get(0).getUser().getUsername(), foundPosts.iterator().next().getUser().getUsername());
    }

    @Test(expected = PostsNotFoundException.class)
    public void getPostByUserId_ThrewPostsNotFoundException_Failure() throws PostsNotFoundException, UserNotFoundException {
        when(userClient.getUserById(anyInt())).thenReturn(user);

        postService.getPostByUserId(1);
    }

    @Test(expected = UserNotFoundException.class)
    public void getPostByUserId_ThrewUserNotFoundException_Failure() throws PostsNotFoundException, UserNotFoundException {
        postService.getPostByUserId(0);
    }

    @Test
    public void findById_ReturnsPost_Success() throws PostNotFoundException {
        when(postRepository.findById(anyInt())).thenReturn(java.util.Optional.of(post));

        Optional<Post> foundPost = postService.findById(1);

        assertNotNull(foundPost);
        assertEquals(post.getTitle(), foundPost.get().getTitle());
    }

    @Test(expected = PostNotFoundException.class)
    public void findById_ThrewPostNotFoundException_Failure() throws PostNotFoundException {
        postService.findById(0);
    }

    @Test
    public void deletePost_ReturnsStringMsg_Success() throws PostNotFoundException {
        when(postRepository.findById(anyInt())).thenReturn(Optional.of(post));

        String deletedMsg = postService.deletePost(1);

        assertNotNull(deletedMsg);
        assertEquals("post: 1 successfully deleted", deletedMsg);
    }

    @Test(expected = PostNotFoundException.class)
    public void deletePost_ThrewPostNotFoundException_Failure() throws PostNotFoundException {
        postService.deletePost(0);
    }
}
