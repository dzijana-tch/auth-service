package com.charniuk.authservice.dto.response;

import com.charniuk.authservice.enums.Role;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UserResponse(
    UUID userId,
    String email,
    String username,
    String firstName,
    String lastName,
    Role role
) {

}