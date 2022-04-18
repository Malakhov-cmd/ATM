package com.atm.client.config.kafca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;

@Configuration
public class ListenerContainerConfig {
    @Bean
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactoryUser() {
        return new ConcurrentKafkaListenerContainerFactory<>();
    }
}
