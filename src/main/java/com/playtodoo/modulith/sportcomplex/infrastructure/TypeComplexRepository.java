package com.playtodoo.modulith.sportcomplex.infrastructure;

import com.playtodoo.modulith.sportcomplex.domain.TypeComplex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TypeComplexRepository extends JpaRepository<TypeComplex, UUID> {
}
