CREATE TABLE app_users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE task (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255),
    completed BOOLEAN NOT NULL,
    due_date DATE,
    user_id VARCHAR(255),
    category VARCHAR(255)
);