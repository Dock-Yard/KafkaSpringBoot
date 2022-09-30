package com.standalone.java.reactive;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MyKafkaReactiveProducerFromDocs {

    public static void main(String[] args){
        try {
            System.out.println("Inside MyKafkaReactiveProducerFromDocs::main()::START::thread id=" + Thread.currentThread().getId() + ", thread name=" + Thread.currentThread().getName());

            Map<String, Object> producerProps = new HashMap<>();
            producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
            producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

            SenderOptions<Integer, String> senderOptions = SenderOptions.<Integer, String>create(producerProps).maxInFlight(1024);

            KafkaSender<Integer, String> sender = KafkaSender.create(senderOptions);

            Flux<SenderRecord<Integer, String, Integer>> outboundFlux =
                    Flux.range(1, 1000)
                            .map(i -> SenderRecord.create("airPlaneTopic", null, null, i, "From sagnik::Message_" + i, i));

            Flux<SenderResult<Integer>> senderResultFlux = (Flux<SenderResult<Integer>>)sender.send(outboundFlux);

            CompletableFuture<String> completableFuture = new CompletableFuture<>();

            senderResultFlux/*.doOnError(e-> e.printStackTrace())
                .doOnNext(r -> System.out.println("Inside doOnNext()::" + "Message #" + r.correlationMetadata() + ", send response=" + r.recordMetadata() + ", thread id=" + Thread.currentThread().getId() + ", thread id=" + Thread.currentThread().getId() + ", thread name=" + Thread.currentThread().getName()))*/
                .subscribe(new Subscriber<SenderResult<Integer>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("Inside senderResultFlux::onSubscribe()::thread id=" + Thread.currentThread().getId() + ", thread id=" + Thread.currentThread().getId() + ", thread name=" + Thread.currentThread().getName());
                        s.request(100);
                    }

                    @Override
                    public void onNext(SenderResult<Integer> integerSenderResult) {
                        System.out.println("Inside senderResultFlux::onNext()::thread id=" + Thread.currentThread().getId() + ", thread id=" + Thread.currentThread().getId() + ", thread name=" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("Inside senderResultFlux::onError()::thread id=" + Thread.currentThread().getId() + ", thread id=" + Thread.currentThread().getId() + ", thread name=" + Thread.currentThread().getName());
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Inside senderResultFlux::onComplete()::thread id=" + Thread.currentThread().getId() + ", thread id=" + Thread.currentThread().getId() + ", thread name=" + Thread.currentThread().getName());
                        completableFuture.complete("completed jab");
                    }
                });

            System.out.println("Inside MyKafkaReactiveProducerFromDocs::main()::END::thread id=" + Thread.currentThread().getId() + ", thread id=" + Thread.currentThread().getId() + ", thread name=" + Thread.currentThread().getName());

            System.out.println("completableFuture return=" + completableFuture.get());
            //Thread.sleep(5000);//if sender is closed before its completed, sending stops with error
            sender.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
