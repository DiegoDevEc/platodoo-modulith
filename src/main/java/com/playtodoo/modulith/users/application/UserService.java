package com.playtodoo.modulith.users.application;

import com.playtodoo.modulith.common.PageResponse;
import com.playtodoo.modulith.sportcomplex.validation.SportComplexDto;
import com.playtodoo.modulith.users.validation.CreateUserDTO;
import com.playtodoo.modulith.users.validation.UserDto;
import com.playtodoo.modulith.users.validation.UserPasswordUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {
    UserDto createUser(CreateUserDTO dto);
    PageResponse<UserDto> findAll(int page, int size, String sortField, String sortDirection);
    UserDto getUserById(UUID id);
    UserDto assignRoleToUser(UUID userId, String roleName);
    UserDto updateUser(UUID id, CreateUserDTO dto);
    Boolean existsEmail(String email, UUID userId, String platform);
    Boolean existsPhone(String phone, UUID userId, String platform);
    Boolean existsUsername(String username, UUID userId, String platform);
    Boolean updatePassword(UserPasswordUpdateRequest request);
}
