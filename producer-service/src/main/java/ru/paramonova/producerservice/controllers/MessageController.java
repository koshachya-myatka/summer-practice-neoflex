package ru.paramonova.producerservice.controllers;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.paramonova.producerservice.publishers.MessageProducer;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageProducer messageProducer;

    @PostMapping("/message")
    public ResponseEntity<Void> sendMessage(@PathVariable String message) {
        if (messageProducer.sendMessage(message)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
