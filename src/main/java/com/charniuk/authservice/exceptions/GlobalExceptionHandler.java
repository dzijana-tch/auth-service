package com.charniuk.authservice.exceptions;

import com.charniuk.authservice.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
