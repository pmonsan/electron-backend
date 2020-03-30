package com.electron.mfs.pg.transactions.web.rest;

import com.electron.mfs.pg.transactions.service.TransactionManagerKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/transaction-manager-kafka")
public class TransactionManagerKafkaResource {

    private final Logger log = LoggerFactory.getLogger(TransactionManagerKafkaResource.class);

    private TransactionManagerKafkaProducer kafkaProducer;

    public TransactionManagerKafkaResource(TransactionManagerKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
