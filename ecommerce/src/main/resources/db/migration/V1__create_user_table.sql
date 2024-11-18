CREATE TABLE IF NOT EXISTS _user
(
    id             BIGSERIAL PRIMARY KEY,
    firstname      VARCHAR(50)  NOT NULL,
    lastname       VARCHAR(50)  NOT NULL,
    email          VARCHAR(80)  NOT NULL UNIQUE,
    password       VARCHAR(255) NOT NULL,
    gender         VARCHAR(6)   NOT NULL CHECK (gender IN ('MALE', 'FEMALE')),
    date_of_birth  DATE         NOT NULL,
    account_locked BOOLEAN      NOT NULL DEFAULT FALSE,
    enabled        BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE _user IS 'Table storing user information';
COMMENT ON COLUMN _user.id IS 'Primary key: Unique identifier for each user';
COMMENT ON COLUMN _user.firstname IS 'First name of the user';
COMMENT ON COLUMN _user.lastname IS 'Last name of the user';
COMMENT ON COLUMN _user.email IS 'Email address of the user (must be unique)';
COMMENT ON COLUMN _user.gender IS 'Gender of the user (MALE or FEMALE)';
COMMENT ON COLUMN _user.date_of_birth IS 'Date of birth of the user';
COMMENT ON COLUMN _user.account_locked IS 'Status indicating if the user account is locked';
COMMENT ON COLUMN _user.enabled IS 'Status indicating if the user account is enabled';
COMMENT ON COLUMN _user.created_at IS 'Timestamp when the user record was created';
COMMENT ON COLUMN _user.updated_at IS 'Timestamp when the user record was last updated';
