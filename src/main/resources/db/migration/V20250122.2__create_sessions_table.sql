CREATE TABLE sessions
(
    id         VARCHAR NOT NULL UNIQUE,
    user_id    INTEGER,
    expires_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_sessions PRIMARY KEY (id)
);

CREATE INDEX idx_expires_at ON sessions (expires_at);

ALTER TABLE sessions
    ADD CONSTRAINT FK_SESSIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);