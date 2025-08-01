package com.charniuk.authservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию по email")
public class SignInByEmailRequest {

  @Schema(description = "Адрес электронной почты", example = "ivanov@mail.ru")
  @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
  @NotBlank(message = "Адрес электронной почты не может быть пустым")
  @Email(message = "Email адрес должен быть в формате user@example.com")
  private String email;

  @Schema(description = "Пароль", example = "my1secret!password")
  @Size(min = 8, max = 100, message = "Длина пароля должна быть от 8 до 100 символов")
  @NotBlank(message = "Пароль не может быть пустым")
  private String password;
}