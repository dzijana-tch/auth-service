package com.charniuk.authservice.controller.impl;

import com.charniuk.authservice.controller.UserController;
import com.charniuk.authservice.dto.request.UserUpdateRequest;
import com.charniuk.authservice.dto.response.UserResponse;
import com.charniuk.authservice.service.AuthenticationService;
import com.charniuk.authservice.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/user")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

  private final UserService userService;
  private final AuthenticationService authenticationService;

  @Override
  @GetMapping("/{user_id}")
  @PreAuthorize("hasRole('ADMIN') or @authenticationServiceImpl.isCurrentUser(#userId)")
  public ResponseEntity<UserResponse> get(
      @PathVariable("user_id") UUID userId) {

    return ResponseEntity.ok(
        userService.getDtoByUserId(userId));
  }

  @Override
  @GetMapping("/current")
  public ResponseEntity<UserResponse> get() {

    UUID userId = authenticationService.currentUserId();
    return ResponseEntity.ok(
        userService.getDtoByUserId(userId));
  }

  @Override
  @GetMapping()
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<UserResponse>> getAll() {

    return ResponseEntity.ok(
        userService.getAllUserDto());
  }

  @Override
  @PutMapping("/{user_id}")
  @PreAuthorize("hasRole('ADMIN') or @authenticationServiceImpl.isCurrentUser(#userId)")
  public ResponseEntity<UserResponse> update(
      @PathVariable("user_id") UUID userId,
      @RequestBody @Valid UserUpdateRequest userUpdateRequest) {

    return ResponseEntity.ok(
        userService.update(userId, userUpdateRequest));
  }

  @Override
  @DeleteMapping("/{user_id}")
  @PreAuthorize("hasRole('ADMIN') or @authenticationServiceImpl.isCurrentUser(#userId)")
  public ResponseEntity<Void> delete(
      @PathVariable("user_id") UUID userId) {

    userService.delete(userId);
    return ResponseEntity.ok().build();
  }
}
