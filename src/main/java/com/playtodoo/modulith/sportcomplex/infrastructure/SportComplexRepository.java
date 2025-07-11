package com.playtodoo.modulith.sportcomplex.infrastructure;

import com.playtodoo.modulith.sportcomplex.domain.SportComplex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SportComplexRepository extends JpaRepository<SportComplex, UUID> {
    Page<SportComplex> findAllByOwnerIdAndStatus(UUID userId, String status, Pageable pageable);

}
