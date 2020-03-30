package com.electron.mfs.pg.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountManagerKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(AccountManagerKafkaProducer.class);
    private static final String TOPIC = "topic_accountmanager";

    private KafkaTemplate<String, String> kafkaTemplate;

    public AccountManagerKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        log.info("Producing message to {} : {}", TOPIC, message);
        this.kafkaTemplate.send(TOPIC, message);
    }
}
