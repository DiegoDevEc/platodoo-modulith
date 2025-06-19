package com.playtodoo.modulith.sportcomplex.application.impl;

import com.playtodoo.modulith.sportcomplex.application.TypeComplexService;
import com.playtodoo.modulith.sportcomplex.domain.TypeComplex;
import com.playtodoo.modulith.sportcomplex.exception.TypeComplexNotFoundException;
import com.playtodoo.modulith.sportcomplex.infrastructure.TypeComplexRepository;
import com.playtodoo.modulith.sportcomplex.validation.CreateTypeComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.TypeComplexDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TypeComplexServiceImpl implements TypeComplexService {
    private final TypeComplexRepository typeComplexRepository;

    @Override
    public TypeComplexDto create(CreateTypeComplexDto request) {
        TypeComplex typeComplex = new TypeComplex();
        typeComplex.setName(request.name());
        typeComplex.setDescription(request.description());
        typeComplex.setStatus("ACT");
        TypeComplex typeComplexDb = typeComplexRepository.save(typeComplex);
        return new TypeComplexDto(typeComplexDb.getId(), typeComplexDb.getName(), typeComplexDb.getDescription(), typeComplexDb.getStatus());
    }

    @Override
    public TypeComplexDto typeComplexById(UUID id) {
        TypeComplex typeComplex = typeComplexRepository.findById(id)
                .orElseThrow(
                        () -> new TypeComplexNotFoundException(id.toString())
                );
        return new TypeComplexDto(typeComplex.getId(),
                typeComplex.getName(),
                typeComplex.getDescription(),
                typeComplex.getStatus());
    }

    @Override
    public List<TypeComplexDto> getAllTypeComplex() {
        List<TypeComplex> listTypeComplex = typeComplexRepository.findAll();
        return listTypeComplex.stream().map(typeComplex ->
                new TypeComplexDto(typeComplex.getId(), typeComplex.getName(), typeComplex.getDescription(), typeComplex.getStatus()))
                .toList();
    }

    @Override
    public void delete(UUID id) {
        TypeComplex typeComplex = typeComplexRepository.findById(id)
                .orElseThrow(() -> new TypeComplexNotFoundException(id.toString()));
        typeComplex.setStatus("INA");
        typeComplexRepository.save(typeComplex);
    }

    @Override
    public TypeComplexDto update(UUID id, CreateTypeComplexDto request) {
        TypeComplex typeComplex = typeComplexRepository.findById(id)
                .orElseThrow(() -> new TypeComplexNotFoundException(id.toString()));
        typeComplex.setName(request.name());
        typeComplex.setDescription(request.description());
        TypeComplex typeComplexDb = typeComplexRepository.save(typeComplex);
        return new TypeComplexDto(typeComplexDb.getId(),
                typeComplexDb.getName(),
                typeComplexDb.getDescription(),
                typeComplexDb.getStatus());
    }
}
