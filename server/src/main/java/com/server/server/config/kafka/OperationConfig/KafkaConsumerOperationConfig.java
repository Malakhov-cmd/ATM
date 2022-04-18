package com.server.server.config.kafka.OperationConfig;

import com.server.server.dto.OperationDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerOperationConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.kafka.consumer.group-id}")
    private String kafkaGroupId;

    private StringJsonMessageConverter messageConverter;

    public KafkaConsumerOperationConfig(StringJsonMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @Bean
    public KafkaListenerContainerFactory<?> batchFactoryOperation() {
        ConcurrentKafkaListenerContainerFactory<Long, OperationDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryOperation());
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(messageConverter));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleFactoryOperation() {
        ConcurrentKafkaListenerContainerFactory<Long, OperationDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryOperation());
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    @Bean
    public ConsumerFactory<Long, OperationDTO> consumerFactoryOperation() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigsOperation());
    }

    @Bean
    public Map<String, Object> consumerConfigsOperation() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        return props;
    }
}
