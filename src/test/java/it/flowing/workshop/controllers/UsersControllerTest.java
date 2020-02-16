package it.flowing.workshop.controllers;

import com.google.common.collect.Lists;
import it.flowing.workshop.model.User;
import it.flowing.workshop.model.UserId;
import it.flowing.workshop.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsersControllerTest {

    private static UsersController usersController;
    private static UserService userService;

    @BeforeAll
    public static void init() {
        userService = Mockito.mock(UserService.class);
        usersController = new UsersController(userService);
    }

    @Test
    public void list() {
        List<User> EXPECTATION =
                Lists.newArrayList(
                        new User(UserId.create(UUID.randomUUID().toString()), "Primo"),
                        new User(UserId.create(UUID.randomUUID().toString()), "Secondo"));

        Mockito.when(userService.list()).thenReturn(EXPECTATION);

        assertEquals(usersController.list(), EXPECTATION);
    }
}
