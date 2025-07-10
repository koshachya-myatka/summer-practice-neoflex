package ru.paramonova.producerservice.services;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.paramonova.producerservice.dto.PublicationDto;
import ru.paramonova.producerservice.dto.PublicationWithoutIdDto;
import ru.paramonova.producerservice.dto.UserDto;
import ru.paramonova.producerservice.dto.UserWithoutIdDto;
import ru.paramonova.producerservice.models.Publication;
import ru.paramonova.producerservice.models.User;
import ru.paramonova.producerservice.repositories.PublicationRepository;
import ru.paramonova.producerservice.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyTgService {
    private final UserRepository userRepository;
    private final PublicationRepository publicationRepository;
    private final ObjectMapper objectMapper;

    // ПОЛЬЗОВАТЕЛИ
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByNickname(String nickname) {
        return userRepository.findUserByNickname(nickname);
    }

    public User createUser(UserWithoutIdDto userDto) {
        User user = objectMapper.convertValue(userDto, User.class);
        return userRepository.save(user);
    }

    @SneakyThrows
    public User updateUser(User user, UserDto userDto) {
        objectMapper.updateValue(user, userDto);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // ПУБЛИКАЦИИ
    public List<Publication> findAllPublications() {
        return publicationRepository.findAll();
    }

    public Optional<Publication> findPublicationById(Long id) {
        return publicationRepository.findById(id);
    }

    public Publication createPublication(PublicationWithoutIdDto publicationDto) {
        Publication publication = objectMapper.convertValue(publicationDto, Publication.class);
        return publicationRepository.save(publication);
    }

    @SneakyThrows
    public Publication updatePublication(Publication publication, PublicationDto publicationDto) {
        objectMapper.updateValue(publication, publicationDto);
        return publicationRepository.save(publication);
    }

    public void deletePublication(Long id) {
        publicationRepository.deleteById(id);
    }
}
