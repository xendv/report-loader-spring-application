CREATE TABLE service.dates
(
    id_entry INTEGER NOT NULL PRIMARY KEY REFERENCES service.indexes (id) ON UPDATE CASCADE ON DELETE CASCADE,
    year     NUMERIC(4, 0),
    month    NUMERIC(2, 0)
);

CREATE INDEX dates_id_entry_idx ON service.dates (id_entry);