CREATE TABLE service.indexes
(
    id         SERIAL PRIMARY KEY,
    okpo       NUMERIC(10) NOT NULL REFERENCES service.main_info (okpo) ON UPDATE CASCADE ON DELETE CASCADE,
    salary     NUMERIC,
    people     INTEGER,
    revenue    NUMERIC,
    payable    NUMERIC,
    receivable NUMERIC,
    profit     NUMERIC
);

CREATE INDEX indexes_okpo_idx ON service.indexes (okpo);

ALTER TABLE service.indexes
    ADD CONSTRAINT okpo_constraint CHECK (okpo > 0 AND okpo <= 9999999999);