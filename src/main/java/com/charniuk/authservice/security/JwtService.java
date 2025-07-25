package com.charniuk.authservice.security;

import com.charniuk.authservice.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  /**
   * Извлечение username из токена
   *
   * @param token токен
   * @return username
   */
  String extractUsername(String token);

  /**
   * Генерация токена
   *
   * @param user данные пользователя
   * @return токен
   */
  String generateToken(User user);

  /**
   * Проверка токена на валидность
   *
   * @param token       токен
   * @param userDetails данные пользователя
   * @return true, если токен валиден
   */
  boolean isTokenValid(String token, UserDetails userDetails);
}
