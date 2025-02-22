CREATE TABLE IF NOT EXISTS phone
(
    id           BIGSERIAL PRIMARY KEY,
    phone_type   VARCHAR(10) NOT NULL CHECK (phone_type IN ('HOME', 'WORK', 'MOBILE')),
    phone_number VARCHAR(13) NOT NULL UNIQUE,
    user_id      BIGINT      NOT NULL REFERENCES _user (id) ON DELETE CASCADE,
    created_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE phone IS 'Table to store phone numbers of users, including type, number, and associated user';
COMMENT ON COLUMN phone.id IS 'Primary key of the phone table, unique identifier for each phone record';
COMMENT ON COLUMN phone.phone_type IS 'Type of phone number, can be HOME, WORK, or MOBILE';
COMMENT ON COLUMN phone.phone_number IS 'Phone number, must be unique and follow a specific format';
COMMENT ON COLUMN phone.user_id IS 'Foreign key to user table, references the id of the user who owns the phone number';
COMMENT ON COLUMN phone.created_at IS 'Timestamp when the phone record was created';
COMMENT ON COLUMN phone.updated_at IS 'Timestamp when the phone record was last updated';