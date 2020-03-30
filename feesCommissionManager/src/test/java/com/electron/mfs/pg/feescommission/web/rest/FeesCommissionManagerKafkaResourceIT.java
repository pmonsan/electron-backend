package com.electron.mfs.pg.feescommission.web.rest;

import com.electron.mfs.pg.feescommission.FeesCommissionManagerApp;
import com.electron.mfs.pg.feescommission.service.FeesCommissionManagerKafkaProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EmbeddedKafka
@SpringBootTest(classes = FeesCommissionManagerApp.class)
public class FeesCommissionManagerKafkaResourceIT {

    @Autowired
    private FeesCommissionManagerKafkaProducer kafkaProducer;

    private MockMvc restMockMvc;

    @BeforeEach
    public void setup() {
        FeesCommissionManagerKafkaResource kafkaResource = new FeesCommissionManagerKafkaResource(kafkaProducer);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(kafkaResource)
            .build();
    }

    @Test
    public void sendMessageToKafkaTopic() throws Exception {
        restMockMvc.perform(post("/api/fees-commission-manager-kafka/publish?message=yolo"))
            .andExpect(status().isOk());
    }
}
