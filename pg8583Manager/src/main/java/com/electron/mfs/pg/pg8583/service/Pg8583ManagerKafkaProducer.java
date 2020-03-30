package com.electron.mfs.pg.pg8583.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Pg8583ManagerKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(Pg8583ManagerKafkaProducer.class);
    private static final String TOPIC = "topic_pg8583manager";

    private KafkaTemplate<String, String> kafkaTemplate;

    public Pg8583ManagerKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        log.info("Producing message to {} : {}", TOPIC, message);
        this.kafkaTemplate.send(TOPIC, message);
    }
}
