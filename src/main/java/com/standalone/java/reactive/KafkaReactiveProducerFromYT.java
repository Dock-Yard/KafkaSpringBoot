package com.standalone.java.reactive;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;
import java.time.Instant;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class KafkaReactiveProducerFromYT {

    public static void main(String[] args) {
        try{
            System.out.println("KafkaReactiveProducer::main()::START");

            Properties properties = new Properties();
            properties.put("bootstrap.servers", "localhost:9092");
            properties.put("key.serializer", IntegerSerializer.class);
            properties.put("value.serializer", StringSerializer.class);

            String topic = "airPlaneTopic";
            int count = 5;
            CountDownLatch latch = new CountDownLatch(count);

            KafkaSender<Integer, String> kafkaSender = KafkaSender.create(SenderOptions.create(properties));

            /*Flux<RecordMetadata> flux = Flux.range(1, count)
                            .flatMap(
                                    i ->
        //kafkaSender.send(SenderRecord.create(new ProducerRecord<>(topic, i, "Message_" + i), i)
        *//*kafkaSender.send(new ProducerRecord<>(topic, i, ("Message_" + i), i)
                            )
                            .doOnNext(metadata -> {
                                System.out.println("Metadata=" + metadata);
                                latch.countDown();
                            })
                            .doOnError(Throwable::printStackTrace)
                    .subscribe();*//*

            //latch.await(10, TimeUnit.SECONDS);
            kafkaSender.close();*/

            System.out.println("KafkaReactiveProducer::main()::DONE");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
