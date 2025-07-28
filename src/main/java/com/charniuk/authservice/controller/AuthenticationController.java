package com.charniuk.authservice.controller;

import com.charniuk.authservice.dto.request.SignInByEmailRequest;
import com.charniuk.authservice.dto.request.SignInByUsernameRequest;
import com.charniuk.authservice.dto.request.SignUpRequest;
import com.charniuk.authservice.dto.response.ErrorResponse;
import com.charniuk.authservice.dto.response.JwtAuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Аутентификация")
public interface AuthenticationController {

  /**
   * Регистрация пользователя
   *
   * @param request данные пользователя
   * @return токен
   */
  @Operation(summary = "Регистрация пользователя")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = JwtAuthenticationResponse.class))),
          @ApiResponse(
              responseCode = "409",
              description = "Conflict",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)))
      }
  )
  @PostMapping
  ResponseEntity<JwtAuthenticationResponse> signUp(SignUpRequest request);

  /**
   * Авторизация пользователя по email
   *
   * @param request данные пользователя
   * @return токен
   */
  @Operation(summary = "Авторизация пользователя по email")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = JwtAuthenticationResponse.class))),
          @ApiResponse(
              responseCode = "401",
              description = "Unauthorized",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)))
      }
  )
  @PostMapping
  ResponseEntity<JwtAuthenticationResponse> signInByEmail(SignInByEmailRequest request);

  /**
   * Авторизация пользователя по username
   *
   * @param request данные пользователя
   * @return токен
   */
  @Operation(summary = "Авторизация пользователя по username")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = JwtAuthenticationResponse.class))),
          @ApiResponse(
              responseCode = "401",
              description = "Unauthorized",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)))
      }
  )
  @PostMapping
  ResponseEntity<JwtAuthenticationResponse> signInByUsername(SignInByUsernameRequest request);
}