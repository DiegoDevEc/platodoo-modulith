package com.playtodoo.modulith.licensing.application.impl;

import com.playtodoo.modulith.common.PageResponse;
import com.playtodoo.modulith.licensing.application.LicenseService;
import com.playtodoo.modulith.licensing.domain.License;
import com.playtodoo.modulith.licensing.domain.Plan;
import com.playtodoo.modulith.licensing.exception.LicenseNotFoundException;
import com.playtodoo.modulith.licensing.exception.PlanNotFoundException;
import com.playtodoo.modulith.licensing.infrastructure.LicenseRepository;
import com.playtodoo.modulith.licensing.infrastructure.PlanRepository;
import com.playtodoo.modulith.licensing.mapper.LicenseMapper;
import com.playtodoo.modulith.licensing.validation.CreateLicenseDto;
import com.playtodoo.modulith.licensing.validation.LicenseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository repository;
    private final PlanRepository planRepository;
    private final LicenseMapper mapper;

    @Override
    public LicenseDto create(CreateLicenseDto dto) {
        License license = mapper.toLicense(dto);
        Plan plan = planRepository.findByType(dto.planType())
                .orElseThrow(() -> new PlanNotFoundException(dto.planType()));
        license.setPlan(plan);
        License saved = repository.save(license);
        return mapper.toLicenseDto(saved);
    }

    @Override
    public PageResponse<LicenseDto> findAll(int page, int size, String sortField, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        Page<License> pageResult = repository.findAll(pageable);
        List<LicenseDto> dtos = pageResult.getContent().stream().map(mapper::toLicenseDto).toList();
        return PageResponse.fromPage(new PageImpl<>(dtos, pageable, pageResult.getTotalElements()), sortField, sortDirection);
    }

    @Override
    public LicenseDto findById(UUID id) {
        License license = repository.findById(id).orElseThrow(() -> new LicenseNotFoundException(id.toString()));
        return mapper.toLicenseDto(license);
    }

    @Override
    public LicenseDto update(UUID id, CreateLicenseDto dto) {
        License license = repository.findById(id).orElseThrow(() -> new LicenseNotFoundException(id.toString()));
        Plan plan = planRepository.findByType(dto.planType())
                .orElseThrow(() -> new PlanNotFoundException(dto.planType()));
        license.setSportComplexId(dto.sportComplexId());
        license.setPlan(plan);
        license.setExpirationDate(dto.expirationDate());
        license.setStatus(dto.status());
        License updated = repository.save(license);
        return mapper.toLicenseDto(updated);
    }

    @Override
    public void delete(UUID id) {
        License license = repository.findById(id).orElseThrow(() -> new LicenseNotFoundException(id.toString()));
        license.setStatus("INA");
        repository.save(license);
    }
}
