package com.playtodoo.modulith.sportcomplex.validation;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SportComplexDto(UUID id,
                              String name,
                              String description,
                              Double latitude,
                              Double longitude,
                              String landingPageConfig,
                              UUID ownerId,
                              String status) { }
