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
    private final MyTgService myTgService;

    // ПОЛЬЗОВАТЕЛИ
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(myTgService.findAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> userOptional = myTgService.findUserById(id);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserWithoutIdDto userDto) {
        if (myTgService.findUserByNickname(userDto.getNickname()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(myTgService.createUser(userDto));
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto) {
        Optional<User> userOptional = myTgService.findUserById(userDto.getId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (myTgService.findUserByNickname(userDto.getNickname()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        User updatedUser;
        try {
            updatedUser = myTgService.updateUser(userOptional.get(), userDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = myTgService.findUserById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        myTgService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ПУБЛИКАЦИИ
    @GetMapping("/publications")
    public ResponseEntity<List<Publication>> getAllPublications() {
        return ResponseEntity.ok(myTgService.findAllPublications());
    }

    @GetMapping("/publications/{id}")
    public ResponseEntity<Publication> getPublication(@PathVariable Long id) {
        Optional<Publication> publicationOptional = myTgService.findPublicationById(id);
        return publicationOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/publications")
    public ResponseEntity<Publication> createPublication(@RequestBody PublicationWithoutIdDto publicationDto) {
        if (myTgService.findUserById(publicationDto.getAuthorId()).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(myTgService.createPublication(publicationDto));
    }

    @PutMapping("/publications")
    public ResponseEntity<Publication> updatePublication(@RequestBody PublicationDto publicationDto) {
        Optional<Publication> publicationOptional = myTgService.findPublicationById(publicationDto.getId());
        if (publicationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Publication updatedPublication;
        try {
            updatedPublication = myTgService.updatePublication(publicationOptional.get(), publicationDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(updatedPublication);
    }

    @DeleteMapping("/publications/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        Optional<Publication> publicationOptional = myTgService.findPublicationById(id);
        if (publicationOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        myTgService.deletePublication(id);
        return ResponseEntity.noContent().build();
    }
}
