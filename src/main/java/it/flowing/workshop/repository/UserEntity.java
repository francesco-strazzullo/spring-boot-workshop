package it.flowing.workshop.repository;

import it.flowing.workshop.model.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;
    public String name;

    public static UserEntity withName(String name) {
        UserEntity userEntity = new UserEntity();
        userEntity.name = name;
        return userEntity;
    }

    public static UserEntity fromUser(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.name = user.name;
        userEntity.id = UUID.fromString(user.id.toString());
        return userEntity;
    }
}
