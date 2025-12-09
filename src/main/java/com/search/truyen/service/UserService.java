package com.search.truyen.service;

import com.search.truyen.dtos.LoginRequest;
import com.search.truyen.dtos.userDTO;
import com.search.truyen.enums.Userrole;
import com.search.truyen.model.entities.User;
import com.search.truyen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(userDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setRole(Userrole.valueOf(userDTO.getRole()));

        if (userDTO.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPasswordHash(encodedPassword);
        }
        return userRepository.save(user);
    }

    public Optional<User> loginUser(LoginRequest loginRequest) {
        log.info("Logging in user: {}", loginRequest);
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if (userOptional.isPresent()) {
            User userFromDB = userOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), userFromDB.getPassword())) {
                return userOptional;
            }
        }
        return Optional.empty();
    }

    public Optional<User> getUserByName(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
