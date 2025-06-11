package com.kafka.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageConsumer {

    //tip : here if we specify the same group id for both the listeners, then only one listener will receive the message
    //to avoid this we can specify different group id for both the listeners
    @KafkaListener(topics = "my-topic", groupId = "my-group-a")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        if ("fail".equalsIgnoreCase(message)) {
            throw new RuntimeException("Simulated processing error");
        }
    }

    @KafkaListener(topics = "my-topic", groupId = "my-group-b")
    public void listen1(String message) {
        System.out.println("Received message to other subscriber: " + message);
    }

    @KafkaListener(topics = "my-topic", groupId = "my-group-batch")
    public void listen(List<String> messages) {
        System.out.println("Received batch of messages: " + messages);
        for (String message : messages) {
            // Process each message
            System.out.println("Processing: " + message);
        }
    }
}
