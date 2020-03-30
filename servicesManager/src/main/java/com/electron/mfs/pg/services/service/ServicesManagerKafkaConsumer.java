package com.electron.mfs.pg.services.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ServicesManagerKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(ServicesManagerKafkaConsumer.class);
    private static final String TOPIC = "topic_servicesmanager";

    @KafkaListener(topics = "topic_servicesmanager", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
