package com.playtodoo.modulith.sportcomplex.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateSportComplexDto(
        @NotBlank(message = "El nombre del complejo deportivo es obligatorio.")
        String name,

        @NotBlank(message = "La descripción del complejo deportivo es obligatoria.")
        String description,

        @NotNull(message = "La latitud del complejo deportivo es obligatoria.")
        Double latitude,

        @NotNull(message = "La longitud del complejo deportivo es obligatoria.")
        Double longitude,

        @NotNull(message = "El ID del propietario es obligatorio.")
        UUID ownerId,

        @NotNull(message = "El estado del complejo deportivo es obligatorio.")
        String status
) {}