package com.server.server.config.kafka.CardConfig.ToSelectAll;

import com.server.server.dto.CardDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class KafkaProducerToSelectAllConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    private String kafkaProducerId = "card.select.client.id";

    @Bean
    public Map<String, Object> producerConfigsToSelectAll() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerId);
        return props;
    }

    @Bean
    public ProducerFactory<Long, Set<CardDTO>> producerFactoryToSelectAll() {
        return new DefaultKafkaProducerFactory<>(producerConfigsToSelectAll());
    }

    @Bean
    public KafkaTemplate<Long, Set<CardDTO>> kafkaTemplateToSelectAll() {
        return new KafkaTemplate<>(producerFactoryToSelectAll());
    }
}
