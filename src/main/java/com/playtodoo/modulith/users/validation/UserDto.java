package com.playtodoo.modulith.users.validation;

import java.util.Set;
import java.util.UUID;

public record UserDto(
         UUID id,
         String username,
         String email,
         String phone,
         String firstName,
         String lastName,
         String status,
         String imageUrl,
         String platform,
         Set<String> roles) {
}
