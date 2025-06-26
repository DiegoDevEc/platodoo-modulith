-- Agrega columna id_type (UUID) a sportcomplex_complex
ALTER TABLE sportcomplex_complex
ADD COLUMN id_type UUID;

-- Crea la relación con type_complex(id)
ALTER TABLE sportcomplex_complex
ADD CONSTRAINT fk_complex_type
FOREIGN KEY (id_type)
REFERENCES type_complex(id);

-- Asegura unicidad de licensing_plan.type
ALTER TABLE licensing_plan
ADD CONSTRAINT uq_plan_type UNIQUE (type);

-- Crea relación entre licensing_license.type y licensing_plan.type
ALTER TABLE licensing_license
ADD CONSTRAINT fk_license_plan_type
FOREIGN KEY (type)
REFERENCES licensing_plan(type);
