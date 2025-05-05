package com.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        try {
            log.info("Send message: {} to Kafka topic.", message);

            kafkaTemplate.send(topic, message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
