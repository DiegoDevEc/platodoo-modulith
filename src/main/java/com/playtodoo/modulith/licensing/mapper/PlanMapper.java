package com.playtodoo.modulith.licensing.mapper;

import com.playtodoo.modulith.licensing.domain.Plan;
import com.playtodoo.modulith.licensing.validation.CreatePlanDto;
import com.playtodoo.modulith.licensing.validation.PlanDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlanMapper {
    Plan toPlan(CreatePlanDto dto);
    PlanDto toPlanDto(Plan plan);
}
