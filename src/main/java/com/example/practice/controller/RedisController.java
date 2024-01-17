package com.example.practice.controller;

import com.example.practice.model.ChatMessage;
import com.example.practice.model.SendMessageRequestModel;
import com.example.practice.service.redis.RedisPubService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final RedisPubService redisPubService;

    @PostMapping("/redis-test")
    public ResponseEntity<?> addRedisKey(){
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set("yellow", "banana");
        vop.set("red", "apple");
        vop.set("green", "watermelon");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/redis-test/{key}")
    public ResponseEntity<?> getRedisKey(@PathVariable(value = "key", required = true) String key){
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String value = vop.get(key);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    @PostMapping("/redis-test/chat")
    public ResponseEntity<?> pubSub(@RequestBody ChatMessage chatMessage){
        redisPubService.sendMessage(chatMessage);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/redis-test/translate")
    public ResponseEntity<?> translatePubSub(@RequestBody SendMessageRequestModel sendMessageRequestModel){
        redisPubService.sendMessage(sendMessageRequestModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
