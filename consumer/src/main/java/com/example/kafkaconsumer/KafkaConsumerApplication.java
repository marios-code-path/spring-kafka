package com.example.kafkaconsumer;

import com.example.kafkaproducer.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.core.ConsumerFactory;

import java.util.Collection;

@Slf4j
@SpringBootApplication
public class KafkaConsumerApplication {


    public KafkaConsumerApplication(KafkaProperties kafkaProperties) {
        this.myGroupId = kafkaProperties.getConsumer().getGroupId();
    }

    /**
     * @Bean public KafkaListenerContainerFactory<?> batchFactory() {
     * ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
     * factory.setConsumerFactory(consumerFactory());
     * factory.setBatchListener(true); // <<<<<<<<<<<<<<<<<<<<<<<<< return factory; }
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class, args);
    }

    private final String myGroupId;

    @KafkaListener(topics = {"foobar"}, containerFactory = "batchingFactory")
    public void processMessage(Customer customer) {
        System.out.println("GROUPID " + myGroupId + ", record = " + customer);
    }

    //@KafkaListener(topics = {"foobar"}, containerFactory = "batchingFactory")// NOTE: exclusive group names (1 consumer may exist with that name )
    public void processMessage(Collection<Customer> customer) {
        customer.forEach(r -> {
            System.out.println("GROUPID " + myGroupId + ", record = " + r);
        });

    }

}