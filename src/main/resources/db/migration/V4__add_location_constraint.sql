ALTER TABLE locations
ADD CONSTRAINT unique_locations_coordinates
UNIQUE (name, latitude, longitude);