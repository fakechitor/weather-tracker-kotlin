CREATE SEQUENCE users_id_seq START 1 INCREMENT 1;

CREATE TABLE users
(
    id       INT NOT NULL DEFAULT nextval('users_id_seq'),
    login    VARCHAR(255) UNIQUE CHECK (LENGTH(login) BETWEEN 3 AND 30),
    password VARCHAR(255) CHECK (LENGTH(login) BETWEEN 3 AND 30),
    CONSTRAINT pk_users PRIMARY KEY (id)
);