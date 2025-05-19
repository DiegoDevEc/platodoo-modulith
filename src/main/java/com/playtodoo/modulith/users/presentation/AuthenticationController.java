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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
