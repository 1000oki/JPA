package com.example.practice.service.kafka;

import com.example.practice.component.KafkaProducer;
import com.example.practice.model.PostRequestModel;
import com.example.practice.model.SendMessageRequestModel;
import com.example.practice.model.TranslateResult;
import com.example.practice.model.song.LyricsModel;
import com.example.practice.model.song.SongEntity;
import com.example.practice.model.song.SongModel;
import com.example.practice.util.HttpConnection;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaProducerService {

    private static final String API_URL = "https://openapi.naver.com/v1/papago/n2mt";

    @Autowired
    private HttpConnection httpConnection;

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @Value(value = "${kafka.topic.name}")
    private String topicName;

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private ResourceLoader resourceLoader;

    public void sendMessage(SendMessageRequestModel sendMessageRequestModel){
        String text = sendMessageRequestModel.getText();
        String beforeLanguage = sendMessageRequestModel.getBeforeLanguage();
        String afterLanguage = sendMessageRequestModel.getAfterLanguage();
        String topic = sendMessageRequestModel.getTopic();

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        log.info("text : {}", text);

        PostRequestModel postRequestModel = new PostRequestModel();
        postRequestModel.setRequestHeaders(requestHeaders);
        postRequestModel.setApiUrl(API_URL);
        postRequestModel.setText(text);
        postRequestModel.setBeforeLanguage(beforeLanguage);
        postRequestModel.setAfterLanguage(afterLanguage);

        String responseBody = httpConnection.post(postRequestModel);

        Gson gson = new Gson();
        TranslateResult translateResult =
                gson.fromJson(responseBody, TranslateResult.class);

        producer.send(topic, translateResult);

    }

    public void sendSong(){
        // FileReader 생성
        Reader reader;
        try {
//            File file = resourceLoader.getResource("classpath:national_anthem.json").getFile();
            File file = resourceLoader.getResource("classpath:idol.json").getFile();
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Json 파일 읽어서, Lecture 객체로 변환
        Gson gson = new Gson();
        SongModel songModel = gson.fromJson(reader, SongModel.class);
        log.info("songModel : {}", songModel);

        Integer verseNum = 0;
        for(LyricsModel verse : songModel.getVerse()){
            verseNum += 1;
            Integer verseRow = 0;
            for(String lyrics : verse.getLyrics()){
                verseRow += 1;
                SongEntity songEntity = new SongEntity();
                songEntity.setSongName(songModel.getSongName());
                songEntity.setVerse(verseNum);
                songEntity.setVerseRow(verseRow);
                songEntity.setLyrics(lyrics);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                producer.songModelSend(topicName, songEntity);
            }
        }
    }
}
