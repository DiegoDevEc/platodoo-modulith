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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth/social")
@RequiredArgsConstructor
@Tag(name = "Autenticación Social", description = "Inicio de sesión mediante proveedores externos")
public class SocialAuthController {

    private final SocialAuthService socialAuthService;

    @PostMapping("/social-login")
    @Operation(summary = "Autenticación social", description = "Genera un token de autenticación a partir de datos de redes sociales")
    public ResponseEntity<AuthenticateUserResponse> socialLogin(@RequestBody SocialLogin request) {
        return ResponseEntity.ok(socialAuthService.generateAuthentificationBySocial(request));
    }
}
