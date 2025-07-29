package com.charniuk.authservice.service.impl;

import static com.charniuk.authservice.enums.Role.ROLE_ADMIN;
import static com.charniuk.authservice.enums.UserAction.*;

import com.charniuk.authservice.UserMapper;
import com.charniuk.authservice.dto.request.UserUpdateRequest;
import com.charniuk.authservice.dto.response.UserResponse;
import com.charniuk.authservice.exceptions.UserAlreadyExistsException;
import com.charniuk.authservice.exceptions.UserNotFoundException;
import com.charniuk.authservice.model.User;
import com.charniuk.authservice.repository.UserRepository;
import com.charniuk.authservice.service.NotificationService;
import com.charniuk.authservice.service.UserService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final NotificationService notificationService;

  @Override
  public UserDetailsService userDetailsService() {
    return this::getByUsername;
  }

  @Override
  @Transactional
  public User getByUsername(String username) {
    log.info("Получение пользователя по username {}", username);
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(
            "Пользователь с username " + username + " не найден"));
  }

  @Override
  @Transactional
  public User getByEmail(String email) {
    log.info("Получение пользователя по email {}", email);
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(
            "Пользователь с email " + email + " не найден"));
  }

  @Override
  @Transactional
  public void create(User user) {
    log.info("Создание пользователя. Проверка на существование пользователя с username {}",
        user.getUsername());
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new UserAlreadyExistsException(
          "Пользователь с никнеймом " + user.getUsername() + " уже существует");
    }
    log.info("Проверка на существование пользователя с email {}",
        user.getEmail());
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new UserAlreadyExistsException(
          "Пользователь с email " + user.getEmail() + " уже существует");
    }
    userRepository.save(user);
    notificationService.notifyAdmins(CREATED, user, getAllAdminEmails());
  }

  @Override
  @Transactional(readOnly = true)
  public UserResponse getDtoByUserId(UUID userId) {
    log.info("Получение пользователя по userId {}", userId);
    User user = getByUserId(userId);
    return userMapper.toResponse(user);
  }

  private User getByUserId(UUID userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + userId + " не найден"));
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserResponse> getAllUserDto() {
    log.info("Получение всех пользователей");
    List<User> users = userRepository.findAll();
    return userMapper.toResponse(users);
  }

  @Override
  @Transactional
  public UserResponse update(UUID userId, UserUpdateRequest userUpdateRequest) {
    User user = getByUserId(userId);
    userMapper.updateUserFromRequest(user, userUpdateRequest);
    log.info("Обновление информации о пользователе {}", user.getUserId());
    userRepository.save(user);
    notificationService.notifyAdmins(UPDATED, user, getAllAdminEmails());
    return userMapper.toResponse(user);
  }

  @Override
  @Transactional
  public void delete(UUID userId) {
    log.info("Удаление пользователя с userId {}", userId);
    User user = getByUserId(userId);
    userRepository.delete(user);
    notificationService.notifyAdmins(DELETED, user, getAllAdminEmails());
  }

  @Override
  @Transactional(readOnly = true)
  public List<String> getAllAdminEmails() {
    return userRepository.findAll()
        .stream()
        .filter(e -> e.getRole().equals(ROLE_ADMIN))
        .map(User::getEmail)
        .toList();
  }
}
