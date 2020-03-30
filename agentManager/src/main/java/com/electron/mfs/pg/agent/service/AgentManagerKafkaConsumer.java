package com.electron.mfs.pg.agent.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AgentManagerKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(AgentManagerKafkaConsumer.class);
    private static final String TOPIC = "topic_agentmanager";

    @KafkaListener(topics = "topic_agentmanager", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
