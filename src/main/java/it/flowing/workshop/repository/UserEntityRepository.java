package it.flowing.workshop.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserEntityRepository extends CrudRepository<UserEntity, UUID> {
}
