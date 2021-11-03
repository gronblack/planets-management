CREATE TABLE lord
(
    id               INTEGER IDENTITY,
    name             VARCHAR NOT NULL,
    age              INTEGER NOT NULL
);

CREATE TABLE planet
(
    id               INTEGER IDENTITY,
    name             VARCHAR NOT NULL UNIQUE
);