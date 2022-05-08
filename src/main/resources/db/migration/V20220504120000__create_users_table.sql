CREATE TABLE security.user
(
    id       SERIAL       NOT NULL PRIMARY KEY,
    login    VARCHAR(200) NOT NULL UNIQUE,
    password VARCHAR(30)  NOT NULL
);

CREATE INDEX user_login_idx ON security.user (login);
CREATE INDEX user_id_idx ON security.user (id);