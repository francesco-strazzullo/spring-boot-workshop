package it.flowing.workshop.repository;

import it.flowing.workshop.model.User;
import it.flowing.workshop.model.UserId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class JPAUserRepository implements UserRepository {
    private UserEntityRepository userEntityRepository;

    private static Function<UserEntity, User> userEntityToUser = userEntity -> new User(
            UserId.create(userEntity.id.toString()),
            userEntity.name
    );

    @Autowired
    public JPAUserRepository(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public List<User> list() {

        return StreamSupport
                .stream(
                        userEntityRepository.findAll().spliterator(),
                        false
                ).map(userEntityToUser)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> get(UserId id) {
        Optional<UserEntity> maybeEntity = userEntityRepository.findById(UUID.fromString(id.toString()));
        if (maybeEntity.isPresent()) {
            User user = userEntityToUser.apply(maybeEntity.get());
            return Optional.of(user);
        }

        return Optional.empty();
    }

    @Override
    public User insert(User toInsert) {
        UserEntity savedUser = userEntityRepository.save(UserEntity.withName(toInsert.name));
        return userEntityToUser.apply(savedUser);
    }

    @Override
    public User update(User toUpdate) {
        UserEntity savedUser = userEntityRepository.save(UserEntity.fromUser(toUpdate));
        return userEntityToUser.apply(savedUser);
    }

    @Override
    public void delete(UserId id) {
        userEntityRepository.deleteById(UUID.fromString(id.toString()));
    }
}
