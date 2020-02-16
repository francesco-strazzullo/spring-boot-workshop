package it.flowing.workshop.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor()
public class User {

  public final UserId id;
  public final @NonNull String name;

  public User withId(UserId id) {
    return new User(id, this.name);
  }

  public User withName(String name) {
    return new User(this.id, name);
  }
}
