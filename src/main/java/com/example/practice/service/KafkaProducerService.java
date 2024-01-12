package com.example.practice.service;

import com.example.practice.component.KafkaProducer;
import com.example.practice.model.PostRequestModel;
import com.example.practice.model.SendMessageRequestModel;
import com.example.practice.model.TranslateResult;
import com.example.practice.util.HttpConnection;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaProducerService {
    private static final String TOPIC = "first";

    private static final String API_URL = "https://openapi.naver.com/v1/papago/n2mt";

    @Autowired
    private HttpConnection httpConnection;

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    @Autowired
    private KafkaProducer producer;


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

        producer.send(TOPIC, translateResult);

    }
}
