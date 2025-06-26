package com.playtodoo.modulith.licensing.application.impl;

import com.playtodoo.modulith.common.PageResponse;
import com.playtodoo.modulith.licensing.application.PlanService;
import com.playtodoo.modulith.licensing.domain.Plan;
import com.playtodoo.modulith.licensing.exception.PlanNotFoundException;
import com.playtodoo.modulith.licensing.infrastructure.PlanRepository;
import com.playtodoo.modulith.licensing.mapper.PlanMapper;
import com.playtodoo.modulith.licensing.validation.CreatePlanDto;
import com.playtodoo.modulith.licensing.validation.PlanDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository repository;
    private final PlanMapper mapper;

    @Override
    public PlanDto create(CreatePlanDto dto) {
        Plan plan = mapper.toPlan(dto);
        Plan saved = repository.save(plan);
        return mapper.toPlanDto(saved);
    }

    @Override
    public PageResponse<PlanDto> findAll(int page, int size, String sortField, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<Plan> pageResult = repository.findAll(pageable);
        List<PlanDto> dtos = pageResult.getContent().stream().map(mapper::toPlanDto).toList();
        return PageResponse.fromPage(new PageImpl<>(dtos, pageable, pageResult.getTotalElements()), sortField, sortDirection);
    }

    @Override
    public PlanDto findById(UUID id) {
        Plan plan = repository.findById(id).orElseThrow(() -> new PlanNotFoundException(id.toString()));
        return mapper.toPlanDto(plan);
    }

    @Override
    public PlanDto update(UUID id, CreatePlanDto dto) {
        Plan plan = repository.findById(id).orElseThrow(() -> new PlanNotFoundException(id.toString()));
        plan.setType(dto.type());
        plan.setValidMonths(dto.validMonths());
        plan.setShortDescription(dto.shortDescription());
        plan.setLongDescription(dto.longDescription());
        plan.setStatus(dto.status());
        Plan updated = repository.save(plan);
        return mapper.toPlanDto(updated);
    }

    @Override
    public void delete(UUID id) {
        Plan plan = repository.findById(id).orElseThrow(() -> new PlanNotFoundException(id.toString()));
        plan.setStatus("INA");
        repository.save(plan);
    }
}
