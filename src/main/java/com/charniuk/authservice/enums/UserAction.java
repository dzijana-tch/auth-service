package com.charniuk.authservice.enums;

import lombok.Getter;

@Getter
public enum UserAction {

  CREATED("Создан"),
  UPDATED("Изменен"),
  DELETED("Удален");

  private final String description;

  UserAction(String description) {
    this.description = description;
  }
}