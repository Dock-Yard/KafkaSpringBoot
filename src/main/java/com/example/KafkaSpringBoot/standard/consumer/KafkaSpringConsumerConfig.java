package com.example.KafkaSpringBoot.standard.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaSpringConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${consumer.topic.name}")
    private String topicName;

    public Map<String, Object> consumerConfig(){
        System.out.println("Inside KafkaSpringConsumerConfig:consumerConfig()");
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "groupId");
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory(){
        System.out.println("Inside KafkaSpringConsumerConfig:consumerFactory()");
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public ContainerProperties containerProperties(){
        System.out.println("Inside KafkaSpringConsumerConfig:containerProperties()");
        ContainerProperties containerProperties = new ContainerProperties(topicName);

        containerProperties.setMessageListener(
                (MessageListener<String, String>) data ->
                        System.out.println("Inside ContainerProperties::onMessage::data=" + data));

        return containerProperties;
    }

}
