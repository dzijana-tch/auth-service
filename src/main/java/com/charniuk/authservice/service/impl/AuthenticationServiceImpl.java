package com.charniuk.authservice.service.impl;

import com.charniuk.authservice.dto.request.SignInByEmailRequest;
import com.charniuk.authservice.dto.request.SignInByUsernameRequest;
import com.charniuk.authservice.dto.request.SignUpRequest;
import com.charniuk.authservice.dto.response.JwtAuthenticationResponse;
import com.charniuk.authservice.enums.Role;
import com.charniuk.authservice.model.User;
import com.charniuk.authservice.security.JwtService;
import com.charniuk.authservice.service.AuthenticationService;
import com.charniuk.authservice.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public JwtAuthenticationResponse signUp(SignUpRequest request) {

    log.info("Регистрация нового пользователя");

    User user = User.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .email(request.getEmail())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .role(Role.ROLE_USER)
        .build();

    userService.create(user);

    var jwt = jwtService.generateToken(user);
    return new JwtAuthenticationResponse(jwt);
  }

  @Override
  @Transactional
  public JwtAuthenticationResponse signInByEmail(SignInByEmailRequest request) {

    log.info("Авторизация по email {}", request.getEmail());

    var user = userService
        .getByEmail(request.getEmail());

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        user.getUsername(),
        request.getPassword()
    ));

    var jwt = jwtService.generateToken(user);
    return new JwtAuthenticationResponse(jwt);
  }

  @Override
  @Transactional
  public JwtAuthenticationResponse signInByUsername(SignInByUsernameRequest request) {

    log.info("Авторизация по username {}", request.getUsername());

    var user = userService
        .getByUsername(request.getUsername());

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
    ));

    var jwt = jwtService.generateToken(user);
    return new JwtAuthenticationResponse(jwt);
  }

  @Override
  public boolean isCurrentUser(UUID userId) {

    UUID currentUserId = currentUserId();
    return userId.equals(currentUserId);
  }

  @Override
  public UUID currentUserId() {

    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return currentUser.getUserId();
  }
}