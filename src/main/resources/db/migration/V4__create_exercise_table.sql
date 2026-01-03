CREATE TABLE exercise (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description VARCHAR(1000),
                          category VARCHAR(255),
                          CONSTRAINT uk_exercise_name UNIQUE (name)
);