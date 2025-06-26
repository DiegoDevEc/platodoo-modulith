package com.playtodoo.modulith.licensing.application;

import com.playtodoo.modulith.common.PageResponse;
import com.playtodoo.modulith.licensing.validation.CreatePlanDto;
import com.playtodoo.modulith.licensing.validation.PlanDto;

import java.util.UUID;

public interface PlanService {
    PlanDto create(CreatePlanDto dto);
    PageResponse<PlanDto> findAll(int page, int size, String sortField, String sortDirection);
    PlanDto findById(UUID id);
    PlanDto update(UUID id, CreatePlanDto dto);
    void delete(UUID id);
}
