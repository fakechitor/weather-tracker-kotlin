CREATE SEQUENCE locations_id_seq START 1 INCREMENT 1;

CREATE TABLE locations
(
    id        INTEGER NOT NULL DEFAULT nextval('locations_id_seq'),
    name      VARCHAR(255),
    user_id   INTEGER,
    latitude  DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    CONSTRAINT pk_locations PRIMARY KEY (id)
);

ALTER TABLE locations
    ADD CONSTRAINT FK_LOCATIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE locations
    ADD CONSTRAINT unique_latitude_longitude_user UNIQUE (latitude, longitude, user_id);