package com.example.practice.config.redis;

import com.example.practice.model.ChatMessage;
import com.example.practice.service.redis.RedisSubService;
import com.example.practice.service.redis.TranslateRedisSubService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory(redisHost, redisPort);
    }

    // redisTemplate 설정
    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    // 리스너 어댑터 설정
    @Bean
    MessageListenerAdapter messageListenerAdapter(){
        return new MessageListenerAdapter(new RedisSubService());
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter2(){
        return new MessageListenerAdapter(new TranslateRedisSubService());
    }

    @Bean
    MessageListenerAdapter translateMessageListenerAdapter(){return new MessageListenerAdapter(new TranslateRedisSubService());}

    // 컨테이너 설정
    @Bean
    RedisMessageListenerContainer redisContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListenerAdapter(), topic());
        container.addMessageListener(messageListenerAdapter2(), topic());
        container.addMessageListener(translateMessageListenerAdapter(), translateTopic());
        return container;
    }

    // pub/sub 토픽 설정
    @Bean
    ChannelTopic topic(){
        return new ChannelTopic("topic1");
    }

    @Bean
    ChannelTopic translateTopic(){
        return new ChannelTopic("translate");
    }

}
