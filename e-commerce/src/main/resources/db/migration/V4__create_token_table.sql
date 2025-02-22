CREATE TABLE IF NOT EXISTS token
(
    id           BIGSERIAL PRIMARY KEY,
    token        VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at   TIMESTAMP    NOT NULL,
    validated_at TIMESTAMP,
    user_id      BIGINT REFERENCES _user (id) ON DELETE CASCADE
);

COMMENT ON TABLE token IS 'Table storing token information';
COMMENT ON COLUMN token.id IS 'Primary key: Unique identifier for each token';
COMMENT ON COLUMN token.token IS 'Token value';
COMMENT ON COLUMN token.created_at IS 'Timestamp when the token record was created';
COMMENT ON COLUMN token.expires_at IS 'Timestamp when the token expires';
COMMENT ON COLUMN token.validated_at IS 'Timestamp when the token was validated';
COMMENT ON COLUMN token.user_id IS 'Foreign key: User ID of the user associated with the token';
