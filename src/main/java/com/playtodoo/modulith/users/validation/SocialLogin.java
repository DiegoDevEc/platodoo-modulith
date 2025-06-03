package com.playtodoo.modulith.users.validation;

public record SocialLogin(String token, String provider, String platform, String firstName, String lastName, String email, String avatar) {
}
