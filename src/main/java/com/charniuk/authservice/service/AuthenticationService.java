package com.charniuk.authservice.service;

import com.charniuk.authservice.dto.request.SignInByEmailRequest;
import com.charniuk.authservice.dto.request.SignInByUsernameRequest;
import com.charniuk.authservice.dto.request.SignUpRequest;
import com.charniuk.authservice.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {

  /**
   * Регистрация пользователя
   *
   * @param request данные пользователя
   * @return токен
   */
  JwtAuthenticationResponse signUp(SignUpRequest request);

  /**
   * Аутентификация пользователя по email
   *
   * @param request данные пользователя
   * @return токен
   */
  JwtAuthenticationResponse signInByEmail(SignInByEmailRequest request);

  /**
   * Аутентификация пользователя по username
   *
   * @param request данные пользователя
   * @return токен
   */
  JwtAuthenticationResponse signInByUsername(SignInByUsernameRequest request);
}
