CREATE TABLE audit_logs (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    entity_name VARCHAR(100) NOT NULL,
    entity_id VARCHAR(100) NOT NULL,
    action VARCHAR(20) NOT NULL, -- Ejemplo: CREATE, UPDATE, DELETE
    performed_by VARCHAR(100) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    details TEXT
);

-- Índices para optimizar consultas de auditoría
CREATE INDEX idx_audit_entity_name ON audit_logs (entity_name);
CREATE INDEX idx_audit_action ON audit_logs (action);
CREATE INDEX idx_audit_performed_by ON audit_logs (performed_by);
CREATE INDEX idx_audit_timestamp ON audit_logs (timestamp);
