package com.electron.mfs.pg.iam.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class IamManagerKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(IamManagerKafkaConsumer.class);
    private static final String TOPIC = "topic_iammanager";

    @KafkaListener(topics = "topic_iammanager", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
