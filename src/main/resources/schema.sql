DROP TABLE todos;

CREATE TABLE IF NOT EXISTS todos (
    id       VARCHAR(60)  PRIMARY KEY,
    title     VARCHAR      NOT NULL,
    description     VARCHAR      NOT NULL,
    date     VARCHAR      NOT NULL,
    time     VARCHAR      NOT NULL,
    isChecked     BOOL      NOT NULL
    );