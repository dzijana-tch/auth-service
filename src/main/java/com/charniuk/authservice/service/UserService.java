package com.charniuk.authservice.service;

import com.charniuk.authservice.model.User;
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

}
