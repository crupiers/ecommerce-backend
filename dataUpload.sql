-- Insert Colors
INSERT IGNORE INTO color (nombre, descripcion, estado, created_at) VALUES
('Rojo', 'Color rojo', 0, '2023-01-01'),
('Azul', 'Color azul', 0, '2023-01-01'),
('Verde', 'Color verde', 0, '2023-01-01'),
('Amarillo', 'Color amarillo', 0, '2023-01-01'),
('Negro', 'Color negro', 0, '2023-01-01');

-- Insert Sizes
INSERT IGNORE INTO tamanio (nombre, descripcion, estado, created_at) VALUES
('Pequeño', 'Tamaño pequeño', 0, '2023-01-01'),
('Mediano', 'Tamaño mediano', 0, '2023-01-01'),
('Grande', 'Tamaño grande', 0, '2023-01-01'),
('Extra Grande', 'Tamaño extra grande', 0, '2023-01-01');

-- Insert Categories
INSERT IGNORE INTO categoria (nombre, descripcion, estado, created_at) VALUES
('Electrodomésticos', 'Productos para el hogar', 0, '2023-01-01'),
('Muebles', 'Muebles para el hogar y oficina', 0, '2023-01-01'),
('Tecnología', 'Dispositivos electrónicos', 0, '2023-01-01'),
('Ropa', 'Vestimenta para todas las edades', 0, '2023-01-01');

-- Insert Brands
INSERT IGNORE INTO marca (nombre, descripcion, estado, created_at) VALUES
('Samsung', 'Marca de tecnología y electrodomésticos', 0, '2023-01-01'),
('LG', 'Marca de tecnología y electrodomésticos', 0, '2023-01-01'),
('Nike', 'Marca de ropa y calzado', 0, '2023-01-01'),
('Adidas', 'Marca de ropa y calzado', 0, '2023-01-01');

-- Insert Products
INSERT IGNORE INTO producto (nombre, descripcion, stock, codigo_barra, precio, umbral, estado, id_color, id_tamanio, id_categoria, id_marca, created_at) VALUES
('Televisor LED 50 pulgadas', 'Televisor de alta definición', 20, 123456789, 50000.00, 5, 0, 1, 3, 1, 1, '2023-01-01'),
('Sofá de 3 plazas', 'Sofá cómodo y moderno', 10, 987654321, 30000.00, 2, 0, 2, 2, 2, 2, '2023-01-01'),
('Laptop Gamer', 'Laptop de alto rendimiento para juegos', 15, 456789123, 80000.00, 3, 0, 3, 3, 3, 1, '2023-01-01'),
('Camiseta Deportiva', 'Camiseta transpirable para deportes', 50, 789123456, 2000.00, 10, 0, 4, 1, 4, 3, '2023-01-01');

-- Insert Users
INSERT IGNORE INTO usuario (nombre, contrasenia, rol, estado, created_at) VALUES
('admin', 'admin123', 0, 0, '2023-01-01'),
('user1', 'user123', 1, 0, '2023-01-01'),
('user2', 'user456', 1, 0, '2023-01-01');

-- Insert Orders
INSERT IGNORE INTO pedido (id_usuario, estado, total, fecha_pedido, hora_pedido, created_at) VALUES
(2, 0, 100000.00, '2023-01-10', '10:00', '2023-01-10'),
(3, 0, 50000.00, '2023-01-15', '15:00', '2023-01-15');

-- Insert Order Details
INSERT IGNORE INTO detalle_pedido (id_producto, cantidad, subtotal, estado, created_at) VALUES
(1, 2, 100000.00, 0, '2023-01-10'),
(3, 1, 80000.00, 0, '2023-01-10'),
(2, 1, 30000.00, 0, '2023-01-15'),
(4, 3, 6000.00, 0, '2023-01-15');

-- Insert Stock Movements
INSERT IGNORE INTO movimiento_stock (producto_id, cantidad, motivo, tipo_movimiento, fecha_pedido, hora_pedido) VALUES
(1, 5, 'Ingreso inicial', 'ENTRADA', '2023-01-01', '08:00'),
(2, 3, 'Venta', 'SALIDA', '2023-01-10', '10:30'),
(3, 10, 'Reabastecimiento', 'ENTRADA', '2023-01-20', '14:00');

-- Insert Images
INSERT IGNORE INTO imagen (id_producto, imagen_base64) VALUES
(1, 'base64string1'),
(2, 'base64string2'),
(3, 'base64string3'),
(4, 'base64string4');
