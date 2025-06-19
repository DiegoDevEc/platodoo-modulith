package com.playtodoo.modulith.sportcomplex.application;

import com.playtodoo.modulith.sportcomplex.validation.CreateTypeComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.TypeComplexDto;

import java.util.List;
import java.util.UUID;

public interface TypeComplexService {
    TypeComplexDto create(CreateTypeComplexDto request);
    TypeComplexDto typeComplexById(UUID id);
    List<TypeComplexDto> getAllTypeComplex();
    void delete(UUID id);
    TypeComplexDto update(UUID id, CreateTypeComplexDto request);
}
