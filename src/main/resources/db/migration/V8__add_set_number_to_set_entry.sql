ALTER TABLE set_entry
    ADD COLUMN set_number INTEGER;

WITH numbered AS (
    SELECT
        id,
        ROW_NUMBER() OVER (PARTITION BY workout_entry_id ORDER BY id) AS rn
    FROM set_entry
)
UPDATE set_entry s
SET set_number = n.rn
FROM numbered n
WHERE s.id = n.id;

ALTER TABLE set_entry
    ALTER COLUMN set_number SET NOT NULL;

ALTER TABLE set_entry
    ADD CONSTRAINT uk_set_entry_workout_entry_set_number
        UNIQUE (workout_entry_id, set_number);