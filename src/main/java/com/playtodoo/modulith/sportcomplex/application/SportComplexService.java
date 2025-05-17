package com.playtodoo.modulith.sportcomplex.application;

import com.playtodoo.modulith.sportcomplex.validation.CreateSportComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.LandingPageComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.SportComplexDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface SportComplexService {
    Page<SportComplexDto> findAll(int page, int size, String sortBy, String direction);
    Page<SportComplexDto> findAllByPositionUser(int page, int size, String sortBy, String direction, Double latitude, Double longitude);
    SportComplexDto findById(UUID id);
    SportComplexDto create(CreateSportComplexDto dto);
    SportComplexDto update(UUID id, CreateSportComplexDto dto);
    SportComplexDto updateLandingPageConfig(UUID id, String landingPageConfig);
    LandingPageComplexDto getLandingPage(UUID id);
    void delete(UUID id);
}
