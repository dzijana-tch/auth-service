package com.charniuk.authservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию по username")
public class SignInByUsernameRequest {

  @Schema(description = "Никнейм", example = "ivan_ivanov")
  @Size(min = 3, max = 50, message = "Никнейм должен содержать от 3 до 50 символов")
  @NotBlank(message = "Никнейм не может быть пустым")
  private String username;

  @Schema(description = "Пароль", example = "my1secret!password")
  @Size(min = 8, max = 100, message = "Длина пароля должна быть от 8 до 100 символов")
  @NotBlank(message = "Пароль не может быть пустым")
  private String password;
}