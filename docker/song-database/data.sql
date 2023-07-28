CREATE TABLE IF NOT EXISTS songs
(
    id          BIGINT       NOT NULL,
    resource_id VARCHAR(255) NOT NULL,
    name        VARCHAR(255),
    artist      VARCHAR(255),
    album       VARCHAR(255),
    length      VARCHAR(255),
    year        VARCHAR(255),
    CONSTRAINT resources_pkey PRIMARY KEY (id)
);

ALTER TABLE songs
    OWNER TO postgres;

CREATE SEQUENCE songs_seq INCREMENT BY 50;

ALTER SEQUENCE songs_seq OWNER TO postgres;
