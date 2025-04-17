CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(255)  NOT NULL,
    password VARCHAR(255)        NOT NULL,

    CONSTRAINT unique_name UNIQUE (login)
);

COMMENT ON TABLE users IS 'Таблица с пользователями приложения';
COMMENT ON COLUMN users.id IS 'Айди пользователя';
COMMENT ON COLUMN users.login IS 'Логин пользователя, username или email';
COMMENT ON COLUMN users.password IS 'Хэшированный пароль';
