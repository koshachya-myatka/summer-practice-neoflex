package ru.paramonova.producerservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.paramonova.producerservice.models.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
