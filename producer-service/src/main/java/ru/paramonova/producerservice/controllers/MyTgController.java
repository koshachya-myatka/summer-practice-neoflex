package ru.paramonova.producerservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.paramonova.producerservice.dto.PublicationDto;
import ru.paramonova.producerservice.dto.PublicationWithoutIdDto;
import ru.paramonova.producerservice.dto.UserDto;
import ru.paramonova.producerservice.dto.UserWithoutIdDto;
import ru.paramonova.producerservice.models.Publication;
import ru.paramonova.producerservice.models.User;
import ru.paramonova.producerservice.services.MyTgService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MyTgController {
    private final MyTgService service;

    // ПОЛЬЗОВАТЕЛИ
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(service.findAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> userOptional = service.findUserById(id);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserWithoutIdDto userDto) {
        if (service.findUserByNickname(userDto.getNickname()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.createUser(userDto));
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto) {
        Optional<User> userOptional = service.findUserById(userDto.getId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User updatedUser;
        try {
            updatedUser = service.updateUser(userOptional.get(), userDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = service.findUserById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ПУБЛИКАЦИИ
    @GetMapping("/publications")
    public ResponseEntity<List<Publication>> getAllPublications() {
        return ResponseEntity.ok(service.findAllPublications());
    }

    @GetMapping("/publications/{id}")
    public ResponseEntity<Publication> getPublication(@PathVariable Long id) {
        Optional<Publication> publicationOptional = service.findPublicationById(id);
        return publicationOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/publications")
    public ResponseEntity<Publication> createPublication(@RequestBody PublicationWithoutIdDto publicationDto) {
        if (service.findUserById(publicationDto.getAuthorId()).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.createPublication(publicationDto));
    }

    @PutMapping("/publications")
    public ResponseEntity<Publication> updatePublication(@RequestBody PublicationDto publicationDto) {
        Optional<Publication> publicationOptional = service.findPublicationById(publicationDto.getId());
        if (publicationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Publication updatedPublication;
        try {
            updatedPublication = service.updatePublication(publicationOptional.get(), publicationDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedPublication);
    }

    @DeleteMapping("/publications/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        Optional<Publication> publicationOptional = service.findPublicationById(id);
        if (publicationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deletePublication(id);
        return ResponseEntity.noContent().build();
    }
}
