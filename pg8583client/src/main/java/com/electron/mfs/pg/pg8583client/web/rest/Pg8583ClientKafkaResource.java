package com.electron.mfs.pg.pg8583client.web.rest;

import com.electron.mfs.pg.pg8583client.service.Pg8583ClientKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pg-8583-client-kafka")
public class Pg8583ClientKafkaResource {

    private final Logger log = LoggerFactory.getLogger(Pg8583ClientKafkaResource.class);

    private Pg8583ClientKafkaProducer kafkaProducer;

    public Pg8583ClientKafkaResource(Pg8583ClientKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
