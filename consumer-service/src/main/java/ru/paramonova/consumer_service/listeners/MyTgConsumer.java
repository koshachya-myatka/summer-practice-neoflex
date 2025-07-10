package ru.paramonova.consumer_service.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyTgConsumer {
    @KafkaListener(topics = "debezium_users_topic", groupId = "users_group_id")
    public void handleUsersChange(@Payload String message) {
        log.info("USERS CHANGE:\n{}", message);
    }

    @KafkaListener(topics = "debezium_publications_topic", groupId = "publications_group_id")
    public void handlePublicationsChange(@Payload String message) {
        log.info("PUBLICATIONS CHANGE:\n{}", message);
    }
}
