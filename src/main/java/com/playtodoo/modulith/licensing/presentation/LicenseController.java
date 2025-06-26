package com.playtodoo.modulith.licensing.presentation;

import com.playtodoo.modulith.common.PageResponse;
import com.playtodoo.modulith.licensing.application.LicenseService;
import com.playtodoo.modulith.licensing.validation.CreateLicenseDto;
import com.playtodoo.modulith.licensing.validation.LicenseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@RestController
@RequestMapping("/api/licensing/licenses")
@RequiredArgsConstructor
@Tag(name = "Licencias", description = "Gestión de licencias")
public class LicenseController {

    private final LicenseService service;

    @PostMapping
    @Operation(summary = "Crear licencia", description = "Registra una nueva licencia")
    public ResponseEntity<LicenseDto> create(@Valid @RequestBody CreateLicenseDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    @Operation(summary = "Listar licencias", description = "Obtiene todas las licencias con paginación")
    public ResponseEntity<PageResponse<LicenseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "expirationDate") String sortField,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(service.findAll(page, size, sortField, direction));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener licencia", description = "Obtiene una licencia por su identificador")
    public ResponseEntity<LicenseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar licencia", description = "Modifica una licencia existente")
    public ResponseEntity<LicenseDto> update(@PathVariable UUID id, @Valid @RequestBody CreateLicenseDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar licencia", description = "Elimina una licencia por su identificador")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
