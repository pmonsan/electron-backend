package com.electron.mfs.pg.customer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerManagerKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(CustomerManagerKafkaProducer.class);
    private static final String TOPIC = "topic_customermanager";

    private KafkaTemplate<String, String> kafkaTemplate;

    public CustomerManagerKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        log.info("Producing message to {} : {}", TOPIC, message);
        this.kafkaTemplate.send(TOPIC, message);
    }
}
