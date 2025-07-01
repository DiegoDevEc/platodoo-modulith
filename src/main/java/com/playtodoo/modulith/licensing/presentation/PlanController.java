package com.playtodoo.modulith.licensing.presentation;

import com.playtodoo.modulith.common.PageResponse;
import com.playtodoo.modulith.licensing.application.PlanService;
import com.playtodoo.modulith.licensing.validation.CreatePlanDto;
import com.playtodoo.modulith.licensing.validation.PlanDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@RestController
@RequestMapping("/api/licensing/plans")
@RequiredArgsConstructor
@Tag(name = "Planes", description = "Gestión de planes de licenciamiento")
public class PlanController {

    private final PlanService service;

    @PostMapping
    @Operation(summary = "Crear un plan", description = "Registra un nuevo plan de licenciamiento")
    public ResponseEntity<PlanDto> create(@Valid @RequestBody CreatePlanDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    @Operation(summary = "Listar planes", description = "Obtiene todos los planes con paginación")
    public ResponseEntity<PageResponse<PlanDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "type") String sortField,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(service.findAll(page, size, sortField, direction));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener plan", description = "Obtiene un plan por su identificador")
    public ResponseEntity<PlanDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar plan", description = "Modifica los datos de un plan existente")
    public ResponseEntity<PlanDto> update(@PathVariable UUID id, @Valid @RequestBody CreatePlanDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar plan", description = "Elimina un plan por su identificador")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
