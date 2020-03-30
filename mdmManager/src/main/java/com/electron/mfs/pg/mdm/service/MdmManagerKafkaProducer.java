package com.electron.mfs.pg.mdm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MdmManagerKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(MdmManagerKafkaProducer.class);
    private static final String TOPIC = "topic_mdmmanager";

    private KafkaTemplate<String, String> kafkaTemplate;

    public MdmManagerKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        log.info("Producing message to {} : {}", TOPIC, message);
        this.kafkaTemplate.send(TOPIC, message);
    }
}
