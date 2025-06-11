package com.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic myTopic() {
        // Create a topic named "my-topic-partition" with 1 partition and a replication factor of 1
        //The topic will have only one partition (all messages go to a single partition unless specified otherwise).
        // in production, you would typically have more partitions for better scalability and parallel processing.
        return new NewTopic("my-topic-partition", 2, (short) 1);
    }
}
