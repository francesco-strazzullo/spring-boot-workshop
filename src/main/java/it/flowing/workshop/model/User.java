package it.flowing.workshop.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor()
public class User {
    public final String id;
    public final @NonNull String name;

    public User withId(String id) {
        return new User(
                id,
                this.name
        );
    }

    public User withName(String name) {
        return new User(
                this.id,
                name
        );
    }
}
