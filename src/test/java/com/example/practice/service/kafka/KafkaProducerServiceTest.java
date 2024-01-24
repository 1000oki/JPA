package com.example.practice.service.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(properties = "spring.profiles.active=local")
class KafkaProducerServiceTest {
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Test
    void sendSong() {
        kafkaProducerService.sendSong("idol");
    }
}