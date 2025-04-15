ALTER TABLE sessions
ADD CONSTRAINT unique_id_user_id
UNIQUE (id, user_id);