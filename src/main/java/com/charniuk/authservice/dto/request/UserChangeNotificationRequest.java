package com.charniuk.authservice.dto.request;

import com.charniuk.authservice.enums.UserAction;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record UserChangeNotificationRequest(
    @NotNull
    UserAction action,
    @NotNull
    String username,
    @NotNull
    String email,
    @NotNull
    String password,
    @JsonProperty("admin_emails")
    @NotNull
    List<String> adminEmails
) {

}