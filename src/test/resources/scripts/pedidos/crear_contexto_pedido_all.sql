-- Insertar Color
INSERT INTO color (id, nombre, descripcion, estado)
VALUES (1, 'Black', 'Color negro', 0);
-- Insertar Tamanio
INSERT INTO tamanio (id, nombre, descripcion, estado)
VALUES (1, 'Large', 'Tamanio grande', 0);

-- Insertar Categoria
INSERT INTO categoria (id, nombre, descripcion, estado)
VALUES (1, 'Electronics', 'Categoria de productos electronicos', 0);

-- Insertar Marca
INSERT INTO marca (id, nombre, descripcion, estado)
VALUES (1, 'Samsung', 'Marca lider en tecnología de dispositivos moviles', 0);

-- Insertar Producto
INSERT INTO producto (id, nombre, descripcion, stock, codigo_barra, precio, umbral, estado, id_color, id_tamanio, id_categoria , id_marca)
VALUES (1, 'Samsung Galaxy 11', 'Smartphone de alta gama', 10, 123456789, 799.99, 10, 0, 1, 1, 1, 1);

-- --Insertar DetallePedido
-- INSERT INTO detalle_pedido (id, cantidad, estado, subtotal, id_producto)
-- VALUES (1, 5, 0, 0.00, 1)

INSERT INTO usuario (id, nombre, contrasenia, rol, estado) 
VALUES (1, 'nacho', 'Hola.1234', 1, 0);