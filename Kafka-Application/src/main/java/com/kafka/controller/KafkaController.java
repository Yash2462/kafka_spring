package com.kafka.controller;

import com.kafka.config.MessageProducer;
import org.apache.kafka.common.errors.InvalidPartitionsException;
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
            messageProducer.sendMessage("my-topic-batch", message);
        }
        return "Batch sent: " + messages.size() + " messages";
    }

    @PostMapping("/sendToPartition")
    public String sendToPartition(@RequestParam("message") String message,
                                  @RequestParam("partition") int partition) {
        try {
            messageProducer.sendMessageToPartition("my-topic-partition", message, partition);
            return "Message sent to partition " + partition;
        } catch (InvalidPartitionsException | IllegalArgumentException e) {
            return "Error: Partition " + partition + " does not exist.";
        } catch (Exception e) {
            System.out.println("Error sending message to partition: " + e.getMessage());
            return "Error sending message to partition: " + e.getMessage();
        }
    }

}
