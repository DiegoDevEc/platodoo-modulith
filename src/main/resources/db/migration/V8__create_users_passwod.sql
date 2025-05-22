CREATE TABLE users_user_password (
    id UUID NOT NULL,
    user_id UUID NOT NULL,
    old_password TEXT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users_user(id) ON DELETE CASCADE
);
