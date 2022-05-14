CREATE TABLE main.indexes
(
    id             SERIAL PRIMARY KEY,
    okpo           NUMERIC(10, 0) NOT NULL REFERENCES main.main_info (okpo) ON UPDATE CASCADE ON DELETE CASCADE,
    reporting_year NUMERIC(4, 0)  NOT NULL,
    salary         NUMERIC,
    people         INTEGER,
    revenue        NUMERIC,
    payable        NUMERIC,
    receivable     NUMERIC,
    profit         NUMERIC
);

CREATE INDEX indexes_okpo_idx ON main.indexes (okpo);

ALTER TABLE main.indexes
    ADD CONSTRAINT okpo_reporting_year_unique UNIQUE (okpo, reporting_year);
ALTER TABLE main.indexes
    ADD CONSTRAINT okpo_constraint CHECK (okpo > 0 AND okpo <= 9999999999);
ALTER TABLE main.indexes
    ADD CONSTRAINT reporting_year_constraint CHECK (reporting_year > 1000);
