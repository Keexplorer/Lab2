--
-- Schema: public; Table: con_countries;
--
CREATE TABLE countries (
    id BIGSERIAL PRIMARY KEY,
    alpha2_code VARCHAR(02) UNIQUE NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL
);