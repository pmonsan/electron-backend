package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.service.Pg8583ManagerKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pg-8583-manager-kafka")
public class Pg8583ManagerKafkaResource {

    private final Logger log = LoggerFactory.getLogger(Pg8583ManagerKafkaResource.class);

    private Pg8583ManagerKafkaProducer kafkaProducer;

    public Pg8583ManagerKafkaResource(Pg8583ManagerKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
