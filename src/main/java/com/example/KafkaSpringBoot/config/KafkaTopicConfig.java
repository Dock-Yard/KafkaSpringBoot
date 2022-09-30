package com.example.KafkaSpringBoot.config;

import com.example.KafkaSpringBoot.pojo.AirPlane;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic airPlaneTopic(){
        try{
            System.out.printf("Inside KafkaTopicConfig::airPlaneTopic()");
            return TopicBuilder.name("airPlaneTopic").build();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public AirPlane newAirplane(){
        try {
            System.out.printf("Inside KafkaTopicConfig::newAirplane()");
            AirPlane airPlane = new AirPlane();
            airPlane.setId(123);
            airPlane.setCompany("Indigo");
            return airPlane;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
