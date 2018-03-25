package com.example.kafkaconsumer;

import com.example.kafkaproducer.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Collection;

@Slf4j
@SpringBootApplication
public class KafkaConsumerApplication {

    public KafkaConsumerApplication(KafkaProperties kafkaProperties) {
        this.myGroupId = kafkaProperties.getConsumer().getGroupId();
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class, args);
    }

    private final String myGroupId;

    @KafkaListener(topics = {"foobar"}/*, groupId = "${my.groupId:default}"*/)// exclusive group names (1 consumer may exist with that name )
    public void processMessage(Collection<Customer> customer) {
        customer.forEach(r -> {
            System.out.println("GROUPID " + myGroupId + ", record = " + r);
        });


    }

}
