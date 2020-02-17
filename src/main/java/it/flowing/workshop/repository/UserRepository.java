package it.flowing.workshop.repository;

import it.flowing.workshop.model.User;
import it.flowing.workshop.model.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> list();

    Optional<User> get(UserId id);

    User insert(User toInsert);

    User update(User toUpdate);

    void delete(UserId id);
}
