DROP DATABASE IF EXISTS mi_base_de_datos;
CREATE DATABASE mi_base_de_datos;
USE mi_base_de_datos;

-- Crear la tabla de contactos
CREATE TABLE contactos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    direccion VARCHAR(255),
    telefono VARCHAR(20)  -- Este campo se puede eliminar si no es necesario
);

-- Crear la tabla de telefonos después de contactos
CREATE TABLE telefonos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    contacto_id INT,
    telefono VARCHAR(20),
    FOREIGN KEY (contacto_id) REFERENCES contactos(id) ON DELETE CASCADE
);

-- Consultar los contactos con sus teléfonos
SELECT c.id, c.nombre, c.direccion, GROUP_CONCAT(t.telefono) AS telefonos
FROM contactos c
LEFT JOIN telefonos t ON c.id = t.contacto_id
GROUP BY c.id;

SHOW COLUMNS FROM contactos;
