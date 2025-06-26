package com.playtodoo.modulith.licensing.validation;

import java.time.LocalDateTime;
import java.util.UUID;

public record LicenseDto(
        UUID id,
        UUID sportComplexId,
        String planType,
        LocalDateTime expirationDate,
        String status
) {}
