package com.mongodb.starter.controllers;

import com.mongodb.starter.models.User;
import com.mongodb.starter.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("user")
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("user/{user_id}")
    public ResponseEntity<User> getUser(@PathVariable int user_id) {
        User user = userRepository.findOne(user_id);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(user);
    }

    @GetMapping("users/count")
    public Long getCount() {
        return userRepository.count();
    }

    @DeleteMapping("user/{user_id}")
    public Long deleteUser(@PathVariable int user_id) {
        return userRepository.delete(user_id);
    }

    @PutMapping("user")
    public User putUser(@RequestBody User user) {
        return userRepository.update(user);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
