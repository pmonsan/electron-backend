package com.electron.mfs.pg.transactions.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionManagerKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(TransactionManagerKafkaProducer.class);
    private static final String TOPIC = "topic_transactionmanager";

    private KafkaTemplate<String, String> kafkaTemplate;

    public TransactionManagerKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        log.info("Producing message to {} : {}", TOPIC, message);
        this.kafkaTemplate.send(TOPIC, message);
    }
}
