package ru.paramonova.producerservice.publishers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProducer {
    @Value("${kafka.message.topic}")
    private String topic;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public boolean sendMessage(String message) {
        try {
            String key = UUID.randomUUID().toString();
            kafkaTemplate.send(topic, key, message);
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }
}
