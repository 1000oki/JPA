package com.example.practice.service.redis;

import com.example.practice.Repository.SongRepository;
import com.example.practice.model.song.SongEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SongRedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SongRepository songRepository;

    @Transactional
    public void insertSong(){
        log.info("[SongRedisService.insertSong] mysql에서 모든 데이터 가져오기 start");
        Sort sort = Sort.by(Sort.Direction.ASC, "songName", "verse", "verseRow");
        List<SongEntity> songs= songRepository.findAll(sort);
        log.info("[SongRedisService.insertSong] mysql에서 모든 데이터 가져오기 end");

        log.info("[SongRedisService.insertSong] 가져온 List형식 노래명이 key인 Map형식으로 변경 start");
        Map<String, List<SongEntity>> songToMap = songs.stream().collect(Collectors.groupingBy(SongEntity::getSongName));
        log.info("songtoMap:{}", songToMap);
        log.info("[SongRedisService.insertSong] 가져온 List형식 노래명이 key인 Map형식으로 변경 end");

        log.info("[SongRedisService.insertSong] 같은 노래끼리 절별로 Map형식으로 변경 후 String으로 변경 start");
        for (Map.Entry<String, List<SongEntity>> song : songToMap.entrySet()) {
            // 절이 key인 Map형식으로 변경
            Map<Integer, List<SongEntity>> songDivideMap = song.getValue().stream().collect(Collectors.groupingBy(SongEntity::getVerse));
            List<String> verse = new ArrayList<>();
            // 각 절별로 가사 합쳐서 string으로 변경
            for(Map.Entry<Integer, List<SongEntity>> songDivide : songDivideMap.entrySet()){
                String lyrics = new String();
                for(SongEntity songentity : songDivide.getValue()){
                    lyrics += songentity.getLyrics() + " ";
                }
                verse.add(lyrics);
                log.info("{}노래 {}절 : {}", song.getKey(), songDivide.getKey(), lyrics);

                log.info("redis 저장 start");
                HashOperations<String, String,String> list = redisTemplate.opsForHash();
                list.put(song.getKey().toString(), songDivide.getKey().toString().trim(), lyrics.toString().trim());
                log.info("redis 저장 end");
            }
        }
    }

    public void selectSong(){
         HashOperations<String, String,String> list = redisTemplate.opsForHash();
         Long size = list.size("아이돌");
         String lyrics = new String();
         for(int i=1; i<=size; i++){
             if(i!=1){
                 lyrics += " ";
             }
             lyrics += list.get("아이돌", String.format("%s", i));
         }

         log.info("[SongRedisService.selectSong] {}", lyrics);
    }

}
