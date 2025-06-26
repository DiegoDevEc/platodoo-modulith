package com.playtodoo.modulith.licensing.infrastructure;

import com.playtodoo.modulith.licensing.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlanRepository extends JpaRepository<Plan, UUID> {
}
