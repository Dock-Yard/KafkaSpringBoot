package com.standalone.java.reactive;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KafkaReactiveConsumerFromDocs {
    public static void main(String[] args) {
        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        ReceiverOptions<Integer, String> receiverOptions =
            ReceiverOptions.<Integer, String>create(consumerProps)
                .subscription(Collections.singleton("airPlaneTopic"));

        KafkaReceiver<Integer, String> kafkaReceiver = KafkaReceiver.create(receiverOptions);

        Flux<ReceiverRecord<Integer, String>> inboundFlux = kafkaReceiver.receive();

        inboundFlux.subscribe(receiverRecord -> {
            System.out.println("Inside inboundFlux::subscribe()::receiverRecord=" + receiverRecord + ", thread id=" + Thread.currentThread().getId() + ", thread id=" + Thread.currentThread().getId() + ", thread name=" + Thread.currentThread().getName());
            receiverRecord.receiverOffset().acknowledge();
        });


    }
}
