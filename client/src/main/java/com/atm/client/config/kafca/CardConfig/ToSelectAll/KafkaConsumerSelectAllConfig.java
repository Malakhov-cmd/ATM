package com.atm.client.config.kafca.CardConfig.ToSelectAll;

import com.atm.client.dto.CardDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class KafkaConsumerSelectAllConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.kafka.consumer.group-id}")
    private String kafkaGroupId;

    @Bean
    public KafkaListenerContainerFactory<?> batchFactorySelectAll() {
        ConcurrentKafkaListenerContainerFactory<Long, Set<CardDTO>> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactorySelectAll());
        factory.setBatchListener(true);

        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleFactorySelectAll() {
        ConcurrentKafkaListenerContainerFactory<Long, Set<CardDTO>> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactorySelectAll());
        factory.setBatchListener(false);

        return factory;
    }

    @Bean
    public ConsumerFactory<Long, Set<CardDTO>> consumerFactorySelectAll() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigsSelectAll());
    }

    @Bean
    public Map<String, Object> consumerConfigsSelectAll() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        return props;
    }
}
