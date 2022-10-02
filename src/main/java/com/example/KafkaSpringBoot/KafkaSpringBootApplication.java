package com.example.KafkaSpringBoot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringBootApplication.class, args);
	}

	/**
	 * Sends message to Kafka
	 */
	@Bean

	public CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate){
		try {
			System.out.printf("Inside KafkaSpringBootApplication:commandLineRunner()");
			return args -> {
				kafkaTemplate.send("airPlaneTopic", "Hello World");
			};
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
