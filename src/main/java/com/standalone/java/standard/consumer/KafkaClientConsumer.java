package com.standalone.java.standard.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class KafkaClientConsumer {

    public static void main(String[] args) {

        System.out.println("KafkaClientConsumer::main()::START");

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "dummy_value");

        String topicName = "airPlaneTopic";

        try(KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties)){

            System.out.println("Created kafkaConsumer");

            kafkaConsumer.subscribe(List.of(topicName), new ConsumerRebalanceListener() {
                @Override
                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                    System.out.println("Inside onPartitionsRevoked()::partitions=" + partitions);
                }
                @Override
                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                    System.out.println("Inside onPartitionsAssigned()::partitions=" + partitions);
                }
            });
            System.out.println("Subscribed to kafkaConsumer");

            int received = 0;
            int expected = 1;
            int count = 0;
            while(received < expected){
                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
                System.out.println("polled=" + (count + 1));
                for(ConsumerRecord<String, String> consumerRecord : consumerRecords){
                    System.out.println("Received data=" + consumerRecord);
                    received++;
                }
                count++;
            }

            System.out.println("KafkaClientConsumer::main()::DONE");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
