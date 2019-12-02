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
    public void initComment() {
        comment.setId(1);
        comment.setText("Test Comment Text");
        comment.setUserId(1);
        comment.setPostId(1);
    }

    @Test
    public void getText() {
        assertEquals("Test Comment Text", comment.getText());
    }

    @Test
    public void setText() {
        comment.setText("New Test Comment Text");
        assertEquals("New Test Comment Text", comment.getText());
    }

    @Test
    public void setId() {
        comment.setId(2);
        assertEquals(2, comment.getId());
    }

    @Test
    public void getPostId() {
        assertEquals(1, comment.getPostId());
    }

    @Test
    public void setPostId() {
        comment.setPostId(2);
        assertEquals(2, comment.getPostId());
    }

    @Test
    public void getUserId() {
        assertEquals(1, comment.getUserId());
    }

    @Test
    public void setUserId() {
        comment.setUserId(2);
        assertEquals(2, comment.getUserId());
    }
}
