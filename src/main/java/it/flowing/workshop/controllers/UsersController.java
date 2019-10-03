package it.flowing.workshop.controllers;

import it.flowing.workshop.model.User;
import it.flowing.workshop.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static it.flowing.workshop.ApiVersion.V1;

@RestController
@RequestMapping(value = "/" + V1 + "/users")
public class UsersController {

    private UserRepository userRepository = new UserRepository();

    @GetMapping
    public List<User> list() {
        return userRepository.list();
    }
}
