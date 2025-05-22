CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users_person (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    photo_url TEXT,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    status VARCHAR(3) NOT NULL DEFAULT 'ACT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users_user (
    id UUID PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    platform VARCHAR(20) NOT NULL DEFAULT 'BACKOFFICE',
    status VARCHAR(3) NOT NULL DEFAULT 'ACT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_person FOREIGN KEY (id) REFERENCES users_person(id) ON DELETE CASCADE,

    -- Restricciones compuestas
    CONSTRAINT uq_users_platform_username UNIQUE (platform, username),
    CONSTRAINT uq_users_platform_email UNIQUE (platform, email),
    CONSTRAINT uq_users_platform_phone UNIQUE (platform, phone)
);

CREATE TABLE users_role (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(150)
);

CREATE TABLE users_user_roles (
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users_user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES users_role(id) ON DELETE CASCADE
);
