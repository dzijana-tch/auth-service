package com.charniuk.authservice.service;

import com.charniuk.authservice.dto.request.UserUpdateRequest;
import com.charniuk.authservice.dto.response.UserResponse;
import com.charniuk.authservice.model.User;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

  /**
   * Сервис для загрузки пользователя по username.
   * Используется Spring Security
   *
   * @return пользователь
   */
  UserDetailsService userDetailsService();

  /**
   * Получение пользователя по username
   *
   * @return пользователь
   */
  User getByUsername(String username);

  /**
   * Получение пользователя по email
   *
   * @return пользователь
   */
  User getByEmail(String email);

  /**
   * Создание пользователя
   */
  void create(User user);

  /**
   * Получение пользователя по айди
   *
   * @return dto пользователя
   */
  UserResponse getDtoByUserId(UUID userId);

  /**
   * Получение всех пользователей
   *
   * @return dto пользователей
   */
  List<UserResponse> getAllUserDto();

  /**
   * Обновление данных пользователя
   *
   * @return dto пользователя
   */
  UserResponse update(UUID userId, UserUpdateRequest userUpdateRequest);

  /**
   * Удаление пользователя
   */
  void delete(UUID userId);
}
