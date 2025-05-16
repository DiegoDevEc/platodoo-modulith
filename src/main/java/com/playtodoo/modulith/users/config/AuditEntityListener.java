package com.playtodoo.modulith.users.config;

// audit/AuditEntityListener.java
import com.playtodoo.modulith.users.domain.AuditLog;
import com.playtodoo.modulith.users.domain.User;
import com.playtodoo.modulith.users.infrastructure.AuditLogRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class AuditEntityListener {

    private static AuditLogRepository auditLogRepositoryStatic;
    private static ObjectMapper objectMapperStatic;

    @Autowired
    public void setAuditLogRepository(AuditLogRepository repository) {
        auditLogRepositoryStatic = repository;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper mapper) {
        objectMapperStatic = mapper;
    }

    @PrePersist
    public void prePersist(Object entity) {
        logAction(entity, "CREATE");
    }

    @PreUpdate
    public void preUpdate(Object entity) {
        logAction(entity, "UPDATE");
    }

    @PreRemove
    public void preRemove(Object entity) {
        logAction(entity, "DELETE");
    }

    private void logAction(Object entity, String action) {
        try {
            AuditLog log = new AuditLog();
            log.setEntityName(entity.getClass().getSimpleName());
            log.setEntityId(getEntityId(entity));
            log.setAction(action);
            log.setPerformedBy(getCurrentUser());
            log.setTimestamp(LocalDateTime.now());
            log.setDetails(objectMapperStatic.writeValueAsString(entity));

            auditLogRepositoryStatic.save(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getEntityId(Object entity) {
        try {
            return entity.getClass().getMethod("getId").invoke(entity).toString();
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }

    private String getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && !(authentication.getPrincipal() instanceof String)) {
           User user = (User) authentication.getPrincipal();
            return user.getUsername();
        }

        return "ANONYMOUS";
    }
}


