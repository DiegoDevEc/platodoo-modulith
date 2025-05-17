package com.playtodoo.modulith.sportcomplex.mapper;

import com.playtodoo.modulith.sportcomplex.domain.SportComplex;
import com.playtodoo.modulith.sportcomplex.validation.CreateSportComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.SportComplexDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SportComplexMapper {
    SportComplex toSportComplex(SportComplexDto dto);
    SportComplexDto toSportComplexDto(SportComplex sportComplex);
    SportComplex toCreateSportComplex(CreateSportComplexDto sportComplex);
}
