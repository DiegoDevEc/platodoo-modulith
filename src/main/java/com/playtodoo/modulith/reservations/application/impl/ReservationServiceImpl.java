package com.playtodoo.modulith.reservations.application.impl;

import com.playtodoo.modulith.notifications.NotificationService;
import com.playtodoo.modulith.reservations.application.ReservationService;
import com.playtodoo.modulith.reservations.domain.Reservation;
import com.playtodoo.modulith.reservations.exception.ReservationNotFoundException;
import com.playtodoo.modulith.reservations.infrastructure.ReservationRepository;
import com.playtodoo.modulith.reservations.mapper.ReservationMapper;
import com.playtodoo.modulith.reservations.validation.CreateReservationDto;
import com.playtodoo.modulith.reservations.validation.ReservationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;
    private final ReservationMapper mapper;
    private final NotificationService notificationService;

    @Override
    public ReservationDto create(CreateReservationDto dto) {
        Reservation reservation = mapper.toReservation(dto);
        Reservation saved = repository.save(reservation);
        ReservationDto result = mapper.toReservationDto(saved);
        notificationService.send("Reservation created " + saved.getId());
        return result;
    }

    @Override
    public ReservationDto update(UUID id, CreateReservationDto dto) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id.toString()));
        reservation.setCourtId(dto.courtId());
        reservation.setUserId(dto.userId());
        reservation.setStartTime(dto.startTime());
        reservation.setEndTime(dto.endTime());
        if (dto.status() != null) {
            reservation.setStatus(dto.status());
        }
        Reservation updated = repository.save(reservation);
        ReservationDto result = mapper.toReservationDto(updated);
        notificationService.send("Reservation updated " + updated.getId());
        return result;
    }

    @Override
    public ReservationDto cancel(UUID id) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id.toString()));
        reservation.setStatus("CANCELLED");
        Reservation updated = repository.save(reservation);
        ReservationDto result = mapper.toReservationDto(updated);
        notificationService.send("Reservation cancelled " + updated.getId());
        return result;
    }
}
