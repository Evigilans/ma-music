CREATE TABLE IF NOT EXISTS resources
(
    id        BIGINT       NOT NULL,
    file_name VARCHAR(255) NULL,
    song_url  VARCHAR(255) NULL,
    CONSTRAINT resources_pkey PRIMARY KEY (id)
);

ALTER TABLE resources
    OWNER TO postgres;

CREATE SEQUENCE resources_seq INCREMENT BY 50;

ALTER SEQUENCE resources_seq OWNER TO postgres;
