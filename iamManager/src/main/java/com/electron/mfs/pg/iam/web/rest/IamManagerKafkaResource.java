package com.electron.mfs.pg.iam.web.rest;

import com.electron.mfs.pg.iam.service.IamManagerKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/iam-manager-kafka")
public class IamManagerKafkaResource {

    private final Logger log = LoggerFactory.getLogger(IamManagerKafkaResource.class);

    private IamManagerKafkaProducer kafkaProducer;

    public IamManagerKafkaResource(IamManagerKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
