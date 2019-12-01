package com.example.postsapi.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Sender {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    Queue queue;

    public void sendPostId(String postId) {
        // TODO: how to specify consumer?
        rabbitTemplate.convertAndSend(queue.getName(), postId);
        System.out.println("PostId sent: " + postId + " on q: " + queue.getName());
    }
}
