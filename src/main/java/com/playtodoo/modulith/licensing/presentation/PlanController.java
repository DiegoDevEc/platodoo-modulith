package com.playtodoo.modulith.licensing.presentation;

import com.playtodoo.modulith.common.PageResponse;
import com.playtodoo.modulith.licensing.application.PlanService;
import com.playtodoo.modulith.licensing.validation.CreatePlanDto;
import com.playtodoo.modulith.licensing.validation.PlanDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/licensing/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService service;

    @PostMapping
    public ResponseEntity<PlanDto> create(@Valid @RequestBody CreatePlanDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<PageResponse<PlanDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "type") String sortField,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(service.findAll(page, size, sortField, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanDto> update(@PathVariable UUID id, @Valid @RequestBody CreatePlanDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
