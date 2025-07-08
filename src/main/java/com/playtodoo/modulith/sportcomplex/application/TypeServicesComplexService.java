package com.playtodoo.modulith.sportcomplex.application;

import com.playtodoo.modulith.sportcomplex.validation.CreateTypeServicesComplexDto;
import com.playtodoo.modulith.sportcomplex.validation.TypeServicesComplexDto;

public interface TypeServicesComplexService {
    TypeServicesComplexDto create(CreateTypeServicesComplexDto request);
}
