package com.standalone.java.standard.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaClientProducer {

    public static void main(String[] args) {

        System.out.println("KafkaClientConsumer::main()::START");

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", StringSerializer.class);
        properties.put("value.serializer", StringSerializer.class);

        try(KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);){

            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("airPlaneTopic", "wuuuuuu");

            Future<RecordMetadata> future = kafkaProducer.send(producerRecord, (metadata, exception) -> {
                if(exception != null){
                    System.out.println("some thing wrong");
                    exception.printStackTrace();
                }
                else{
                    System.out.println("Successfully transmitted");
                }
            });

            System.out.println("KafkaClientConsumer::main()::sent" + future.get());//future.get() is needed as else the sending thread also dies along with the main thread

            System.out.println("KafkaClientConsumer::main()::DONE");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
