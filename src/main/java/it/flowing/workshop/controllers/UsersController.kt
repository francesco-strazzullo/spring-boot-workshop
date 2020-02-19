package it.flowing.workshop.controllers

import it.flowing.workshop.ApiVersion
import it.flowing.workshop.model.User
import it.flowing.workshop.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/" + ApiVersion.V1 + "/users"])
class UsersController @Autowired constructor(private val userService: UserService) {
    @GetMapping
    @SkipAuthentication
    fun list(): List<User> {
        return userService.list()
    }

    @GetMapping("/{id}")
    @SkipAuthentication
    operator fun get(@PathVariable("id") id: String): User {
        return userService[id]
    }

    @PostMapping
    fun create(@RequestBody toInsert: User): User {
        return userService.insert(toInsert)
    }

    @PutMapping("/{id}")
    fun update(@RequestBody toUpdate: User, @PathVariable("id") id: String): User {
        return userService.update(id, toUpdate)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) {
        userService.delete(id)
    }

}