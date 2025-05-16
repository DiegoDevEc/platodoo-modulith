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
    UserDto toUserDto(User user);
    User toUser(UserDto userDto);
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    User toUserByCreateUserDto(CreateUserDTO userDto);

    @Named("mapRoles")
    default Set<Role> mapRoles(Set<String> roleNames) {
        // Si usas Spring, deberías inyectar el RoleRepository para buscar en DB.
        return roleNames.stream()
                .map(name -> new Role(null, name, null))  // Solo asigna el nombre, el ID se manejará en DB
                .collect(Collectors.toSet());
    }
}
