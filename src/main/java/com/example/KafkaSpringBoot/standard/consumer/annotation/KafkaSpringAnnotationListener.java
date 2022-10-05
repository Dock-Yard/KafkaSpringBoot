package com.example.KafkaSpringBoot.standard.consumer.annotation;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaSpringAnnotationListener {

    @KafkaListener(topics = {"airPlaneAnnotationTopic"}, groupId = "groupId2")
    public void listen(String data){
        System.out.println("Inside KafkaSpringAnnotationListener::listen::for Annotation::"
                + "thread=" + Thread.currentThread().getId()
                + ", data=" + data);
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

}
