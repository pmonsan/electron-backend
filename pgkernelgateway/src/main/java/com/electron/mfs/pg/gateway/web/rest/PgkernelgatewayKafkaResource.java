package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.service.PgkernelgatewayKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/pgkernelgateway-kafka")
public class PgkernelgatewayKafkaResource {

    private final Logger log = LoggerFactory.getLogger(PgkernelgatewayKafkaResource.class);

    private PgkernelgatewayKafkaProducer kafkaProducer;

    public PgkernelgatewayKafkaResource(PgkernelgatewayKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
