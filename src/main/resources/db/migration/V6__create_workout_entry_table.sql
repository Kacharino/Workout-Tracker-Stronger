CREATE TABLE workout_entry (
    id BIGSERIAL PRIMARY KEY,
    workout_id BIGINT NOT NULL,
    exercise_id BIGINT NOT NULL,

    CONSTRAINT fk_workout_entry_workout
                           FOREIGN KEY (workout_id) REFERENCES workout (id),
    CONSTRAINT fk_workout_entry_exercise
                           FOREIGN KEY (exercise_id) REFERENCES exercise (id)
);

CREATE UNIQUE INDEX ux_workout_entry_workout_exercise
    ON workout_entry (workout_id, exercise_id);