CREATE TABLE licensing_license (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    sport_complex_id UUID NOT NULL,
    type VARCHAR(20) NOT NULL, -- FREE, BASIC, PREMIUM
    expiration_date TIMESTAMP NOT NULL,
    status VARCHAR(3) NOT NULL DEFAULT 'ACT',
    FOREIGN KEY (sport_complex_id) REFERENCES sportcomplex_complex(id) ON DELETE CASCADE
);
