package com.playtodoo.modulith.licensing.mapper;

import com.playtodoo.modulith.licensing.domain.License;
import com.playtodoo.modulith.licensing.validation.CreateLicenseDto;
import com.playtodoo.modulith.licensing.validation.LicenseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LicenseMapper {
    @Mapping(target = "plan", ignore = true)
    License toLicense(CreateLicenseDto dto);

    @Mapping(source = "plan.type", target = "planType")
    LicenseDto toLicenseDto(License license);
}
