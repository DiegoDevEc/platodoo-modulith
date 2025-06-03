ALTER TABLE users_user
ADD COLUMN provider VARCHAR(20) NOT NULL DEFAULT 'PLATFORM';

ALTER TABLE users_user
ADD COLUMN last_login TIMESTAMP;

ALTER TABLE users_user
ADD COLUMN email_verified BOOLEAN DEFAULT FALSE;

ALTER TABLE users_user
ADD COLUMN phone_verified BOOLEAN DEFAULT FALSE;


CREATE TABLE users_user_sportcomplex (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    sportcomplex_id UUID NOT NULL,
    status VARCHAR(3) NOT NULL DEFAULT 'ACT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users_user(id) ON DELETE CASCADE,
    CONSTRAINT uq_user_sportcomplex UNIQUE (user_id, sportcomplex_id)
);

CREATE INDEX idx_user_sportcomplex_user_id ON users_user_sportcomplex(user_id);
CREATE INDEX idx_user_sportcomplex_sportcomplex_id ON users_user_sportcomplex(sportcomplex_id);
