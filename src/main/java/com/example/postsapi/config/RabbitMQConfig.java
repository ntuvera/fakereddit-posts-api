
package com.example.postsapi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final static String QUEUE_NAME = "queue1";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false, false ,false);
    }
}
