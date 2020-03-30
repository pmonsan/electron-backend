package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.Pg8583ManagerApp;
import com.electron.mfs.pg.pg8583.service.Pg8583ManagerKafkaProducer;
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
@SpringBootTest(classes = Pg8583ManagerApp.class)
public class Pg8583ManagerKafkaResourceIT {

    @Autowired
    private Pg8583ManagerKafkaProducer kafkaProducer;

    private MockMvc restMockMvc;

    @BeforeEach
    public void setup() {
        Pg8583ManagerKafkaResource kafkaResource = new Pg8583ManagerKafkaResource(kafkaProducer);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(kafkaResource)
            .build();
    }

    @Test
    public void sendMessageToKafkaTopic() throws Exception {
        restMockMvc.perform(post("/api/pg-8583-manager-kafka/publish?message=yolo"))
            .andExpect(status().isOk());
    }
}
