package com.electron.mfs.pg.subscription.web.rest;

import com.electron.mfs.pg.subscription.SubscriptionManagerApp;
import com.electron.mfs.pg.subscription.service.SubscriptionManagerKafkaProducer;
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
@SpringBootTest(classes = SubscriptionManagerApp.class)
public class SubscriptionManagerKafkaResourceIT {

    @Autowired
    private SubscriptionManagerKafkaProducer kafkaProducer;

    private MockMvc restMockMvc;

    @BeforeEach
    public void setup() {
        SubscriptionManagerKafkaResource kafkaResource = new SubscriptionManagerKafkaResource(kafkaProducer);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(kafkaResource)
            .build();
    }

    @Test
    public void sendMessageToKafkaTopic() throws Exception {
        restMockMvc.perform(post("/api/subscription-manager-kafka/publish?message=yolo"))
            .andExpect(status().isOk());
    }
}
