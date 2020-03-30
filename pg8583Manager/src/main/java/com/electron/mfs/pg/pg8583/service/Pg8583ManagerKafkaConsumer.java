package com.electron.mfs.pg.pg8583.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Pg8583ManagerKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(Pg8583ManagerKafkaConsumer.class);
    private static final String TOPIC = "topic_pg8583manager";

    @KafkaListener(topics = "topic_pg8583manager", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
