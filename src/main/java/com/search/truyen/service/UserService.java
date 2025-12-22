package com.search.truyen.service;

import com.search.truyen.dtos.LoginRequest;
import com.search.truyen.dtos.userDTO;
import com.search.truyen.model.entities.User;
import com.search.truyen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public userDTO createUser(userDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, userDTO.class);
    }

    public Optional<User> loginUser(LoginRequest loginRequest) {
        log.info("Logging in user: {}", loginRequest.getUsername());
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if (userOptional.isPresent()) {
            User userFromDB = userOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), userFromDB.getPassword())) {
                return userOptional;
            }
        }
        return Optional.empty();
    }

    public List<userDTO> getUserByName(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username).stream()
                .map(u -> modelMapper.map(u, userDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<userDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(u -> modelMapper.map(u, userDTO.class));
    }
}