package com.electron.mfs.pg.account.web.rest;

import com.electron.mfs.pg.account.service.AccountManagerKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/account-manager-kafka")
public class AccountManagerKafkaResource {

    private final Logger log = LoggerFactory.getLogger(AccountManagerKafkaResource.class);

    private AccountManagerKafkaProducer kafkaProducer;

    public AccountManagerKafkaResource(AccountManagerKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
