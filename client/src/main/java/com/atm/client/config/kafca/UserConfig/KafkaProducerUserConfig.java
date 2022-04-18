package com.atm.client.config.kafca.UserConfig;

import com.atm.client.dto.OperationDTO;
import com.atm.client.dto.UserDTO;
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
public class KafkaProducerUserConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    private String kafkaProducerId = "user.create.client.id";

    @Bean
    public Map<String, Object> producerConfigsUser() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerId);
        return props;
    }

    @Bean
    public ProducerFactory<Long, UserDTO> producerFactoryUser() {
        return new DefaultKafkaProducerFactory<>(producerConfigsUser());
    }

    @Bean
    public KafkaTemplate<Long, UserDTO> kafkaTemplateUser() {
        KafkaTemplate<Long, UserDTO> template = new KafkaTemplate<>(producerFactoryUser());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }
}
