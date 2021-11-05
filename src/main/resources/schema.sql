DROP TABLE IF EXISTS planet;
DROP TABLE IF EXISTS lord;

CREATE TABLE lord
(
    id               INTEGER IDENTITY,
    name             VARCHAR NOT NULL,
    age              INTEGER NOT NULL,
    height           INTEGER NOT NULL
);

CREATE TABLE planet
(
    id               INTEGER IDENTITY,
    name             VARCHAR NOT NULL UNIQUE,
    radius           INTEGER NOT NULL,
    lord_id          INTEGER,
    FOREIGN KEY (lord_id) REFERENCES lord (id)
);