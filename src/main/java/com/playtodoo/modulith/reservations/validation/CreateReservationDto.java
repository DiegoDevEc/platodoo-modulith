package com.playtodoo.modulith.reservations.validation;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateReservationDto(
        @NotNull UUID courtId,
        @NotNull UUID userId,
        @NotNull LocalDateTime startTime,
        @NotNull LocalDateTime endTime,
        String status
) {}
