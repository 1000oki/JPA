package com.example.practice.component;

import com.example.practice.controller.KafkaController;
import com.example.practice.model.TranslateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, TranslateResult> kafkaTemplate;

    public void send(String topic, TranslateResult translateResult){
        log.info("send topic : {}, translateResult : {}", topic, translateResult);
        kafkaTemplate.send(topic, translateResult);
    }
}
