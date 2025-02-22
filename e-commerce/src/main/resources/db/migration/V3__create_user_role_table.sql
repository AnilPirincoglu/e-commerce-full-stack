CREATE TABLE IF NOT EXISTS user_role
(
    user_id    BIGINT REFERENCES _user (id) ON DELETE CASCADE,
    role_id    BIGINT REFERENCES role (id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id)
);

COMMENT ON TABLE user_role IS 'Table storing user-role relationships, linking users to their roles';
COMMENT ON COLUMN user_role.user_id IS 'Foreign key referencing the id column in the _user table.';
COMMENT ON COLUMN user_role.role_id IS 'Foreign key referencing the id column in the role table.';
COMMENT ON COLUMN user_role.created_at IS 'Timestamp when the user-role record was created.';
COMMENT ON COLUMN user_role.updated_at IS 'Timestamp when the user-role record was last updated.';

