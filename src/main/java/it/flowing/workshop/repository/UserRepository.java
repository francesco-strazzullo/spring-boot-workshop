package it.flowing.workshop.repository;

import com.github.javafaker.Faker;
import com.google.common.collect.ImmutableList;
import it.flowing.workshop.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class UserRepository {
    private final List<User> dummyData;

    private static List<User> createDummyData(){
        Faker faker = new Faker();
        int maxUsers = faker.number().numberBetween(1, 50);
        return IntStream
                .range(0, maxUsers)
                .mapToObj((i) -> new User(
                        UUID.randomUUID().toString(),
                        faker.name().fullName()
                ))
                .collect(Collectors.toList());
    }

    public UserRepository() {
        dummyData = createDummyData();
    }

    public List<User> list() {
        return ImmutableList.copyOf(dummyData);
    }
}
