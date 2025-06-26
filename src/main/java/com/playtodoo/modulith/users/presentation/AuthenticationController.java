package com.playtodoo.modulith.users.presentation;

import com.playtodoo.modulith.users.application.UserService;
import com.playtodoo.modulith.users.application.authenticateUser.AccessTokenRequest;
import com.playtodoo.modulith.users.application.authenticateUser.AuthenticateUserHandler;
import com.playtodoo.modulith.users.application.authenticateUser.AuthenticateUserRequest;
import com.playtodoo.modulith.users.application.authenticateUser.AuthenticateUserResponse;
import com.playtodoo.modulith.users.validation.CreateUserDTO;
import com.playtodoo.modulith.users.validation.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Operaciones de autenticación de usuarios")
public class AuthenticationController {

    private final UserService service;
    private final AuthenticateUserHandler authenticateUserHandler;

    @PostMapping("/sign-in")
    @Operation(summary = "Iniciar sesión", description = "Autentica a un usuario y devuelve el token correspondiente")
    public ResponseEntity<AuthenticateUserResponse> authenticate(@RequestBody AuthenticateUserRequest request) {
        return ResponseEntity.ok(authenticateUserHandler.authenticateUser(request));
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario en la plataforma")
    public UserDto createUser(@RequestBody @Valid CreateUserDTO dto) {
        return service.createUser(dto);
    }

    @PostMapping("/sign-in-with-token")
    @Operation(summary = "Iniciar sesión con token", description = "Autentica utilizando un token de acceso previamente obtenido")
    public ResponseEntity<AuthenticateUserResponse> authenticateWithToken(@RequestBody AccessTokenRequest request) {
        return ResponseEntity.ok(authenticateUserHandler.authenticateFromAccessToken(request));
    }

    @GetMapping("/validate-existing-email")
    @Operation(summary = "Validar correo", description = "Verifica si un correo electrónico ya existe")
    public ResponseEntity<Boolean> validateEmail(@RequestParam String email,
                                                 @RequestParam String platform,
                                                 @RequestParam(required = false) UUID userId) {
        boolean exists = service.existsEmail(email, userId, platform);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/validate-existing-username")
    @Operation(summary = "Validar usuario", description = "Verifica si un nombre de usuario ya existe")
    public ResponseEntity<Boolean> validateUsername(@RequestParam String username,
                                                    @RequestParam String platform,
                                                    @RequestParam(required = false) UUID userId) {
        boolean exists = service.existsUsername(username, userId, platform);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/validate-existing-phone")
    @Operation(summary = "Validar teléfono", description = "Verifica si un número telefónico ya existe")
    public ResponseEntity<Boolean> validatePhone(@RequestParam String phone,
                                                 @RequestParam String platform,
                                                 @RequestParam(required = false) UUID userId) {
        boolean exists = service.existsPhone(phone, userId, platform);
        return ResponseEntity.ok(exists);
    }


}
