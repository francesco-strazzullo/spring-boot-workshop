package it.flowing.workshop.repository;

import com.github.javafaker.Faker;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import it.flowing.workshop.model.User;
import it.flowing.workshop.model.UserId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MemoryUserRepository implements UserRepository {
    private List<User> dummyData;

    private static List<User> createDummyData() {
        Faker faker = new Faker();
        int maxUsers = faker.number().numberBetween(1, 50);
        return IntStream.range(0, maxUsers)
                .mapToObj(
                        (i) -> new User(UserId.create(UUID.randomUUID().toString()), faker.name().fullName()))
                .collect(Collectors.toList());
    }

    public MemoryUserRepository() {
        dummyData = createDummyData();
    }

    @Override
    public List<User> list() {

        return ImmutableList.copyOf(dummyData);
    }

    @Override
    public Optional<User> get(UserId id) {
        return dummyData.stream().filter(user -> user.id.equals(id)).findFirst();
    }

    @Override
    public User insert(User toInsert) {
        Preconditions.checkArgument(toInsert.id == null, "Invalid User");
        User toReturn = toInsert.withId(UserId.create(UUID.randomUUID().toString()));
        dummyData.add(toReturn);
        return toReturn;
    }

    @Override
    public User update(User toUpdate) {
        Preconditions.checkArgument(toUpdate.id != null, "Invalid User");
        dummyData =
                dummyData.stream()
                        .map(
                                user -> {
                                    if (user.id.equals(toUpdate.id)) {
                                        return toUpdate;
                                    }

                                    return user;
                                })
                        .collect(Collectors.toList());
        return toUpdate;
    }

    @Override
    public void delete(UserId id) {
        Preconditions.checkNotNull(id, "Invalid Id");
        dummyData = dummyData.stream().filter(user -> !user.id.equals(id)).collect(Collectors.toList());
    }
}
