package ru.paramonova.producerservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {
    @Value("${kafka.message.topic}")
    private String messageTopic;

    @Bean
    public NewTopic createMessageTopic() {
        return TopicBuilder.name(messageTopic).partitions(4).build();
    }
}

