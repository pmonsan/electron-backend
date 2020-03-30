package com.electron.mfs.pg.subscription.web.rest;

import com.electron.mfs.pg.subscription.service.SubscriptionManagerKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/subscription-manager-kafka")
public class SubscriptionManagerKafkaResource {

    private final Logger log = LoggerFactory.getLogger(SubscriptionManagerKafkaResource.class);

    private SubscriptionManagerKafkaProducer kafkaProducer;

    public SubscriptionManagerKafkaResource(SubscriptionManagerKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
