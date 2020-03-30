package com.electron.mfs.pg.services.web.rest;

import com.electron.mfs.pg.services.service.ServicesManagerKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/services-manager-kafka")
public class ServicesManagerKafkaResource {

    private final Logger log = LoggerFactory.getLogger(ServicesManagerKafkaResource.class);

    private ServicesManagerKafkaProducer kafkaProducer;

    public ServicesManagerKafkaResource(ServicesManagerKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
