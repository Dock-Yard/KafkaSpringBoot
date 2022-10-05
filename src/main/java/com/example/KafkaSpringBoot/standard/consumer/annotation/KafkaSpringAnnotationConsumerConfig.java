package com.example.KafkaSpringBoot.standard.consumer.annotation;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaSpringAnnotationConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${consumer.annotation.topic.name}")
    private String topicName;

    public Map<String, Object> consumerConfigForAnnotation(){
        System.out.println("Inside KafkaSpringAnnotationConsumerConfig:consumerConfigForAnnotation()");
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "groupId2");
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactoryForAnnotation(){
        System.out.println("Inside KafkaSpringAnnotationConsumerConfig:consumerFactoryForAnnotation()");
        return new DefaultKafkaConsumerFactory<>(consumerConfigForAnnotation());
    }

    /*@Bean
    public ContainerProperties containerPropertiesForAnnotation(){
        System.out.println("Inside KafkaSpringAnnotationConsumerConfig:containerPropertiesForAnnotation()");
        ContainerProperties containerProperties = new ContainerProperties(topicName);

        containerProperties.setMessageListener(
                (MessageListener<String, String>) data ->
                        System.out.println("Inside MessageListener::onMessage::for Annotation::"
                                + "thread=" + Thread.currentThread().getId()
                                + "data=" + data));

        return containerProperties;
    }*/


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,String>> listenerContainerFactory(
            ConsumerFactory<String, String> consumerFactoryForAnnotation){
        ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactoryForAnnotation);
        return concurrentKafkaListenerContainerFactory;
    }
}
