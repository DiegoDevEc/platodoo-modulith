package com.playtodoo.modulith.reservations.application;

import com.playtodoo.modulith.reservations.validation.CreateReservationDto;
import com.playtodoo.modulith.reservations.validation.ReservationDto;

import java.util.UUID;

public interface ReservationService {
    ReservationDto create(CreateReservationDto dto);
    ReservationDto update(UUID id, CreateReservationDto dto);
    ReservationDto cancel(UUID id);
}
