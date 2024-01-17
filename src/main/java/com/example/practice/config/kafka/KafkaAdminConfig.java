package com.example.practice.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class KafkaAdminConfig {
    @Value(value = "${kafka.bootstrap-servers}")
    private String bootstrapServer;
    @Value(value = "${kafka.admin.min-in-sync-replicas}")
    private Integer minInSyncReplicas;
    @Value(value = "${kafka.topic.name}")
    private String topicName;
    @Value(value = "${kafka.topic.num-partitions}")
    private Integer topicNumPartitions;
    @Value(value = "${kafka.topic.replication-factor}")
    private Short topicReplicationFactor;

    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String, Object> props = new HashMap<>();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put("spring.kafka.admin.properties.min.insync.replicas", minInSyncReplicas);
        return new KafkaAdmin(props);
    }

    @Bean
    public NewTopic newTopics(){
        return new NewTopic(topicName, topicNumPartitions, topicReplicationFactor);
    }
}
