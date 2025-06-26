package com.playtodoo.modulith.reservations.infrastructure;

import com.playtodoo.modulith.reservations.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
}
