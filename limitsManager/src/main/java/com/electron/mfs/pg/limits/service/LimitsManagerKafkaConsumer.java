package com.electron.mfs.pg.limits.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LimitsManagerKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(LimitsManagerKafkaConsumer.class);
    private static final String TOPIC = "topic_limitsmanager";

    @KafkaListener(topics = "topic_limitsmanager", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
