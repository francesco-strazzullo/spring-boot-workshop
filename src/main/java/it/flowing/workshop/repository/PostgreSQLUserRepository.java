package it.flowing.workshop.repository;


import it.flowing.workshop.model.User;
import it.flowing.workshop.model.UserId;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PostgreSQLUserRepository implements UserRepository {
    @Override
    public List<User> list() {
        return new ArrayList<>();
    }

    @Override
    public Optional<User> get(UserId id) {
        return Optional.empty();
    }

    @Override
    public User insert(User toInsert) {
        throw new NotImplementedException("Not Implemented");
    }

    @Override
    public User update(User toUpdate) {
        throw new NotImplementedException("Not Implemented");
    }

    @Override
    public void delete(UserId id) {
        throw new NotImplementedException("Not Implemented");
    }
}
