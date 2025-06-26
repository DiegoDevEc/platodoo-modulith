package com.playtodoo.modulith.licensing.validation;

import java.util.UUID;

public record PlanDto(
        UUID id,
        String type,
        Integer validMonths,
        String shortDescription,
        String longDescription,
        String status
) {}
