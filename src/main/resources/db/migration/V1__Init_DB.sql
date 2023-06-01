CREATE TABLE IF NOT EXISTS friendship (
    id                          BIGSERIAL PRIMARY KEY,
    src_general_status          VARCHAR(255) NOT NULL,
    src_subscription_status     VARCHAR(255) NOT NULL,
    dst_general_status          VARCHAR(255) NOT NULL,
    dst_subscription_status     VARCHAR(255) NOT NULL,
    src_person_id               BIGSERIAL NOT NULL,
    dst_person_id               BIGSERIAL NOT NULL
);