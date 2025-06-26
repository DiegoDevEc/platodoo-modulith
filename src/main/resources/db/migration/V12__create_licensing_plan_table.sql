CREATE TABLE licensing_plan (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    type VARCHAR(20) NOT NULL UNIQUE,
    valid_months INTEGER NOT NULL,
    short_description VARCHAR(255),
    long_description TEXT,
    status VARCHAR(3) NOT NULL DEFAULT 'ACT'
);

INSERT INTO licensing_plan (type, valid_months, short_description, long_description)
VALUES ('FREE', 0, 'Unlimited access', 'Free plan with unlimited duration');

INSERT INTO licensing_plan (type, valid_months, short_description, long_description)
VALUES ('BASIC', 3, '3 months plan', 'Basic plan valid for three months from registration');

INSERT INTO licensing_plan (type, valid_months, short_description, long_description)
VALUES ('PREMIUM', 12, '1 year plan', 'Premium plan valid for twelve months');
