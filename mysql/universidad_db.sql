CREATE DATABASE IF NOT EXISTS universidad_db;
USE universidad_db;

DROP TABLE IF EXISTS estudiantes;

CREATE TABLE estudiantes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    semestre INT NOT NULL
);

INSERT INTO estudiantes (codigo, nombre, apellido, semestre) VALUES
('E001', 'Juan', 'Perez', 5),
('E002', 'Ana', 'Lopez', 6),
('E003', 'Pedro', 'Torres', 4),
('E004', 'Maria', 'Quispe', 7);


SELECT * FROM estudiantes;

