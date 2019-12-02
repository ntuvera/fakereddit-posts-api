package com.example.postsapi.bean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CommentBeanTest {

    @InjectMocks
    private CommentBean comment;

    @Before
    public void init() {
        comment.setId(1);
        comment.setText("Test Comment Text");
        comment.setUserId(1);
        comment.setPostId(1);
    }

    @Test
    public void getText_Text_Success() {
        assertEquals("Test Comment Text", comment.getText());
    }

    @Test
    public void setText_NewText_Success() {
        comment.setText("New Test Comment Text");
        assertEquals("New Test Comment Text", comment.getText());
    }

    @Test
    public void setId_NewId_Success() {
        comment.setId(2);
        assertEquals(2, comment.getId());
    }

    @Test
    public void getPostId_PostId_Success() {
        assertEquals(1, comment.getPostId());
    }

    @Test
    public void setPostId_NewPostId_Success() {
        comment.setPostId(2);
        assertEquals(2, comment.getPostId());
    }

    @Test
    public void getUserId_UserId_Success() {
        assertEquals(1, comment.getUserId());
    }

    @Test
    public void setUserId_NewUserId_Success() {
        comment.setUserId(2);
        assertEquals(2, comment.getUserId());
    }

    @Test
    public void CommentBean_NewComment_Success() {
        CommentBean newComment = new CommentBean(2, "Test Comment Text", 1, 1);

        assertEquals(2, newComment.getId());
        assertEquals("Test Comment Text", newComment.getText());
        assertEquals(1, newComment.getPostId());
        assertEquals(1, newComment.getUserId());
    }
}
