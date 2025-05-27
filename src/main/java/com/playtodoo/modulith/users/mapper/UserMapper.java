package com.playtodoo.modulith.users.mapper;

import com.playtodoo.modulith.users.domain.Role;
import com.playtodoo.modulith.users.domain.User;
import com.playtodoo.modulith.users.validation.CreateUserDTO;
import com.playtodoo.modulith.users.validation.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;
@Mapper(componentModel = "spring")
public interface UserMapper {

    // Entity → DTO
    @Mapping(target = "roles", source = "roles") // usa el default map(Set<Role>) → Set<String>
    UserDto toUserDto(User user);

    // DTO → Entity (de vuelta), mapea automáticamente si nombres coinciden
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    User toUser(UserDto userDto);

    // DTO específico para crear usuario
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    User toUserByCreateUserDto(CreateUserDTO userDto);

    // Set<Role> → Set<String>
    default Set<String> map(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream()
                .map(Role::getName) // o .getCode()
                .collect(Collectors.toSet());
    }

    // Set<String> → Set<Role>
    @Named("mapRoles")
    default Set<Role> mapRoles(Set<String> roleNames) {
        if (roleNames == null) return null;
        return roleNames.stream()
                .map(name -> new Role(null, name, null)) // Ajusta según tu constructor
                .collect(Collectors.toSet());
    }
}

