package com.example.practice.controller;

import com.example.practice.model.SendMessageRequestModel;
import com.example.practice.service.KafkaProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping(value = "/message")
    @Operation(description = "번역 가능 언어 확인 : https://developers.naver.com/docs/papago/papago-nmt-api-reference.md")
    public ResponseEntity<?> sendMessage(@RequestBody SendMessageRequestModel sendMessageRequestModel){
        kafkaProducerService.sendMessage(sendMessageRequestModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
