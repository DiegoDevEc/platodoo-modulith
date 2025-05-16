CREATE TABLE courts_court (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    sport_type VARCHAR(50) NOT NULL,
    price_per_hour DECIMAL(10, 2) NOT NULL,
    sport_complex_id UUID NOT NULL,
    status VARCHAR(3) NOT NULL DEFAULT 'ACT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sport_complex_id) REFERENCES sportcomplex_complex(id) ON DELETE CASCADE
);

CREATE TABLE courts_availability (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    court_id UUID NOT NULL,
    day_of_week VARCHAR(3) NOT NULL, -- MON, TUE, etc.
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    status VARCHAR(3) NOT NULL DEFAULT 'ACT',
    FOREIGN KEY (court_id) REFERENCES courts_court(id) ON DELETE CASCADE
);
