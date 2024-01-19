package com.example.practice.service.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.profiles.active=local")
class SongRedisServiceTest {
    @Autowired
    SongRedisService songRedisService;

    @Test
    void insertSong() {
        songRedisService.insertSong();
    }

    @Test
    void selectSong(){
        songRedisService.selectSong();
    }
}