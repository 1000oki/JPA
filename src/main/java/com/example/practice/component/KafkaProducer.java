package com.example.practice.component;

import com.example.practice.model.TranslateResult;
import com.example.practice.model.song.SongEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, TranslateResult> kafkaTemplate;
    private final KafkaTemplate<String, SongEntity> songModelkafkaTemplate;

    public void send(String topic, TranslateResult translateResult){
        log.info("send topic : {}, translateResult : {}", topic, translateResult);
        kafkaTemplate.send(topic, translateResult);
    }

    public void songModelSend(String topic, SongEntity songEntity){
        log.info("send topic : {}, SongModel : {}", topic, songEntity);
        songModelkafkaTemplate.send(topic, songEntity);
    }
}
