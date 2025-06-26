package com.playtodoo.modulith.licensing.application;

import com.playtodoo.modulith.common.PageResponse;
import com.playtodoo.modulith.licensing.validation.CreateLicenseDto;
import com.playtodoo.modulith.licensing.validation.LicenseDto;

import java.util.UUID;

public interface LicenseService {
    LicenseDto create(CreateLicenseDto dto);
    PageResponse<LicenseDto> findAll(int page, int size, String sortField, String sortDirection);
    LicenseDto findById(UUID id);
    LicenseDto update(UUID id, CreateLicenseDto dto);
    void delete(UUID id);
}
