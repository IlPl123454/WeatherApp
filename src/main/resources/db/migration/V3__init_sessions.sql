CREATE TABLE sessions
(
    id         UUID PRIMARY KEY,
    user_id    INT                         NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT unique_id_user_id
        UNIQUE (id, user_id),

    FOREIGN KEY (user_id) REFERENCES users (id)
);

COMMENT ON TABLE sessions IS 'Сессии';
COMMENT ON COLUMN sessions.id IS 'Айди сесии';
COMMENT ON COLUMN sessions.user_id IS 'Айди пользователя, для которого создана сессия';
COMMENT ON COLUMN sessions.expires_at IS 'Время истечения сессии. Равно времени создания сессии плюс N часов'