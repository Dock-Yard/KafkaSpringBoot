package com.example.KafkaSpringBoot.standard.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "spring.producer.enabled", havingValue = "true", matchIfMissing = false)
@RequiredArgsConstructor
public class KafkaSpringProducer {

    @Value("${producer.topic.name}")
    private String topicName;

    @Value("${producer.message}")
    private String message;

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Sends message to Kafka
     */
    @EventListener(ApplicationStartedEvent.class)
    public void produce() {
        try {
            System.out.println("Inside KafkaProducer:produce()");
            kafkaTemplate.send(topicName, message);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
