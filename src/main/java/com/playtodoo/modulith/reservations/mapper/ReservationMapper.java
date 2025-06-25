package com.playtodoo.modulith.reservations.mapper;

import com.playtodoo.modulith.reservations.domain.Reservation;
import com.playtodoo.modulith.reservations.validation.CreateReservationDto;
import com.playtodoo.modulith.reservations.validation.ReservationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    Reservation toReservation(CreateReservationDto dto);
    ReservationDto toReservationDto(Reservation reservation);
}
