package com.search.truyen.repository;

import com.search.truyen.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameContainingIgnoreCase(String name);

    Optional<User> findByEmail(String email);

    Optional<User> findByRole(String role);

    Optional<User> findByUsernameAndEmail(String username, String email);

}
