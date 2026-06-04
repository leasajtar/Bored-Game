-- Dodaj nove kolumne u tablicu users
-- Pokreni ovo ako Hibernate ne kreira kolumne automatski (ddl-auto=update bi trebao)

ALTER TABLE users ADD COLUMN IF NOT EXISTS bio VARCHAR(300);
ALTER TABLE users ADD COLUMN IF NOT EXISTS profile_picture VARCHAR(255);
