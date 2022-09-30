package com.standalone.java.reactive;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MyKafkaReactiveProducer {
    static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss:SSS z dd MMM yyyy");

    public static void main(String[] args) {
        try{
            System.out.println("MyKafkaReactiveProducer::main()::START");

            Properties properties = new Properties();
            properties.put("bootstrap.servers", "localhost:9092");
            properties.put("key.serializer", IntegerSerializer.class);
            properties.put("value.serializer", StringSerializer.class);

            KafkaSender<Integer, String> kafkaSender = KafkaSender.create(SenderOptions.create(properties));

            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("airPlaneTopic", "from standalone reactive java");

            //SenderRecord<String, String, String> senderRecord = SenderRecord.create(producerRecord, "1");
            //kafkaSender.<String>send();
            //kafkaSender.send(senderRecord);

            String topic = "airPlaneTopic";
            int count = 1;
            CountDownLatch latch = null;

            kafkaSender.<Integer>send(Flux.range(1, count)
                            .map(i -> SenderRecord.create(new ProducerRecord<>(topic, i, "Message_" + i), i)))
                    .doOnError(e -> System.out.println("Send failed"))
                    .subscribe(r -> {
                        RecordMetadata metadata = r.recordMetadata();
                        Instant timestamp = Instant.ofEpochMilli(metadata.timestamp());
                        System.out.printf("Message %d sent successfully, topic-partition=%s-%d offset=%d timestamp=%s\n",
                                r.correlationMetadata(),
                                metadata.topic(),
                                metadata.partition(),
                                metadata.offset(),
                                dateFormat.format(timestamp));
                        latch.countDown();
                    });

            latch.await(10, TimeUnit.SECONDS);
            kafkaSender.close();

            System.out.println("MyKafkaReactiveProducer::main()::DONE");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
