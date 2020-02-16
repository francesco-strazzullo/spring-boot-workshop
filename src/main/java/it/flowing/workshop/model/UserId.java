package it.flowing.workshop.model;

import java.util.Objects;
import java.util.UUID;

public final class UserId {
  private final UUID value;

  private UserId(UUID value) {
    this.value = value;
  }

  public static UserId create(String value) {
    return new UserId(UUID.fromString(value));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserId userId = (UserId) o;
    return Objects.equals(value, userId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
