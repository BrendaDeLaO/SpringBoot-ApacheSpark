CREATE DATABASE universidad_db;

SELECT current_database();

CREATE TABLE matriculas (
    id SERIAL PRIMARY KEY,
    estudiante_id INT NOT NULL,
    curso VARCHAR(100) NOT NULL,
    creditos INT NOT NULL,
    semestre INT NOT NULL,
    nota DECIMAL(4,2) NOT NULL
);

INSERT INTO matriculas
    (estudiante_id, curso, creditos, semestre, nota)
VALUES
    (1, 'Java', 4, 5, 18),
    (1, 'Bases de Datos', 4, 5, 16),
    (2, 'Java', 4, 6, 15),
    (2, 'Docker', 3, 6, 17),
    (3, 'Java', 4, 4, 14),
    (3, 'Spark', 4, 4, 18),
    (4, 'Redes', 4, 7, 19);

SELECT * FROM matriculas;