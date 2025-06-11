package com.kafka.controller;

import com.kafka.config.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KafkaController {

    @Autowired
    private MessageProducer messageProducer;

    @PostMapping("/send")
    public String sendMessage(@RequestParam("message") String message) {
        messageProducer.sendMessage("my-topic", message);
        return "Message sent: " + message;
    }

    @PostMapping("/sendBatch")
    public String sendBatch(@RequestBody List<String> messages) {
        for (String message : messages) {
            messageProducer.sendMessage("my-topic", message);
        }
        return "Batch sent: " + messages.size() + " messages";
    }

}
