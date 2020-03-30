package com.electron.mfs.pg.pg8583client.web.rest;

import com.electron.mfs.pg.pg8583client.Pg8583ClientApp;
import com.electron.mfs.pg.pg8583client.service.Pg8583ClientKafkaProducer;
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
@SpringBootTest(classes = Pg8583ClientApp.class)
public class Pg8583ClientKafkaResourceIT {

    @Autowired
    private Pg8583ClientKafkaProducer kafkaProducer;

    private MockMvc restMockMvc;

    @BeforeEach
    public void setup() {
        Pg8583ClientKafkaResource kafkaResource = new Pg8583ClientKafkaResource(kafkaProducer);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(kafkaResource)
            .build();
    }

    @Test
    public void sendMessageToKafkaTopic() throws Exception {
        restMockMvc.perform(post("/api/pg-8583-client-kafka/publish?message=yolo"))
            .andExpect(status().isOk());
    }
}
