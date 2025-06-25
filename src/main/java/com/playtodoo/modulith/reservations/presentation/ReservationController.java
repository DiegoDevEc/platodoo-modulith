package com.playtodoo.modulith.reservations.presentation;

import com.playtodoo.modulith.reservations.application.ReservationService;
import com.playtodoo.modulith.reservations.validation.CreateReservationDto;
import com.playtodoo.modulith.reservations.validation.ReservationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    @PostMapping
    public ResponseEntity<ReservationDto> create(@Valid @RequestBody CreateReservationDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> update(@PathVariable UUID id, @Valid @RequestBody CreateReservationDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ReservationDto> cancel(@PathVariable UUID id) {
        return ResponseEntity.ok(service.cancel(id));
    }
}
