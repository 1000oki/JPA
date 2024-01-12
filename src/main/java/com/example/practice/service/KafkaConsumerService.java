package com.example.practice.service;

import com.example.practice.model.Result;
import com.example.practice.model.TranslateResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {
    @KafkaListener(topics="first", groupId = "cjs")
    public void consume(ConsumerRecord<String, TranslateResult> consumerRecord){
        TranslateResult translateResult = consumerRecord.value();
        String text = translateResult.getMessage().getResult().getTranslatedText();
        String beforeLanguage = translateResult.getMessage().getResult().getSrcLangType();
        String afterLanguage = translateResult.getMessage().getResult().getTarLangType();

        log.info("beforeLanguage : {}, afterLanguage : {}, translate messager : {}", beforeLanguage, afterLanguage, text);
    }
}
