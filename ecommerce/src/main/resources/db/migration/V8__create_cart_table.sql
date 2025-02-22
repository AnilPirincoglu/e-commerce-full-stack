CREATE TABLE IF NOT EXISTS cart
(
    id          BIGSERIAL PRIMARY KEY,
    total_price DECIMAL(10, 2) NOT NULL,
    user_id     BIGINT         NOT NULL REFERENCES _user (id) ON DELETE CASCADE,
    created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE cart IS 'Table to store shopping cart information, including total price, user reference, and timestamps';
COMMENT ON COLUMN cart.id IS 'Unique identifier for the cart, generated automatically as a BIGSERIAL';
COMMENT ON COLUMN cart.total_price IS 'Total price of the items in the cart, with two decimal places';
COMMENT ON COLUMN cart.user_id IS 'Reference to the user owning this cart, with cascading delete on user removal';
COMMENT ON COLUMN cart.created_at IS 'Timestamp of when the cart was created, defaults to current timestamp';
COMMENT ON COLUMN cart.updated_at IS 'Timestamp of the last update to the cart, defaults to current timestamp';
