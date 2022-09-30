package com.standalone.java.standard.gamora.QA;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.Future;

import static com.standalone.java.standard.gamora.Constants.*;

public class ImsQAProducer {

    public static void main(String[] args) {
        try{
            System.out.println("ImsQAProducer::main()::START");

            Properties properties = new Properties();

            properties.put("bootstrap.servers", QA_IMS_BOOTSTRAP_SERVERS);

            properties.put("key.serializer", StringSerializer.class);
            properties.put("value.serializer", StringSerializer.class);

            properties.put("enable.idempotence", "false");

            // for SSL Encryption
            properties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
            properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "/Users/s0d0bc6/Documents/KafkaConnect/SSL/QA/certs/certs.truststore");
            properties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "Walmart@12345");

            // for SSL Authentication
            properties.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "/Users/s0d0bc6/Documents/KafkaConnect/SSL/QA/certs/kafka.server.keystore.jks");
            properties.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "Walmart@12345");
            properties.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "Walmart@12345");



            KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                    QA_IMS_TOPIC_NAME, "Hello World from program");

            System.out.println("Attempting to send");

            Future<RecordMetadata> future = kafkaProducer.send(
                    producerRecord,
                    (metadata, exception) -> {
                        if(exception != null){
                            System.out.printf("ImsQAProducer::Inside callback::" + "some thing wrong");
                            exception.printStackTrace();
                        }
                        else{
                            System.out.println("ImsQAProducer::Inside callback::" + "Successfully transmitted");
                        }
                    });

            System.out.println("ImsQAProducer::main()::sent" + future.get());//future.get() is needed as else the sending thread also dies along with the main thread

            kafkaProducer.close();;

            System.out.println("ImsQAProducer::main()::DONE");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
