package com.example.practice.service;

import com.example.practice.model.TranslateResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslateRedisSubService implements MessageListener {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try{
            TranslateResult translateResult = mapper.readValue(message.getBody(), TranslateResult.class);
            String text = translateResult.getMessage().getResult().getTranslatedText();

            log.info("translateResult ? {}", translateResult);
            log.info("translateText ? {}", text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
