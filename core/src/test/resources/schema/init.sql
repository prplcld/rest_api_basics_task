CREATE SCHEMA IF NOT EXISTS certificates_db;
USE certificates_db;

CREATE TABLE IF NOT EXISTS gift_certificate
(
    id               BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
    name             VARCHAR(50)  NOT NULL,
    description      VARCHAR(100) NOT NULL,
    price            DECIMAL(10, 2) unsigned NOT NULL,
    duration         INT UNSIGNED NOT NULL,
    create_date      DATETIME    NOT NULL,
    last_update_date DATETIME    NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tag
(
    id   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
    name VARCHAR(50) UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tags_in_certificate
(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE,
    certificate_id BIGINT UNSIGNED NOT NULL,
    tag_id         BIGINT UNSIGNED NOT NULL,
    CONSTRAINT certificate_fk FOREIGN KEY (certificate_id) REFERENCES gift_certificate (id) ON DELETE NO ACTION,
    CONSTRAINT tag_fk FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE NO ACTION
);
