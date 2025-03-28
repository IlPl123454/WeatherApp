CREATE TABLE locations
(
    id        SERIAL PRIMARY KEY,
    name      VARCHAR(255) NOT NULL ,
    user_id   INT NOT NULL,
    latitude  DECIMAL NOT NULL ,
    longitude DECIMAL NOT NULL ,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

COMMENT ON TABLE locations IS 'Локации пользователя';
COMMENT ON COLUMN locations.id IS 'Айди локации';
COMMENT ON COLUMN locations.name IS 'Наименование локации';
COMMENT ON COLUMN locations.user_id IS 'Пользователь, добавивший эту локацию';
COMMENT ON COLUMN locations.latitude IS 'Широта локации';
COMMENT ON COLUMN locations.longitude IS 'Долгота локации';
