CREATE TABLE main.dates
(
    id_entry INTEGER NOT NULL PRIMARY KEY REFERENCES main.indexes (id) ON UPDATE CASCADE ON DELETE CASCADE,
    year     NUMERIC(4, 0),
    month    NUMERIC(2, 0)
);

CREATE INDEX dates_id_entry_idx ON main.dates (id_entry);