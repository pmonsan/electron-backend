package com.electron.mfs.pg.limits.web.rest;

import com.electron.mfs.pg.limits.service.LimitsManagerKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/limits-manager-kafka")
public class LimitsManagerKafkaResource {

    private final Logger log = LoggerFactory.getLogger(LimitsManagerKafkaResource.class);

    private LimitsManagerKafkaProducer kafkaProducer;

    public LimitsManagerKafkaResource(LimitsManagerKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
