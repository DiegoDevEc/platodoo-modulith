package com.playtodoo.modulith.users.presentation;

import com.playtodoo.modulith.users.application.SocialAuthService;
import com.playtodoo.modulith.users.application.authenticateUser.AuthenticateUserResponse;
import com.playtodoo.modulith.users.validation.SocialLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/social")
@RequiredArgsConstructor
public class SocialAuthController {

    private final SocialAuthService socialAuthService;

    @PostMapping("/social-login")
    public ResponseEntity<AuthenticateUserResponse> socialLogin(@RequestBody SocialLogin request) {
        return ResponseEntity.ok(socialAuthService.generateAuthentificationBySocial(request));
    }
}
