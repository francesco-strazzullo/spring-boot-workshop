package it.flowing.workshop.controllers;

import it.flowing.workshop.model.User;
import it.flowing.workshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static it.flowing.workshop.ApiVersion.V1;

@RestController
@RequestMapping(value = "/" + V1 + "/users")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @SkipAuthentication
    public List<User> list() {
        return userService.list();
    }

    @GetMapping("/{id}")
    @SkipAuthentication
    public User get(@PathVariable("id") String id) {
        return userService.get(id);
    }

    @PostMapping
    public User create(@RequestBody User toInsert) {
        return userService.insert(toInsert);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User toUpdate, @PathVariable("id") String id) {
        return userService.update(id, toUpdate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        userService.delete(id);
    }
}
