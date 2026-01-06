CREATE TABLE set_entry (
    id BIGSERIAL PRIMARY KEY,
    workout_entry_id BIGINT NOT NULL,
    reps INTEGER NOT NULL,
    weight NUMERIC(5,2),

    CONSTRAINT fk_set_entry_workout_entry
                       FOREIGN KEY (workout_entry_id) REFERENCES workout_entry (id)
);