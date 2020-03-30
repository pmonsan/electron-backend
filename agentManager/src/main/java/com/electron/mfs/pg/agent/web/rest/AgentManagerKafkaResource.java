package com.electron.mfs.pg.agent.web.rest;

import com.electron.mfs.pg.agent.service.AgentManagerKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/agent-manager-kafka")
public class AgentManagerKafkaResource {

    private final Logger log = LoggerFactory.getLogger(AgentManagerKafkaResource.class);

    private AgentManagerKafkaProducer kafkaProducer;

    public AgentManagerKafkaResource(AgentManagerKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
