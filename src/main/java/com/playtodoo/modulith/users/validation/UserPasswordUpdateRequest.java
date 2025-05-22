package com.playtodoo.modulith.users.validation;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UserPasswordUpdateRequest(
        @NotBlank UUID userId,
        @NotBlank String newPassword,
        @NotBlank String oldPassword) {
}
