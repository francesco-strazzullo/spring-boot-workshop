package it.flowing.workshop.controllers;

import com.google.common.collect.Lists;
import it.flowing.workshop.model.User;
import it.flowing.workshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersControllerTest {

  private static UsersController usersController;
  private static UserRepository userRepository;

  @BeforeAll
  public static void init() {
    userRepository = Mockito.mock(UserRepository.class);
    usersController = new UsersController(userRepository);
  }

  @Test
  public void list() {
    List<User> EXPECTATION = Lists.newArrayList(
            new User("1","Primo"),
            new User("2","Secondo")
    );

    Mockito.when(userRepository.list()).thenReturn(EXPECTATION);

    assertEquals(usersController.list(), EXPECTATION);
  }
}