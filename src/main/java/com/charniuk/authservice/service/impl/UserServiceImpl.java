package com.charniuk.authservice.service.impl;

import com.charniuk.authservice.exceptions.UserAlreadyExistsException;
import com.charniuk.authservice.model.User;
import com.charniuk.authservice.repository.UserRepository;
import com.charniuk.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserDetailsService userDetailsService() {
    return this::getByUsername;
  }

  @Override
  @Transactional
  public User getByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(
            "Пользователь с username " + username + " не найден"));
  }

  @Override
  @Transactional
  public User getByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(
            "Пользователь с email " + email + " не найден"));
  }

  @Override
  @Transactional
  public void create(User user) {
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new UserAlreadyExistsException(
          "Пользователь с никнеймом " + user.getUsername() + " уже существует");
    }
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new UserAlreadyExistsException(
          "Пользователь с email " + user.getEmail() + " уже существует");
    }
    userRepository.save(user);
  }
}
