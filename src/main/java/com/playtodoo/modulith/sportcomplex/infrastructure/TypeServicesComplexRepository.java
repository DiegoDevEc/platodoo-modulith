package com.playtodoo.modulith.sportcomplex.infrastructure;

import com.playtodoo.modulith.sportcomplex.domain.TypeServicesComplex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TypeServicesComplexRepository extends JpaRepository<TypeServicesComplex, UUID> {
}
