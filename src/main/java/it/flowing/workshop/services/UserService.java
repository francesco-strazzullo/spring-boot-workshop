package it.flowing.workshop.services;

import it.flowing.workshop.exceptions.MalformedUUIDException;
import it.flowing.workshop.exceptions.NotFoundException;
import it.flowing.workshop.model.User;
import it.flowing.workshop.model.UserId;
import it.flowing.workshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserId createUserId(String id) {
        try {
            return UserId.create(id);
        } catch (IllegalArgumentException e) {
            throw new MalformedUUIDException(e);
        }
    }

    public List<User> list() {
        return userRepository.list();
    }

    private boolean exist(UserId id) {
        return userRepository
                .get(id)
                .isPresent();
    }

    public User get(String id) {
        UserId userId = createUserId(id);
        return userRepository
                .get(userId)
                .orElseThrow(() -> {
                    throw new NotFoundException(userId.toString());
                });
    }

    public User insert(User toInsert) {
        return userRepository.insert(toInsert);
    }

    public User update(String id, User toUpdate) {
        UserId userId = createUserId(id);
        if (!exist(userId)) {
            throw new NotFoundException(userId.toString());
        }
        return userRepository.update(toUpdate.withId(userId));
    }

    public void delete(String id) {
        UserId userId = createUserId(id);
        if (!exist(userId)) {
            throw new NotFoundException(userId.toString());
        }
        userRepository.delete(userId);
    }
}
