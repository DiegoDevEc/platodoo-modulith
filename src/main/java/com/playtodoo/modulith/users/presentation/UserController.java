package com.playtodoo.modulith.users.presentation;

import com.playtodoo.modulith.common.PageResponse;
import com.playtodoo.modulith.users.application.UserService;
import com.playtodoo.modulith.users.validation.CreateUserDTO;
import com.playtodoo.modulith.users.validation.UserDto;
import com.playtodoo.modulith.users.validation.UserPasswordUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String search) {

        return ResponseEntity.ok(service.findAll(page, size, sortField, direction, search));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/{userId}/roles")
    public ResponseEntity<UserDto> assignRole(@PathVariable UUID userId, @RequestParam String role) {
        return ResponseEntity.ok(service.assignRoleToUser(userId, role));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable UUID id, @RequestBody CreateUserDTO createUserDTO) {
        return ResponseEntity.ok( service.updateUser(id, createUserDTO));
    }

    @PutMapping("/update-password")
    public ResponseEntity<Boolean> updatePassword(@Valid @RequestBody UserPasswordUpdateRequest userPasswordUpdateRequest) {
        return ResponseEntity.ok(service.updatePassword(userPasswordUpdateRequest)) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}