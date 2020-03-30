package com.electron.mfs.pg.feescommission.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FeesCommissionManagerKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(FeesCommissionManagerKafkaConsumer.class);
    private static final String TOPIC = "topic_feescommissionmanager";

    @KafkaListener(topics = "topic_feescommissionmanager", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
