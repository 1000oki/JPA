package com.example.practice.config.kafka;

import com.example.practice.model.TranslateResult;
import com.example.practice.model.song.InsertSongModel;
import com.example.practice.model.song.SongModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaProducerConfig {
    @Value(value = "${kafka.bootstrap-servers}")
    private String bootstrapServer;
    @Value(value = "${kafka.producer.acks}")
    private String acks;
    @Value(value = "${kafka.producer.retry}")
    private Integer retry;
    @Value(value = "${kafka.producer.max-in-flight-requests-per-connection}")
    private Integer maxInFlightRequestsPerConnection;
    @Bean
    public ProducerFactory<String, TranslateResult> factory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public ProducerFactory<String, InsertSongModel> mysqlFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        // 멱등성(중복 방지) 적용 true일 경우 acks=all, retries>0, max.in.flight.requests.per.connection <= 5
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.ACKS_CONFIG, acks); // acknowledgement 설정 (all, 0, 1)
        props.put(ProducerConfig.RETRIES_CONFIG, retry); // acks 신호를 받지 못할 경우 몇번까지 retry 할 지
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInFlightRequestsPerConnection);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, TranslateResult> kafkaTemplate(){
        return new KafkaTemplate<>(factory());
    }

    @Bean
    public KafkaTemplate<String, InsertSongModel> mysqlKafkaTemplate(){
        return new KafkaTemplate<>(mysqlFactory());
    }
}
