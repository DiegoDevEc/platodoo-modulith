CREATE TABLE sportcomplex_complex (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    description TEXT,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    landing_page_config TEXT,
    status VARCHAR(3) NOT NULL DEFAULT 'ACT',
    owner_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES users_user(id) ON DELETE CASCADE
);
