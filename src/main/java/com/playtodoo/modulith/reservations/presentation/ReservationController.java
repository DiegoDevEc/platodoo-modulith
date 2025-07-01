package com.playtodoo.modulith.reservations.presentation;

import com.playtodoo.modulith.reservations.application.ReservationService;
import com.playtodoo.modulith.reservations.validation.CreateReservationDto;
import com.playtodoo.modulith.reservations.validation.ReservationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Gestión de reservas")
public class ReservationController {

    private final ReservationService service;

    @PostMapping
    @Operation(summary = "Crear reserva", description = "Registra una nueva reserva")
    public ResponseEntity<ReservationDto> create(@Valid @RequestBody CreateReservationDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar reserva", description = "Modifica una reserva existente")
    public ResponseEntity<ReservationDto> update(@PathVariable UUID id, @Valid @RequestBody CreateReservationDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancelar reserva", description = "Cancela una reserva existente")
    public ResponseEntity<ReservationDto> cancel(@PathVariable UUID id) {
        return ResponseEntity.ok(service.cancel(id));
    }
}
