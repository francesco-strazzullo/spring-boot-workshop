package it.flowing.workshop.controllers;

import it.flowing.workshop.model.User;
import it.flowing.workshop.model.UserId;
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
  public ResponseEntity<User> get(@PathVariable("id") String id) {
    UserId userId;
    try {
      userId = UserId.create(id);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }

    Optional<User> maybeUser = userRepository.get(userId);

    if (maybeUser.isPresent()) {
      return ResponseEntity.ok(maybeUser.get());
    }

    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public User create(@RequestBody User toInsert) {
    return userRepository.insert(toInsert);
  }

  @PutMapping("/{id}")
  public User update(@RequestBody User user, @PathVariable("id") String id) {
    return userRepository.update(user.withId(UserId.create(id)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable("id") String id) {
    userRepository.delete(UserId.create(id));
    return ResponseEntity.noContent().build();
  }
}
