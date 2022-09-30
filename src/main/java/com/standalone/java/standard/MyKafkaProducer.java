package com.standalone.java.standard;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;
import java.util.concurrent.Future;

public class MyKafkaProducer {

    public static void main(String[] args) {
        try{
            System.out.println("MyKafkaProducer::main()::START");

            Properties properties = new Properties();
            properties.put("bootstrap.servers", "localhost:9092");
            properties.put("key.serializer", StringSerializer.class);
            properties.put("value.serializer", StringSerializer.class);


            KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("airPlaneTopic", "from standalone java");

            Future<RecordMetadata> future = kafkaProducer.send(producerRecord, (metadata, exception) -> {
                if(exception != null){
                    System.out.printf("some thing wrong");
                    exception.printStackTrace();
                }
                else{
                    System.out.println("Successfully transmitted");
                }
            });

            System.out.println("MyKafkaProducer::main()::sent" + future.get());//future.get() is needed as else the sending thread also dies along with the main thread

            kafkaProducer.close();;

            System.out.println("MyKafkaProducer::main()::DONE");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
