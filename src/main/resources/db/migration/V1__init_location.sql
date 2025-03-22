CREATE TABLE locations (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    user_id INT,
    latitude DECIMAL,
    longitude DECIMAL
)