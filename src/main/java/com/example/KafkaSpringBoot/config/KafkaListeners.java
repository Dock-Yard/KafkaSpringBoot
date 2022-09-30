package com.example.KafkaSpringBoot.config;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "airPlaneTopic", groupId = "groupId")
    public void listener(String data){
        System.out.printf("Inside KafkaListeners::listener::received data=" + data + "\n");
    }

}
