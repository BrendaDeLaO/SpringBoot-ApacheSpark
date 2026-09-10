CREATE DATABASE IF NOT EXISTS universidad_db;
USE universidad_db;

DROP TABLE IF EXISTS carrera_universidad;

CREATE TABLE carrera_universidad (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo_estudiante VARCHAR(20) NOT NULL,
    carrera VARCHAR(100) NOT NULL,
    universidad VARCHAR(100) NOT NULL
);

INSERT INTO carrera_universidad (codigo_estudiante, carrera, universidad) VALUES
('E001', 'Ingeniería de Sistemas', 'Universidad Valle Grande'),
('E002', 'Ingeniería de Software', 'Universidad Valle Grande'),
('E003', 'Ingeniería de Sistemas', 'Universidad Continental'),
('E004', 'Ingeniería de Redes', 'Universidad Continental');

SELECT * FROM carrera_universidad;