package com.charniuk.authservice.controller.impl;

import com.charniuk.authservice.controller.AuthenticationController;
import com.charniuk.authservice.dto.request.SignInByEmailRequest;
import com.charniuk.authservice.dto.request.SignInByUsernameRequest;
import com.charniuk.authservice.dto.request.SignUpRequest;
import com.charniuk.authservice.dto.response.JwtAuthenticationResponse;
import com.charniuk.authservice.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationControllerImpl implements AuthenticationController {

  private final AuthenticationService authenticationService;

  @Override
  @PostMapping("/sign-up")
  public ResponseEntity<JwtAuthenticationResponse> signUp(
      @RequestBody @Valid SignUpRequest request) {
    return ResponseEntity.ok(authenticationService.signUp(request));
  }

  @Override
  @PostMapping("/sign-in/email")
  public ResponseEntity<JwtAuthenticationResponse> signInByEmail(
      @RequestBody @Valid SignInByEmailRequest request) {
    return ResponseEntity.ok(authenticationService.signInByEmail(request));
  }

  @Override
  @PostMapping("/sign-in/username")
  public ResponseEntity<JwtAuthenticationResponse> signInByUsername(
      @RequestBody @Valid SignInByUsernameRequest request) {
    return ResponseEntity.ok(authenticationService.signInByUsername(request));
  }
}