package com.playtodoo.modulith.customers.validation;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateCustomerDto(
        UUID id,
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        @NotBlank(message = "El email es obligatorio")
        String email) { }
