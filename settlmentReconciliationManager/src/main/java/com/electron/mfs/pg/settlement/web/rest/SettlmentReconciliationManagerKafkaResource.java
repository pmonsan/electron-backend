package com.electron.mfs.pg.settlement.web.rest;

import com.electron.mfs.pg.settlement.service.SettlmentReconciliationManagerKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/settlment-reconciliation-manager-kafka")
public class SettlmentReconciliationManagerKafkaResource {

    private final Logger log = LoggerFactory.getLogger(SettlmentReconciliationManagerKafkaResource.class);

    private SettlmentReconciliationManagerKafkaProducer kafkaProducer;

    public SettlmentReconciliationManagerKafkaResource(SettlmentReconciliationManagerKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
