CREATE TABLE IF NOT EXISTS resources
(
    id   BIGINT NOT NULL,
    data bytea  NOT NULL,
    CONSTRAINT resources_pkey PRIMARY KEY (id)
);

ALTER TABLE resources
    OWNER TO postgres;

CREATE SEQUENCE resources_seq INCREMENT BY 50;

ALTER SEQUENCE resources_seq OWNER TO postgres;
