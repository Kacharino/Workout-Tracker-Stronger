CREATE TABLE workout (
                         id BIGSERIAL PRIMARY KEY,
                         workout_name VARCHAR(100) NOT NULL,
                         date DATE NOT NULL,
                         duration TIME,
                         user_id BIGINT NOT NULL,
                         CONSTRAINT fk_workout_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);