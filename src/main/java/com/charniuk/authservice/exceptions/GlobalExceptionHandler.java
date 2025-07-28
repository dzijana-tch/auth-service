package com.charniuk.authservice.exceptions;

import com.charniuk.authservice.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthentication(HttpServletRequest request) {

    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(ErrorResponse.builder()
            .uri(request.getRequestURI())
            .type("401 UNAUTHORIZED")
            .message("Неверный логин или пароль")
            .timestamp(Instant.now().getEpochSecond())
            .build());
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDenied(HttpServletRequest request) {

    return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(ErrorResponse.builder()
            .uri(request.getRequestURI())
            .type("403 FORBIDDEN")
            .message("Доступ запрещен")
            .timestamp(Instant.now().getEpochSecond())
            .build());
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex,
      HttpServletRequest request) {

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.builder()
            .uri(request.getRequestURI())
            .type("404 NOT FOUND")
            .message(ex.getMessage())
            .timestamp(Instant.now().getEpochSecond())
            .build());
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex,
      HttpServletRequest request) {

    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(ErrorResponse.builder()
            .uri(request.getRequestURI())
            .type("409 CONFLICT")
            .message(ex.getMessage())
            .timestamp(Instant.now().getEpochSecond())
            .build());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleRuntime(HttpServletRequest request) {

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ErrorResponse.builder()
            .uri(request.getRequestURI())
            .type("500 INTERNAL SERVER ERROR")
            .message("Внутренняя ошибка сервера")
            .timestamp(Instant.now().getEpochSecond())
            .build());
  }
}
