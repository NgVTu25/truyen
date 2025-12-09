package com.search.truyen.controller;

import com.search.truyen.dtos.LoginRequest;
import com.search.truyen.dtos.userDTO;
import com.search.truyen.model.entities.User;
import com.search.truyen.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/name/{username}")
    public ResponseEntity<User> getUserByName(@PathVariable("username") String username) {
        return userService.getUserByName(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<User> userRegister(@RequestBody userDTO user) {
        User created = userService.createUser(user);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<Optional<User>> userLogin(@RequestBody LoginRequest loginRequest) {
        log.info("Username: {}", loginRequest.getUsername());
        log.info("Password: {}", loginRequest.getPassword());
        Optional<User> login = userService.loginUser(loginRequest);
        if (login.isPresent()) {
            return ResponseEntity.ok(login);
        }
        return ResponseEntity.notFound().build();
    }

}
