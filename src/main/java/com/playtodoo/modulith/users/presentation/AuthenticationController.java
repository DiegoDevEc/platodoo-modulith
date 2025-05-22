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

import java.util.UUID;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService service;
    private final AuthenticateUserHandler authenticateUserHandler;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticateUserResponse> authenticate(@RequestBody AuthenticateUserRequest request) {
        return ResponseEntity.ok(authenticateUserHandler.authenticateUser(request));
    }

    @PostMapping("/sign-up")
    public UserDto createUser(@RequestBody @Valid CreateUserDTO dto) {
        return service.createUser(dto);
    }

    @PostMapping("/sign-in-with-token")
    public ResponseEntity<AuthenticateUserResponse> authenticateWithToken(@RequestBody AccessTokenRequest request) {
        return ResponseEntity.ok(authenticateUserHandler.authenticateFromAccessToken(request));
    }

    @GetMapping("/validate-existing-email")
    public ResponseEntity<Boolean> validateEmail(@RequestParam String email,
                                                 @RequestParam String platform,
                                                 @RequestParam(required = false) UUID userId) {
        boolean exists = service.existsEmail(email, userId, platform);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/validate-existing-username")
    public ResponseEntity<Boolean> validateUsername(@RequestParam String username,
                                                    @RequestParam String platform,
                                                    @RequestParam(required = false) UUID userId) {
        boolean exists = service.existsUsername(username, userId, platform);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/validate-existing-phone")
    public ResponseEntity<Boolean> validatePhone(@RequestParam String phone,
                                                 @RequestParam String platform,
                                                 @RequestParam(required = false) UUID userId) {
        boolean exists = service.existsPhone(phone, userId, platform);
        return ResponseEntity.ok(exists);
    }


}
