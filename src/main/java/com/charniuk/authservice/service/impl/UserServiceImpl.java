package com.charniuk.authservice.service.impl;

import com.charniuk.authservice.model.User;
import com.charniuk.authservice.repository.UserRepository;
import com.charniuk.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserDetailsService userDetailsService() {
    return this::getByUsername;
  }

  @Override
  public User getByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
  }
}
