package com.playtodoo.modulith.users.application;

import com.playtodoo.modulith.users.validation.CreateUserDTO;
import com.playtodoo.modulith.users.validation.UserDto;

import java.util.UUID;

public interface UserService {
    UserDto createUser(CreateUserDTO dto);
    UserDto getUserById(UUID id);
    UserDto assignRoleToUser(UUID userId, String roleName);
    UserDto updateUser(UUID id, CreateUserDTO dto);
    Boolean existsEmail(String email, UUID userId, String platform);
    Boolean existsPhone(String phone, UUID userId, String platform);
    Boolean existsUsername(String username, UUID userId, String platform);
}
