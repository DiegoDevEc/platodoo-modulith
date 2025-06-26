package com.playtodoo.modulith.licensing.presentation;

import com.playtodoo.modulith.common.PageResponse;
import com.playtodoo.modulith.licensing.application.LicenseService;
import com.playtodoo.modulith.licensing.validation.CreateLicenseDto;
import com.playtodoo.modulith.licensing.validation.LicenseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/licensing/licenses")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService service;

    @PostMapping
    public ResponseEntity<LicenseDto> create(@Valid @RequestBody CreateLicenseDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<PageResponse<LicenseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "expirationDate") String sortField,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(service.findAll(page, size, sortField, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LicenseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LicenseDto> update(@PathVariable UUID id, @Valid @RequestBody CreateLicenseDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
