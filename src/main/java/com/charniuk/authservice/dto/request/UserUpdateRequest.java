package com.charniuk.authservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на изменение данных пользователя")
public class UserUpdateRequest {

  @Schema(description = "Адрес электронной почты", example = "ivanov@mail.ru")
  @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
  @NotBlank(message = "Адрес электронной почты не может быть пустыми")
  @Email(message = "Email адрес должен быть в формате user@example.com")
  private String email;

  @Schema(description = "Пароль", example = "my1secret!password")
  @Size(min = 8, max = 100, message = "Длина пароля должна быть от 8 до 100 символов")
  @NotBlank(message = "Пароль не может быть пустым")
  private String password;

  @Schema(description = "Имя", example = "Иван")
  @Size(max = 50, message = "Имя должно содержать до 50 символов")
  private String firstName;

  @Schema(description = "Фамилия", example = "Иванов")
  @Size(max = 50, message = "Фамилия должна содержать до 50 символов")
  private String lastName;
}