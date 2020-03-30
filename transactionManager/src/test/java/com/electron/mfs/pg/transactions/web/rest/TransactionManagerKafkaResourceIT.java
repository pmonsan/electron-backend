package com.electron.mfs.pg.transactions.web.rest;

import com.electron.mfs.pg.transactions.TransactionManagerApp;
import com.electron.mfs.pg.transactions.service.TransactionManagerKafkaProducer;
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
@SpringBootTest(classes = TransactionManagerApp.class)
public class TransactionManagerKafkaResourceIT {

    @Autowired
    private TransactionManagerKafkaProducer kafkaProducer;

    private MockMvc restMockMvc;

    @BeforeEach
    public void setup() {
        TransactionManagerKafkaResource kafkaResource = new TransactionManagerKafkaResource(kafkaProducer);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(kafkaResource)
            .build();
    }

    @Test
    public void sendMessageToKafkaTopic() throws Exception {
        restMockMvc.perform(post("/api/transaction-manager-kafka/publish?message=yolo"))
            .andExpect(status().isOk());
    }
}
