package com.playtodoo.modulith.sportcomplex.application.impl;

import com.playtodoo.modulith.sportcomplex.application.TypeServicesComplexService;
import com.playtodoo.modulith.sportcomplex.domain.TypeComplex;
import com.playtodoo.modulith.sportcomplex.domain.TypeServicesComplex;
import com.playtodoo.modulith.sportcomplex.infrastructure.TypeServicesComplexRepository;
import com.playtodoo.modulith.sportcomplex.validation.CreateTypeComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.CreateTypeServicesComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.TypeComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.TypeServicesComplexDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeServicesComplexServiceImp implements TypeServicesComplexService {
    private final TypeServicesComplexRepository typeServicesComplexRepository;

    @Override
    public TypeServicesComplexDto create(CreateTypeServicesComplexDto request) {
        TypeServicesComplex typeServicesComplex = new TypeServicesComplex();
        typeServicesComplex.setName(request.name());
        typeServicesComplex.setDescription(request.description());
        typeServicesComplex.setStatus("ACT");
        TypeServicesComplex typeServicesComplexDb = typeServicesComplexRepository.save(typeServicesComplex);
        return new TypeServicesComplexDto(typeServicesComplexDb.getId(), typeServicesComplexDb.getName(), typeServicesComplexDb.getDescription(), typeServicesComplexDb.getStatus());
    }

}
