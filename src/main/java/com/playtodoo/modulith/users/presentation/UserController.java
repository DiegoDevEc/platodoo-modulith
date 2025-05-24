package com.playtodoo.modulith.users.presentation;

import com.playtodoo.modulith.common.PageResponse;
import com.playtodoo.modulith.sportcomplex.validation.SportComplexDto;
import com.playtodoo.modulith.users.application.UserService;
import com.playtodoo.modulith.users.application.authenticateUser.AuthenticateUserHandler;
import com.playtodoo.modulith.users.validation.CreateUserDTO;
import com.playtodoo.modulith.users.validation.UserDto;
import com.playtodoo.modulith.users.validation.UserPasswordUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageResponse<UserDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username") String sortField,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(service.findAll(page, size, sortField, direction));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public UserDto getUserById(@PathVariable UUID id) {
        return service.getUserById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/{userId}/roles")
    public UserDto assignRole(@PathVariable UUID userId, @RequestParam String role) {
        return service.assignRoleToUser(userId, role);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable UUID id, @RequestBody CreateUserDTO createUserDTO) {
        return service.updateUser(id, createUserDTO);
    }
    @PutMapping("/update-password")
    public Boolean updatePassword(@RequestBody UserPasswordUpdateRequest userPasswordUpdateRequest) {
        return service.updatePassword(userPasswordUpdateRequest);
    }
}