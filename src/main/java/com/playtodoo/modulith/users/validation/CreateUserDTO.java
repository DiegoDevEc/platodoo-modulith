package com.playtodoo.modulith.users.validation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateUserDTO(
        @NotBlank String username,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String status,
        @Email String email,
        @NotBlank String phone,
        @NotBlank String platform,
        @Size(min = 8, message = "Password must be at least 8 characters") String password,
        Set<String> roles
) {}
