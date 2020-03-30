package com.electron.mfs.pg.feescommission.web.rest;

import com.electron.mfs.pg.feescommission.service.FeesCommissionManagerKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/fees-commission-manager-kafka")
public class FeesCommissionManagerKafkaResource {

    private final Logger log = LoggerFactory.getLogger(FeesCommissionManagerKafkaResource.class);

    private FeesCommissionManagerKafkaProducer kafkaProducer;

    public FeesCommissionManagerKafkaResource(FeesCommissionManagerKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
