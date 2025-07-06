CREATE SCHEMA IF NOT EXISTS pgml;
CREATE EXTENSION IF NOT EXISTS vector;
CREATE TABLE items (
                       id SERIAL PRIMARY KEY,
                       embedding VECTOR(3)
);
INSERT INTO items (embedding) VALUES
                                  ('[1, 2, 3]'),
                                  ('[4, 5, 6]'),
                                  ('[7, 8, 9]');

