# kafka_spring

A demo project for integrating Apache Kafka with Spring Boot.

## Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [Kafka Configuration](#kafka-configuration)
  - [Producer Configuration](#producer-configuration)
  - [Consumer Configuration](#consumer-configuration)
  - [application.properties](#applicationproperties)
- [How to Run](#how-to-run)
- [API Usage](#api-usage)
- [Dependencies](#dependencies)
- [References](#references)

---

## Overview

This project demonstrates how to use Kafka as a message broker with Spring Boot, providing simple producer and consumer examples.

## Project Structure

```
Kafka-Application/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/kafka/
│   │   │       ├── config/
│   │   │       │   ├── KafkaConsumerConfig.java
│   │   │       │   ├── KafkaProducerConfig.java
│   │   │       │   ├── MessageConsumer.java
│   │   │       │   └── MessageProducer.java
│   │   │       ├── controller/
│   │   │       │   └── KafkaController.java
│   │   │       └── KafkaApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/kafka/KafkaApplicationTests.java
├── pom.xml
```

## Kafka Configuration

### Producer Configuration (`KafkaProducerConfig.java`)

- `ProducerFactory` bean:
  - `bootstrap.servers`: `localhost:9092`
  - `key.serializer`: `org.apache.kafka.common.serialization.StringSerializer`
  - `value.serializer`: `org.apache.kafka.common.serialization.StringSerializer`
- `KafkaTemplate` bean for sending messages.

### Consumer Configuration (`KafkaConsumerConfig.java`)

- `ConsumerFactory` bean:
  - `bootstrap.servers`: `localhost:9092`
  - `group.id`: `my-group-id`
  - `key.deserializer`: `org.apache.kafka.common.serialization.StringDeserializer`
  - `value.deserializer`: `org.apache.kafka.common.serialization.StringDeserializer`
  - (Optional) `auto.offset.reset`: `earliest`
- `ConcurrentKafkaListenerContainerFactory` for listener management.

### application.properties

```properties
spring.application.name=Kafka

# Kafka config
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=my-group-id

# Optional serializer/deserializer properties (can be set here or in bean configs)
# auto-offset-reset= earliest
# key-deserializer= org.apache.kafka.common.serialization.StringDeserializer
# value-deserializer= org.apache.kafka.common.serialization.StringDeserializer
# producer.key-serializer= org.apache.kafka.common.serialization.StringSerializer
# value-serializer= org.apache.kafka.common.serialization.StringSerializer
```

## How to Run
1. **Start Kafka & Zookeeper with Docker Compose**  
   If you have Docker installed, you can use the following `docker-compose.yml` to start Kafka and Zookeeper without manual installation:

   ```yaml
   version: '3'
   services:
     zookeeper:
       image: confluentinc/cp-zookeeper:7.5.0
       environment:
         ZOOKEEPER_CLIENT_PORT: 2181
         ZOOKEEPER_TICK_TIME: 2000
       ports:
         - "2181:2181"

     kafka:
       image: confluentinc/cp-kafka:7.5.0
       depends_on:
         - zookeeper
       ports:
         - "9092:9092"
       environment:
         KAFKA_BROKER_ID: 1
         KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
         KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
         KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
   
2. **Start Kafka & Zookeeper**  
   Ensure you have a Kafka broker running locally on port 9092.

3. **Build the Application**
   ```sh
   cd Kafka-Application
   mvn clean install
   ```

4. **Run the Spring Boot App**
   ```sh
   mvn spring-boot:run
   ```

## API Usage

- **Send Message**
  - **Endpoint:** `POST /send`
  - **Parameter:** `message` (String)
  - **Example using curl:**
    ```sh
    curl -X POST "http://localhost:8080/send?message=HelloKafka"
    ```
- **Send Batch Messages**
  - **Endpoint:** `POST /sendBatch`
  - **Body:** JSON array of strings
  - **Example:**
    ```sh
    curl -X POST -H "Content-Type: application/json" \
      -d '["msg1", "msg2", "msg3"]' \
      http://localhost:8080/sendBatch
    ```

- **Send Message to Specific Partition**
  - **Endpoint:** `POST /sendToPartition`
  - **Parameters:**
    - `message` (String, as query param)
    - `partition` (Integer, as query param)
  - **Example:**
    ```sh
    curl -X POST "http://localhost:8080/sendToPartition?message=HelloPartition&partition=1"
    ```    

- **Consumer**
  - Listens to topic `my-topic` with group IDs: `my-group-a`, `my-group-b`
  - Messages will be printed to the console by the consumers.

## Dependencies

Main dependencies from `pom.xml`:
- Spring Boot Starter Web
- Spring for Apache Kafka (`spring-kafka`)
- Spring Boot Starter Test
- Spring Kafka Test

## References

- [Spring for Apache Kafka Documentation](https://docs.spring.io/spring-kafka/docs/current/reference/html/)
- [Project Repository](https://github.com/Yash2462/kafka_spring)
- [View all Kafka-related code in this repo](https://github.com/Yash2462/kafka_spring/search?q=kafka)

---

*Note: This README was generated based on the codebase as of the latest update. If you have additional requirements or want to document advanced Kafka configurations, please specify!*
