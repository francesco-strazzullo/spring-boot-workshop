package it.flowing.workshop.controllers;

import it.flowing.workshop.model.User;
import it.flowing.workshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static it.flowing.workshop.ApiVersion.V1;

@RestController
@RequestMapping(value = "/" + V1 + "/users")
public class UsersController {

  private final UserRepository userRepository;

  @Autowired
  public UsersController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping
  public List<User> list() {
    return userRepository.list();
  }

  @GetMapping("/{id}")
  public User get(@PathVariable("id") String id) {
    Optional<User> maybeUser = userRepository.get(id);
    return maybeUser.get();
  }

  @PostMapping
  public User create(@RequestBody User toInsert) {
    return userRepository.insert(toInsert);
  }

  @PutMapping("/{id}")
  public User update(@RequestBody User user, @PathVariable("id") String id) {
    return userRepository.update(user.withId(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable("id") String id) {
    userRepository.delete(id);
    return ResponseEntity.noContent().build();
  }
}
