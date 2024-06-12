CREATE DATABASE clinicadb WITH OWNER postgres ALLOW_CONNECTIONS true;

\c clinicadb;

CREATE SEQUENCE patients_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE IF NOT EXISTS patients (
    id BIGINT NOT NULL DEFAULT nextval('patients_id_seq'::regclass),
    nome VARCHAR(128),
    sobrenome VARCHAR(128),
    sexo CHAR(1),
    nascimento DATE,
    idade INTEGER DEFAULT 0,
    altura FLOAT,
    peso FLOAT,
    cpf VARCHAR(11),
    imc FLOAT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);

CREATE INDEX idx_patients_id ON patients(id);
