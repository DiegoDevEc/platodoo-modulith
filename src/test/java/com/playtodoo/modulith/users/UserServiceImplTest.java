package com.playtodoo.modulith.users;

import com.playtodoo.modulith.common.PageResponse;
import com.playtodoo.modulith.users.application.UserServiceImpl;
import com.playtodoo.modulith.users.domain.Role;
import com.playtodoo.modulith.users.domain.User;
import com.playtodoo.modulith.users.domain.UserPasswordOld;
import com.playtodoo.modulith.users.exception.RoleNotFoundException;
import com.playtodoo.modulith.users.exception.TechnicalErrorException;
import com.playtodoo.modulith.users.exception.UserNotFoundException;
import com.playtodoo.modulith.users.infrastructure.RoleRepository;
import com.playtodoo.modulith.users.infrastructure.UserPasswordRepository;
import com.playtodoo.modulith.users.infrastructure.UserRepository;
import com.playtodoo.modulith.users.mapper.UserMapper;
import com.playtodoo.modulith.users.validation.CreateUserDTO;
import com.playtodoo.modulith.users.validation.UserDto;
import com.playtodoo.modulith.users.validation.UserPasswordUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    private UserRepository repository;
    @Mock
    private UserPasswordRepository userPasswordRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserMapper mapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void shouldCreateUserSuccessfully() {
        CreateUserDTO dto = new CreateUserDTO(
                "user1","John","Doe","ACT","john@example.com","123","MANAGER","PLATFORM","pass",Set.of("ROLE_ADMIN"),true);
        Role role = new Role(UUID.randomUUID(),"ROLE_ADMIN",null);
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(role));
        User user = new User();
        when(mapper.toUserByCreateUserDto(dto)).thenReturn(user);
        when(passwordEncoder.encode("pass")).thenReturn("enc");
        when(repository.save(any(User.class))).thenReturn(user);
        UserDto expected = new UserDto(null,"user1","john@example.com","123","John","Doe","ACT",null,"MANAGER",true,false,Set.of("ROLE_ADMIN"));
        when(mapper.toUserDto(user)).thenReturn(expected);

        UserDto result = service.createUser(dto);

        assertThat(result).isEqualTo(expected);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getPassword()).isEqualTo("enc");
        verify(roleRepository).findByName("ROLE_ADMIN");
    }

    @Test
    void shouldReturnUserById() {
        UUID id = UUID.randomUUID();
        User user = new User();
        when(repository.findById(id)).thenReturn(Optional.of(user));
        UserDto expected = new UserDto(id,"user","mail","phone","fn","ln","ACT",null,"MANAGER",true,false,Set.of());
        when(mapper.toUserDto(user)).thenReturn(expected);
        UserDto result = service.getUserById(id);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.getUserById(id));
    }

    @Test
    void shouldAssignRoleToUser() {
        UUID userId = UUID.randomUUID();
        Role role = new Role(UUID.randomUUID(),"ROLE_ADMIN",null);
        User user = new User();
        when(repository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(role));
        when(repository.save(user)).thenReturn(user);
        UserDto expected = new UserDto(userId,"user","mail","phone","fn","ln","ACT",null,"MANAGER",true,false,Set.of("ROLE_ADMIN"));
        when(mapper.toUserDto(user)).thenReturn(expected);

        UserDto result = service.assignRoleToUser(userId,"ROLE_ADMIN");

        assertThat(result).isEqualTo(expected);
        assertThat(user.getRoles()).contains(role);
    }

    @Test
    void shouldUpdatePasswordSuccessfully() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setPassword("old");
        when(repository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("old","old")).thenReturn(true);
        when(passwordEncoder.encode("new")).thenReturn("enc");
        UserPasswordUpdateRequest request = new UserPasswordUpdateRequest(id,"new","old");

        boolean result = service.updatePassword(request);

        assertThat(result).isTrue();
        assertThat(user.getPassword()).isEqualTo("enc");
        verify(repository).save(user);
        verify(userPasswordRepository).save(any(UserPasswordOld.class));
    }

    @Test
    void shouldThrowWhenOldPasswordEqualsNew() {
        UUID id = UUID.randomUUID();
        User user = new User();
        when(repository.findById(id)).thenReturn(Optional.of(user));
        UserPasswordUpdateRequest request = new UserPasswordUpdateRequest(id,"pass","pass");
        assertThrows(TechnicalErrorException.class, () -> service.updatePassword(request));
    }

    @Test
    void shouldDeleteUser() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setStatus("ACT");
        when(repository.findById(id)).thenReturn(Optional.of(user));
        service.delete(id);
        assertThat(user.getStatus()).isEqualTo("INA");
        verify(repository).save(user);
    }

    @Test
    void shouldCheckExistsEmail() {
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setId(id);
        when(repository.findByEmailUsernamePhoneAndPlatform("mail","MANAGER")).thenReturn(Optional.of(user));
        boolean result = service.existsEmail("mail",id,"MANAGER");
        assertThat(result).isTrue();
    }
}
