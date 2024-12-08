CREATE TABLE sessions
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id    INTEGER,
    expires_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_sessions PRIMARY KEY (id)
);

ALTER TABLE sessions
    ADD CONSTRAINT FK_SESSIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);