package com.electron.mfs.pg.customer.web.rest;

import com.electron.mfs.pg.customer.service.CustomerManagerKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/customer-manager-kafka")
public class CustomerManagerKafkaResource {

    private final Logger log = LoggerFactory.getLogger(CustomerManagerKafkaResource.class);

    private CustomerManagerKafkaProducer kafkaProducer;

    public CustomerManagerKafkaResource(CustomerManagerKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
