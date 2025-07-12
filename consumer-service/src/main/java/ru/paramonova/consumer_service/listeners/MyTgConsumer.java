package ru.paramonova.consumer_service.listeners;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyTgConsumer {
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "debezium_users_topic", groupId = "users_group_id")
    public void handleUsersChange(@Payload(required = false) String message) {
        log.info("EVENT IN TABLE USERS");
        processMessageData(message, "USER");
    }

    @KafkaListener(topics = "debezium_publications_topic", groupId = "publications_group_id")
    public void handlePublicationsChange(@Payload(required = false) String message) {
        log.info("EVENT IN TABLE PUBLICATIONS");
        processMessageData(message, "PUBLICATION");
    }

    private void processMessageData(String message, String table) {
        if (message == null || message.isEmpty()) {
            log.warn("Received empty message in {}", table);
            return;
        }
        try {
            JsonNode root = objectMapper.readTree(message);
            JsonNode payload = root.path("payload");
            String op = payload.path("op").asText();
            switch (op) {
                case "c":
                    log.info("NEW {} CREATED: {}", table, payload.path("after"));
                    break;
                case "u":
                    log.info("{} UPDATED. Before: {}, After: {}", table, payload.path("before"), payload.path("after"));
                    break;
                case "d":
                    log.info("{} DELETED: {}", table, payload.path("before"));
                    break;
                case "r":
                    log.info("INITIAL {} DATA: {}", table, payload.path("after"));
                    break;
                default:
                    log.warn("UNKNOWN OPERATION: {}", message);
            }
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
        }
    }
}
