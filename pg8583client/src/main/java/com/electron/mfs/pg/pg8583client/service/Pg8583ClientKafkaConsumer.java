package com.electron.mfs.pg.pg8583client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Pg8583ClientKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(Pg8583ClientKafkaConsumer.class);
    private static final String TOPIC = "topic_pg8583client";

    @KafkaListener(topics = "topic_pg8583client", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
