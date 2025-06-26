package com.playtodoo.modulith.licensing.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateLicenseDto(
        @NotNull UUID sportComplexId,
        @NotBlank String planType,
        @NotNull LocalDateTime expirationDate,
        @NotBlank String status
) {}
