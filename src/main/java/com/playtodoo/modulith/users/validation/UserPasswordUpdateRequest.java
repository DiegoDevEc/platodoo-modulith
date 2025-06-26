package com.playtodoo.modulith.users.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserPasswordUpdateRequest(
        @NotNull UUID userId,
        @NotBlank String newPassword,
        @NotBlank String oldPassword) {
}
