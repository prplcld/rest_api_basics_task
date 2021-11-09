CREATE TABLE gift_certificate
(
    id               BIGINT PRIMARY KEY,
    name             VARCHAR(45) NOT NULL,
    description      VARCHAR(45) NOT NULL,
    price            DECIMAL     NOT NULL,
    duration         INT         NOT NULL,
    create_date      DATETIME   NOT NULL,
    last_update_date DATETIME   NOT NULL
);

CREATE TABLE tag
(
    id   BIGSINT PRIMARY KEY,
    name VARCHAR(45) UNIQUE
);

CREATE TABLE user
(
    id   BIGSINT PRIMARY KEY,
    name VARCHAR(45) UNIQUE
);

CREATE TABLE orders
(
    id            BIGINT PRIMARY KEY,
    cost          DECIMAL(10, 2) NOT NULL,
    purchase_date DATETIME      NOT NULL,
    user_id       BIGINT         NOT NULL,

    CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE tags_in_certificate
(
    certificate_id BIGINT NOT NULL,
    tag_id         BIGINT NOT NULL,

    CONSTRAINT ct_certificate_fk FOREIGN KEY (certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    CONSTRAINT ct_tag_fk FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);

CREATE TABLE certificates_in_order
(
    certificate_id BIGINT NOT NULL,
    order_id       BIGINT NOT NULL,

    CONSTRAINT co_certificate_fk FOREIGN KEY (certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    CONSTRAINT co_order_fk FOREIGN KEY (order_id) REFERENCES app_order (id)
);

CREATE TABLE audit
(
    id          BIGSERIAL PRIMARY KEY,
    operation   VARCHAR(20),
    entity_name VARCHAR(50),
    timestamp   DATETIME
);