package com.search.truyen.controller;

import com.search.truyen.dtos.AuthResponse;
import com.search.truyen.dtos.LoginRequest;
import com.search.truyen.dtos.userDTO;
import com.search.truyen.model.entities.User;
import com.search.truyen.security.JwtTokenProvider;
import com.search.truyen.service.UserService;
import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/name/{username}")
    public ResponseEntity<List<User>> getUserByName(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUserByName(username));
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
    public ResponseEntity<AuthResponse> userLogin(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String token = jwtTokenProvider.generateToken(loginRequest.getUsername());

        return ResponseEntity.ok(new AuthResponse(token, loginRequest.getUsername()));
    }

}
