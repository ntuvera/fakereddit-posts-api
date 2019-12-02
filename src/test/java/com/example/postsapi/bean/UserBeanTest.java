package com.example.postsapi.bean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserBeanTest {

    @InjectMocks
    private UserBean user;

    @Before
    public void init() {
        user.setUsername("testUser");
    }

    @Test
    public void getUsernameTest_Username_Success() {
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void setUsernameTest_NewUsername_Success() {
        user.setUsername("Thanos");
        assertEquals("Thanos", user.getUsername());
    }
}
