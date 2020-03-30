package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.service.MdmManagerKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/mdm-manager-kafka")
public class MdmManagerKafkaResource {

    private final Logger log = LoggerFactory.getLogger(MdmManagerKafkaResource.class);

    private MdmManagerKafkaProducer kafkaProducer;

    public MdmManagerKafkaResource(MdmManagerKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
