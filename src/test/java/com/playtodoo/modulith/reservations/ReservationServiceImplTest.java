package com.playtodoo.modulith.reservations;

import com.playtodoo.modulith.notifications.NotificationService;
import com.playtodoo.modulith.reservations.application.impl.ReservationServiceImpl;
import com.playtodoo.modulith.reservations.domain.Reservation;
import com.playtodoo.modulith.reservations.exception.ReservationNotFoundException;
import com.playtodoo.modulith.reservations.infrastructure.ReservationRepository;
import com.playtodoo.modulith.reservations.mapper.ReservationMapper;
import com.playtodoo.modulith.reservations.validation.CreateReservationDto;
import com.playtodoo.modulith.reservations.validation.ReservationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository repository;
    @Mock
    private ReservationMapper mapper;
    @Mock
    private NotificationService notificationService;
    @InjectMocks
    private ReservationServiceImpl service;

    @Test
    void shouldCreateReservation() {
        CreateReservationDto dto = new CreateReservationDto(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now().plusHours(1), "PENDING");
        Reservation reservation = new Reservation();
        when(mapper.toReservation(dto)).thenReturn(reservation);
        when(repository.save(reservation)).thenReturn(reservation);
        ReservationDto expected = new ReservationDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), null, null, null, null);

        when(mapper.toReservationDto(reservation)).thenReturn(expected);

        ReservationDto result = service.create(dto);

        assertThat(result).isEqualTo(expected);
        verify(notificationService).send(startsWith("Reservation created"));
    }

    @Test
    void shouldUpdateReservation() {
        UUID id = UUID.randomUUID();
        CreateReservationDto dto = new CreateReservationDto(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now().plusHours(1), "CONFIRMED");
        Reservation reservation = new Reservation();
        when(repository.findById(id)).thenReturn(Optional.of(reservation));
        when(repository.save(reservation)).thenReturn(reservation);

        ReservationDto expected = new ReservationDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), null, null, null, null);
        when(mapper.toReservationDto(reservation)).thenReturn(expected);

        ReservationDto result = service.update(id, dto);

        assertThat(result).isEqualTo(expected);
        verify(notificationService).send(startsWith("Reservation updated"));
    }

    @Test
    void shouldThrowWhenReservationNotFound() {
        UUID id = UUID.randomUUID();
        CreateReservationDto dto = new CreateReservationDto(UUID.randomUUID(), UUID.randomUUID(), LocalDateTime.now(), LocalDateTime.now().plusHours(1), "CONFIRMED");
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> service.update(id, dto));
    }

    @Test
    void shouldCancelReservation() {
        UUID id = UUID.randomUUID();
        Reservation reservation = new Reservation();
        when(repository.findById(id)).thenReturn(Optional.of(reservation));
        when(repository.save(reservation)).thenReturn(reservation);
        ReservationDto expected = new ReservationDto(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), null, null, null, null);
        when(mapper.toReservationDto(reservation)).thenReturn(expected);

        ReservationDto result = service.cancel(id);

        assertThat(result).isEqualTo(expected);
        verify(notificationService).send(startsWith("Reservation cancelled"));
    }
}
