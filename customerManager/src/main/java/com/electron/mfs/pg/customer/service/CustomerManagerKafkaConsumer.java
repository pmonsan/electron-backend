package com.electron.mfs.pg.customer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomerManagerKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(CustomerManagerKafkaConsumer.class);
    private static final String TOPIC = "topic_customermanager";

    @KafkaListener(topics = "topic_customermanager", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
