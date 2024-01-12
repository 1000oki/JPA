package com.example.practice.config;

import com.example.practice.util.HttpConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {
    @Bean
    public HttpConnection httpConnection(){
        return new HttpConnection();
    }
}
