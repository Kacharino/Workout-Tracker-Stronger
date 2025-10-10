-- 1. Tabelle "excercises" umbenennen in "exercises"
ALTER TABLE excercise RENAME TO exercises;

-- 2. excercises TAbelle löschen
DROP TABLE excercises;

-- 3. "name"-Spalte aus "users" entfernen
ALTER TABLE users DROP COLUMN name;