package com.electron.mfs.pg.transactions.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TransactionManagerKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(TransactionManagerKafkaConsumer.class);
    private static final String TOPIC = "topic_transactionmanager";

    @KafkaListener(topics = "topic_transactionmanager", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
