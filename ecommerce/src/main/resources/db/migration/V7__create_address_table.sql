CREATE TABLE IF NOT EXISTS address
(
    id           BIGSERIAL PRIMARY KEY,
    address_type VARCHAR(6)   NOT NULL CHECK ( address_type IN ('HOME', 'OFFICE', 'OTHER') ),
    address_line VARCHAR(255) NOT NULL,
    street       VARCHAR(50)  NOT NULL,
    district     VARCHAR(50)  NOT NULL,
    city         VARCHAR(50)  NOT NULL,
    postal_code  VARCHAR(5)   NOT NULL,
    user_id      BIGINT       NOT NULL REFERENCES _user (id) ON DELETE CASCADE,
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE address IS 'Table to store user addresses, including type, line, street, district, city, postal code, and user reference';
COMMENT ON COLUMN address.id IS 'Unique identifier for the address, generated automatically as a BIGSERIAL';
COMMENT ON COLUMN address.address_type IS 'Type of address, restricted to HOME, OFFICE, or OTHER';
COMMENT ON COLUMN address.address_line IS 'Detailed address line, up to 255 characters';
COMMENT ON COLUMN address.street IS 'Street name, up to 50 characters';
COMMENT ON COLUMN address.district IS 'District name, up to 50 characters';
COMMENT ON COLUMN address.city IS 'City name, up to 50 characters';
COMMENT ON COLUMN address.postal_code IS 'Postal code, exactly 5 characters';
COMMENT ON COLUMN address.user_id IS 'Reference to the user owning this address, with cascading delete on user removal';
COMMENT ON COLUMN address.created_at IS 'Timestamp of when the address was created, defaults to current timestamp';
COMMENT ON COLUMN address.updated_at IS 'Timestamp of the last update to the address, defaults to current timestamp';
