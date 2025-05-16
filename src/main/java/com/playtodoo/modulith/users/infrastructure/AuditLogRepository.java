package com.playtodoo.modulith.users.infrastructure;

import com.playtodoo.modulith.users.domain.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {}
