package com.example.postsapi.mq;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SenderTest {

    @Mock
    private Sender sender;

    @Mock
    private RabbitTemplate rabbitTemplate;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void sendPostId_Success() {
        sender.sendPostId("1");

        System.out.println("PostId sent: 1 on q: queue1");

        verify(sender, times(1)).sendPostId("1");
        assertEquals("PostId sent: 1 on q: queue1\n", outContent.toString());
    }

}
