package com.playtodoo.modulith.users.validation;

public record UserDto(
         String username,
         String email,
         String phone,
         String firstName,
         String lastName) {
}
