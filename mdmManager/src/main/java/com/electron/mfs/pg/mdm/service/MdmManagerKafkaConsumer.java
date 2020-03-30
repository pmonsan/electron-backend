package com.electron.mfs.pg.mdm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MdmManagerKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(MdmManagerKafkaConsumer.class);
    private static final String TOPIC = "topic_mdmmanager";

    @KafkaListener(topics = "topic_mdmmanager", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
