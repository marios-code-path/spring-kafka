package com.example.kafkaproducer;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;


@SpringBootApplication
public class KafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}


	@Bean
	ApplicationRunner applicationRunner(KafkaTemplate<String, Customer> kafkaTemplate) {
		return args -> {

				while(true) {
					Thread.sleep(1000);
					kafkaTemplate.send("foobar", new Customer(System.currentTimeMillis(), "mario"));
				}
		};
	}
}

// Heart of the producer side == KafkaTemplate
// kafka autoconfig - is the topic of interest thats gets us the behaviour and function we want
// configuration of the template (en.g value serializers for your pojo's )
// multiple overloads for send() function (e.g. spec key type )



