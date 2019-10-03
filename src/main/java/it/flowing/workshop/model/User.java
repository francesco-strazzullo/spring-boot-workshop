package it.flowing.workshop.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor()
public class User {
    public final @NonNull String id;
    public final @NonNull String name;
}
