CREATE TABLE main.main_info
(
    okpo NUMERIC(10) NOT NULL PRIMARY KEY,
    name VARCHAR(200) DEFAULT '-'
);

CREATE INDEX main_info_okpo_idx ON main.main_info (okpo);
CREATE INDEX main_info_name_idx ON main.main_info (name);

ALTER TABLE main.main_info
    ADD CONSTRAINT okpo_constraint CHECK (okpo > 0 AND okpo <= 9999999999);