package com.playtodoo.modulith.licensing.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePlanDto(
        @NotBlank String type,
        @NotNull Integer validMonths,
        String shortDescription,
        String longDescription,
        @NotBlank String status
) {}
