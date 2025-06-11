package com.kafka.config;

import org.apache.kafka.common.PartitionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        System.out.println("Sending message: " + message + " to topic: " + topic);
        kafkaTemplate.send(topic, message);
    }

    public void sendMessageToPartition(String topic, String message, int partition) {
        //check if the partition exists for the given topic
        List<PartitionInfo> partitions = kafkaTemplate.partitionsFor(topic);
        //if no partitions are found, throw an exception
        if (partition < 0 || partition >= partitions.size()) {
            throw new IllegalArgumentException("Partition " + partition + " does not exist for topic " + topic);
        }
        kafkaTemplate.send(topic, partition, null, message);
    }
}
