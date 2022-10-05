package com.example.KafkaSpringBoot.standard.consumer.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

@Configuration
public class KafkaSpringAnnotationConsumer {
    /*@Bean
    public KafkaMessageListenerContainer<String, String> kafkaMessageListenerContainerForAnnotation(
            ConsumerFactory<String, String> consumerFactoryForAnnotation, ContainerProperties containerPropertiesForAnnotation
    ) {
        System.out.println("Inside KafkaSpringAnnotationConsumer:kafkaMessageListenerContainerForAnnotation()\n" +
                "consumerFactoryHash=" + consumerFactoryForAnnotation.hashCode() +
                ", containerPropertiesHash=" + containerPropertiesForAnnotation.hashCode());

        return new KafkaMessageListenerContainer<>(consumerFactoryForAnnotation, containerPropertiesForAnnotation);
    }*/


}
