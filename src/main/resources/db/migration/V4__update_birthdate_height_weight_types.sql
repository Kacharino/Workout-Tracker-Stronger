ALTER TABLE users
ALTER COLUMN birthdate TYPE DATE USING birthdate::date,
    ALTER COLUMN weight TYPE double precision USING weight::double precision,
    ALTER COLUMN height TYPE double precision USING height::double precision;