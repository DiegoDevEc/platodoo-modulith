package com.playtodoo.modulith.users.application;

import com.playtodoo.modulith.users.validation.CreateUserDTO;
import com.playtodoo.modulith.users.validation.UserDto;

import java.util.UUID;

public interface UserService {
    UserDto createUser(CreateUserDTO dto);
    UserDto getUserById(UUID id);
    UserDto assignRoleToUser(UUID userId, String roleName);

}
