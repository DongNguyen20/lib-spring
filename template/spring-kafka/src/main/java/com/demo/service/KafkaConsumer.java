package com.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {
    @KafkaListener(topics = "test-topic", groupId = "demogroup")
    public void listen(String message) {
        log.info("Listen from Kafka topic!");
        System.out.println(">>> Received: " + message);
    }
}
