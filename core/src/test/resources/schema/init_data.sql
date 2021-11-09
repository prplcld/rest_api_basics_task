INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('certificate1', 'certificate1', 1, 1, '2021-09-25 00:00:00', '2021-09-25 00:00:00');

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('certificate2', 'certificate2', 1.1, 2, '2021-09-25 00:00:00', '2021-09-25 00:00:00');

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('certificate3', 'certificate3', 10.3, 10, '2021-09-25 00:00:00', '2021-09-25 00:00:00');

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('certificate4', 'certificate4', 1.0, 20, '2021-09-25 00:00:00', '2021-09-25 00:00:00');

INSERT INTO tag (id, name)
VALUES (1, 'tag');

INSERT INTO tag (id, name)
VALUES (2, 'tag1');

INSERT INTO tag (id, name)
VALUES (3, 'tag2');

INSERT INTO tags_in_certificate (certificate_id, tag_id)
VALUES (1, 1);