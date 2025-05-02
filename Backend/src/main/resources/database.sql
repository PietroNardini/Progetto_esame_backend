-- Table for Utente (Base Class)
CREATE TABLE utente (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    telefono VARCHAR(255) NOT NULL,
    dipartimento VARCHAR(255) NOT NULL
);

-- Table for Impiegato (Subclass of Utente)
CREATE TABLE impiegato (
    id INT PRIMARY KEY REFERENCES utente(id)
);

-- Table for ImpiegatoStipendiato (Subclass of Impiegato)
CREATE TABLE impiegato_stipendiato (
    id INT PRIMARY KEY REFERENCES impiegato(id),
    stipendio_mensile DOUBLE PRECISION NOT NULL
);

-- Table for ImpiegatoPagatoOra (Subclass of Impiegato)
CREATE TABLE impiegato_pagato_ora (
    id INT PRIMARY KEY REFERENCES impiegato(id),
    paga_oraria DOUBLE PRECISION NOT NULL
);

-- Table for Manager (Subclass of Utente)
CREATE TABLE manager (
    id INT PRIMARY KEY REFERENCES utente(id),
    stipendio DOUBLE PRECISION NOT NULL
);

-- Table for OraLavorativa
CREATE TABLE ora_lavorativa (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(255) NOT NULL
);

-- Join Table for Many-to-Many Relationship between Impiegato and OraLavorativa
CREATE TABLE impiegato_ora_lavorativa (
    ora_lavorativa_id INT NOT NULL REFERENCES ora_lavorativa(id),
    impiegato_id INT NOT NULL REFERENCES impiegato(id),
    PRIMARY KEY (ora_lavorativa_id, impiegato_id)
);