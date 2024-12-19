DELETE FROM producto;

-- Insertar Color
DELETE FROM color;
INSERT INTO color (id, nombre, descripcion, estado)
VALUES (1, 'Black', 'Color negro', 0);
-- Insertar Tamanio
DELETE FROM tamanio;
INSERT INTO tamanio (id, nombre, descripcion, estado)
VALUES (1, 'Large', 'Tamanio grande', 0);

-- Insertar Categoria
DELETE FROM categoria;
INSERT INTO categoria (id, nombre, descripcion, estado)
VALUES (1, 'Electronics', 'Categoria de productos electronicos', 0);

-- Insertar Marca
DELETE FROM marca;
INSERT INTO marca (id, nombre, descripcion, estado)
VALUES (1, 'Samsung', 'Marca lider en tecnología de dispositivos moviles', 0);

-- Insertar Producto
-- INSERT INTO producto (id, nombre, descripcion, stock, codigo_barra, precio, umbral, estado, id_color, id_tamanio, id_categoria , id_marca)
-- VALUES (11, 'Samsung Galaxy 11', 'Smartphone de alta gama', 100, 123456789, 799.99, 10, 5, 11, 11, 11, 11);
