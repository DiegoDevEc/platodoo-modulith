package com.playtodoo.modulith.reservations.validation;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationDto(
        UUID id,
        UUID courtId,
        UUID userId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String status,
        LocalDateTime createdAt
) {}
