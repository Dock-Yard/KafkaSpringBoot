package com.reactor.core;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReactorExamples {

    public static void main(String[] args) throws Exception{

        /*List<String> listOfString = List.of("sagnik", "kumar", "das", "hello");
        Flux<String> stringFlux = Flux.fromIterable(listOfString);
        stringFlux.subscribe(
                e -> System.out.println("element=" + e + ", thread id=" + Thread.currentThread().getId() + ", thread name=" + Thread.currentThread().getName()));*/


        //.delayElement(Duration.ofSeconds(3));
        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1))
                .delayUntil(ReactorExamples::makeItEven);

        longFlux.subscribe(
                e -> System.out.println("actual element=" + e + ", thread id=" + Thread.currentThread().getId() + ", thread name=" + Thread.currentThread().getName()));

        Thread.sleep(100000);

        System.out.println("End of main");

    }

    static Mono<Long> makeItEven(Long n){
        try {
            System.out.println("makeItEven()::START::n=" + n + ", thread id=" + Thread.currentThread().getId() + ", thread name=" + Thread.currentThread().getName());
            Thread.sleep(5000);
            System.out.println("makeItEven()::END::n=" + n + ", thread id=" + Thread.currentThread().getId() + ", thread name=" + Thread.currentThread().getName());
            return Mono.just(n * 2);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
