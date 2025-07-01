package ru.paramonova.consumer_service.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageConsumer {
    @KafkaListener(concurrency = "2", topics = "${kafka.message.topic}", groupId = "${kafka.message.group}")
    public void readMessage(@Payload String message, @Header(KafkaHeaders.RECEIVED_KEY) String key) {
        log.info("Message: {}, key: {}", message, key);
    }
}
