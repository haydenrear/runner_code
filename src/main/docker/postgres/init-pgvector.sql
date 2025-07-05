CREATE SCHEMA IF NOT EXISTS pgml;
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS ltree;
-- test to make sure it works...
CREATE TABLE items (
                       id SERIAL PRIMARY KEY,
                       embedding VECTOR(3)
);
INSERT INTO items (embedding) VALUES
                                  ('[1, 2, 3]'),
                                  ('[4, 5, 6]'),
                                  ('[7, 8, 9]');

CREATE TABLE tree (id SERIAL PRIMARY KEY,
                    tt ltree);
