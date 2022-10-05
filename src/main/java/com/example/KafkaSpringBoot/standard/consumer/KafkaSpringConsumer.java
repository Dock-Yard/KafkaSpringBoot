package com.example.KafkaSpringBoot.standard.consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

@Configuration
public class KafkaSpringConsumer {

    @Bean
    public KafkaMessageListenerContainer<String, String> kafkaMessageListenerContainer(
            ConsumerFactory<String, String> consumerFactory, ContainerProperties containerProperties
    ){
        System.out.println("Inside KafkaConsumerConfig:kafkaMessageListenerContainer()");
        return new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
    }
}
