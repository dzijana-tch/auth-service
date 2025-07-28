package com.charniuk.authservice.repository;

import com.charniuk.authservice.model.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
  boolean existsByUsername(String username);
}
