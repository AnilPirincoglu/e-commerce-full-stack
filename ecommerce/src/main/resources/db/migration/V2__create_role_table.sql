CREATE TABLE IF NOT EXISTS role
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(50)  NOT NULL UNIQUE,
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE role IS 'Table storing role information';
COMMENT ON COLUMN role.id IS 'Primary key: Unique identifier for each role';
COMMENT ON COLUMN role.name IS 'Name of the role (must be unique)';
COMMENT ON COLUMN role.created_at IS 'Timestamp when the role record was created';
COMMENT ON COLUMN role.updated_at IS 'Timestamp when the role record was last updated';
