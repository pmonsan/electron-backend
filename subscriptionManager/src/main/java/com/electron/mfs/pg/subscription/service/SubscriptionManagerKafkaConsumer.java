package com.electron.mfs.pg.subscription.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SubscriptionManagerKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(SubscriptionManagerKafkaConsumer.class);
    private static final String TOPIC = "topic_subscriptionmanager";

    @KafkaListener(topics = "topic_subscriptionmanager", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
