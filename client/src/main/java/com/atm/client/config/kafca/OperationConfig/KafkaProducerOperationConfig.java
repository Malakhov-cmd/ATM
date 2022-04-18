package com.atm.client.config.kafca.OperationConfig;

import com.atm.client.dto.CardDTO;
import com.atm.client.dto.OperationDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerOperationConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    private String kafkaProducerId = "operation.create.client.id";

    @Bean
    public Map<String, Object> producerConfigsOperation() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerId);
        return props;
    }

    @Bean
    public ProducerFactory<Long, OperationDTO> producerFactoryOperation() {
        return new DefaultKafkaProducerFactory<>(producerConfigsOperation());
    }

    @Bean
    public KafkaTemplate<Long, OperationDTO> kafkaTemplateOperation() {
        KafkaTemplate<Long, OperationDTO> template = new KafkaTemplate<>(producerFactoryOperation());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }
}
