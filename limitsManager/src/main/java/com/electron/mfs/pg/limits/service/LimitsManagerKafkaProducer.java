package com.electron.mfs.pg.limits.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LimitsManagerKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(LimitsManagerKafkaProducer.class);
    private static final String TOPIC = "topic_limitsmanager";

    private KafkaTemplate<String, String> kafkaTemplate;

    public LimitsManagerKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        log.info("Producing message to {} : {}", TOPIC, message);
        this.kafkaTemplate.send(TOPIC, message);
    }
}
