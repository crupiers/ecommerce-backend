-- Desactivar las restricciones de claves foráneas
SET FOREIGN_KEY_CHECKS = 0;

-- Vaciar las tablas sin eliminar la estructura
TRUNCATE TABLE producto;
TRUNCATE TABLE detalle_pedido;
TRUNCATE TABLE pedido;
TRUNCATE TABLE color;
TRUNCATE TABLE tamanio;
TRUNCATE TABLE categoria;
TRUNCATE TABLE marca;
TRUNCATE TABLE usuario;
TRUNCATE TABLE imagen;
TRUNCATE TABLE movimiento_stock;
-- Aquí puedes añadir más tablas si las tienes, según el esquema de tu base de datos

-- Reactivar las restricciones de claves foráneas
SET FOREIGN_KEY_CHECKS = 1;
