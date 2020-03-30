package com.electron.mfs.pg.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AccountManagerKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(AccountManagerKafkaConsumer.class);
    private static final String TOPIC = "topic_accountmanager";

    @KafkaListener(topics = "topic_accountmanager", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
