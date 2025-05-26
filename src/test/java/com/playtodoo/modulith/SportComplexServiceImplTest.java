package com.playtodoo.modulith;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtodoo.modulith.sportcomplex.application.impl.SportComplexServiceImpl;
import com.playtodoo.modulith.sportcomplex.domain.SportComplex;
import com.playtodoo.modulith.sportcomplex.exception.SportComplexNotFoundException;
import com.playtodoo.modulith.sportcomplex.infrastructure.SportComplexRepository;
import com.playtodoo.modulith.sportcomplex.mapper.SportComplexMapper;
import com.playtodoo.modulith.sportcomplex.validation.CreateSportComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.SportComplexDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class SportComplexServiceImplTest {

    @Mock
    private SportComplexRepository repository;

    @Mock
    private SportComplexMapper mapper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private SportComplexServiceImpl service;

    @Test
    void shouldFindSportComplexByIdSuccessfully() {
        UUID id = UUID.randomUUID();
        SportComplex entity = new SportComplex();
        entity.setId(id);

        SportComplexDto expectedDto = new SportComplexDto(
                id, "Complex Name", "Description", 0.0, 0.0, null, UUID.randomUUID(), "ACT"
        );

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toSportComplexDto(entity)).thenReturn(expectedDto);

        SportComplexDto result = service.findById(id);

        assertThat(result).isEqualTo(expectedDto);
        verify(repository).findById(id);
        verify(mapper).toSportComplexDto(entity);
    }

    @Test
    void shouldThrowExceptionWhenSportComplexNotFound() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        SportComplexNotFoundException exception = assertThrows(
                SportComplexNotFoundException.class,
                () -> service.findById(id)
        );

        assertThat(exception.getMessage()).contains(id.toString());
        verify(repository).findById(id);
    }

    @Test
    void shouldCreateSportComplexSuccessfully() {
        CreateSportComplexDto createDto = new CreateSportComplexDto(
                "Complex Name", "Description", 0.0, 0.0, UUID.randomUUID(), "ACT"
        );

        SportComplex entity = new SportComplex();
        entity.setId(UUID.randomUUID());

        SportComplexDto expectedDto = new SportComplexDto(
                entity.getId(),
                createDto.name(),
                createDto.description(),
                createDto.latitude(),
                createDto.longitude(),
                null,
                createDto.ownerId(),
                createDto.status()
        );

        when(mapper.toCreateSportComplex(createDto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toSportComplexDto(entity)).thenReturn(expectedDto);

        SportComplexDto result = service.create(createDto);

        assertThat(result).isEqualTo(expectedDto);
        verify(repository).save(entity);
        verify(mapper).toCreateSportComplex(createDto);
        verify(mapper).toSportComplexDto(entity);
    }

    @Test
    void shouldHandleDeleteWhenComplexIsActive() {
        UUID id = UUID.randomUUID();
        SportComplex entity = new SportComplex();
        entity.setId(id);
        entity.setStatus("ACT");

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        service.delete(id);

        assertThat(entity.getStatus()).isEqualTo("INA");
        verify(repository).save(entity);
    }

    @Test
    void shouldNotDeleteWhenComplexAlreadyInactive() {
        UUID id = UUID.randomUUID();
        SportComplex entity = new SportComplex();
        entity.setId(id);
        entity.setStatus("INA");

        when(repository.findById(id)).thenReturn(Optional.of(entity));

        service.delete(id);

        verify(repository, never()).save(any());
    }
}
