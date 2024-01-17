package com.example.practice.service.redis;

import com.example.practice.model.ChatMessage;
import com.example.practice.model.PostRequestModel;
import com.example.practice.model.SendMessageRequestModel;
import com.example.practice.model.TranslateResult;
import com.example.practice.util.HttpConnection;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisPubService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String API_URL = "https://openapi.naver.com/v1/papago/n2mt";

    @Autowired
    private HttpConnection httpConnection;

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    private static final String TOPIC = "topic1";
    private static final String TRANSLATE_TOPIC = "translate";

    public void sendMessage(ChatMessage chatMessage){
        redisTemplate.convertAndSend(TOPIC, chatMessage);
    }

    public void sendMessage(SendMessageRequestModel sendMessageRequestModel){
        String text = sendMessageRequestModel.getText();
        String beforeLanguage = sendMessageRequestModel.getBeforeLanguage();
        String afterLanguage = sendMessageRequestModel.getAfterLanguage();

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

        redisTemplate.convertAndSend(TRANSLATE_TOPIC, translateResult);
    }
}
