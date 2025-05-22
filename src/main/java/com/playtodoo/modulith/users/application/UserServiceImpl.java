package com.playtodoo.modulith.users.application;

import com.playtodoo.modulith.users.domain.Role;
import com.playtodoo.modulith.users.domain.User;
import com.playtodoo.modulith.users.exception.RoleNotFoundException;
import com.playtodoo.modulith.users.exception.UserNotFoundException;
import com.playtodoo.modulith.users.infrastructure.RoleRepository;
import com.playtodoo.modulith.users.infrastructure.UserRepository;
import com.playtodoo.modulith.users.mapper.UserMapper;
import com.playtodoo.modulith.users.validation.CreateUserDTO;
import com.playtodoo.modulith.users.validation.UserDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(CreateUserDTO dto) {
        Set<Role> userRoles = dto.roles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> {
                            log.info("Role {} not found", roleName);
                            return new RoleNotFoundException(roleName);
                        }))
                .collect(Collectors.toSet());

        User user = mapper.toUserByCreateUserDto(dto);
        user.setRoles(userRoles);
        user.setPassword(passwordEncoder.encode(dto.password()));
        return mapper.toUserDto(repository.save(user));
    }

    @Override
    public UserDto getUserById(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
        return mapper.toUserDto(user);
    }

    @Transactional
    @Override
    public UserDto assignRoleToUser(UUID userId, String roleName) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(roleName));

        user.getRoles().add(role);
        return mapper.toUserDto(repository.save(user));
    }

    @Override
    public UserDto updateUser(UUID userId, CreateUserDTO dto) {
        log.info("Updating user with ID: {}", userId);
        User user = repository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User with ID {} not found", userId);
                   return new UserNotFoundException(userId.toString());
                });

        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());

        return mapper.toUserDto(repository.save(user));
    }

    @Override
    public Boolean existsEmail(String email, UUID userId, String platform) {
        return existsValue(email, userId, platform);
    }

    @Override
    public Boolean existsPhone(String phone, UUID userId, String platform) {
        return existsValue(phone, userId, platform);
    }

    @Override
    public Boolean existsUsername(String username, UUID userId, String platform) {
        return existsValue(username, userId, platform);
    }

    private Boolean existsValue(String value, UUID userId, String platform) {
        return repository.findByEmailUsernamePhoneAndPlatform(value,platform)
                .map(user -> userId != null && user.getId().equals(userId))
                .orElse(true);
    }
}
