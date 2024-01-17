package com.example.practice.service.redis;

import com.example.practice.model.ChatMessage;
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
public class RedisSubService implements MessageListener {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try{
            ChatMessage chatMessage = mapper.readValue(message.getBody(), ChatMessage.class);

            log.info("받은 메세지 ? {}", message.toString());
            log.info("chatMessage.getSender() ? {}", chatMessage.getSender());
            log.info("chatMessage.getContext() ? {}", chatMessage.getContext());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
