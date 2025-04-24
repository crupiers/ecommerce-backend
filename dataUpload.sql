-- Insert Colors
INSERT INTO color (id, nombre) VALUES
(1, 'Rojo'),
(2, 'Azul'),
(3, 'Verde'),
(4, 'Amarillo'),
(5, 'Negro');

-- Insert Sizes
INSERT INTO tamanio (id, nombre) VALUES
(1, 'Pequeño'),
(2, 'Mediano'),
(3, 'Grande'),
(4, 'Extra Grande');

-- Insert Categories
INSERT INTO categoria (id, nombre, descripcion) VALUES
(1, 'Electrodomésticos', 'Productos para el hogar'),
(2, 'Muebles', 'Muebles para el hogar y oficina'),
(3, 'Tecnología', 'Dispositivos electrónicos'),
(4, 'Ropa', 'Vestimenta para todas las edades');

-- Insert Brands
INSERT INTO marca (id, nombre, descripcion) VALUES
(1, 'Samsung', 'Marca de tecnología y electrodomésticos'),
(2, 'LG', 'Marca de tecnología y electrodomésticos'),
(3, 'Nike', 'Marca de ropa y calzado'),
(4, 'Adidas', 'Marca de ropa y calzado');

-- Insert Products
INSERT INTO producto (id, nombre, descripcion, stock, codigo_barra, precio, umbral, estado, idColor, idTamanio, idCategoria, idMarca) VALUES
(1, 'Televisor LED 50 pulgadas', 'Televisor de alta definición', 20, 123456789, 50000.00, 5, 0, 1, 3, 1, 1),
(2, 'Sofá de 3 plazas', 'Sofá cómodo y moderno', 10, 987654321, 30000.00, 2, 0, 2, 2, 2, 2),
(3, 'Laptop Gamer', 'Laptop de alto rendimiento para juegos', 15, 456789123, 80000.00, 3, 0, 3, 3, 3, 1),
(4, 'Camiseta Deportiva', 'Camiseta transpirable para deportes', 50, 789123456, 2000.00, 10, 0, 4, 1, 4, 3),
(5, 'Auriculares Bluetooth', 'Auriculares inalámbricos con cancelación de ruido', 30, 112233445, 15000.00, 5, 0, 5, 1, 3, 1),
(6, 'Refrigerador', 'Refrigerador de doble puerta', 8, 998877665, 60000.00, 3, 0, 1, 3, 1, 2),
(7, 'Zapatos Deportivos', 'Zapatos cómodos para correr', 40, 334455667, 5000.00, 10, 0, 2, 1, 4, 4),
(8, 'Smartphone', 'Teléfono inteligente de última generación', 25, 556677889, 70000.00, 5, 0, 3, 3, 3, 1),
(9, 'Mesa de Comedor', 'Mesa de madera para 6 personas', 12, 778899001, 25000.00, 2, 0, 4, 2, 2, 2),
(10, 'Reloj Inteligente', 'Reloj con monitoreo de actividad física', 35, 990011223, 12000.00, 5, 0, 5, 1, 3, 1),
(11, 'Cámara Fotográfica', 'Cámara profesional con lente intercambiable', 10, 223344556, 90000.00, 3, 0, 1, 3, 3, 1),
(12, 'Licuadora', 'Licuadora de alta potencia', 20, 445566778, 8000.00, 5, 0, 2, 1, 1, 2),
(13, 'Silla de Oficina', 'Silla ergonómica con soporte lumbar', 15, 667788990, 15000.00, 3, 0, 3, 2, 2, 2),
(14, 'Monitor 4K', 'Monitor de alta resolución para trabajo y juegos', 18, 889900112, 40000.00, 5, 0, 4, 3, 3, 1),
(15, 'Zapatillas Casual', 'Zapatillas cómodas para uso diario', 50, 112233445, 3000.00, 10, 0, 5, 1, 4, 4),
(16, 'Tablet', 'Tablet con pantalla táctil de 10 pulgadas', 25, 334455667, 25000.00, 5, 0, 1, 3, 3, 1),
(17, 'Cafetera', 'Cafetera automática con espumador de leche', 10, 556677889, 20000.00, 3, 0, 2, 1, 1, 2),
(18, 'Escritorio', 'Escritorio de madera con cajones', 12, 778899001, 18000.00, 2, 0, 3, 2, 2, 2),
(19, 'Audífonos In-Ear', 'Audífonos compactos con excelente calidad de sonido', 30, 990011223, 8000.00, 5, 0, 4, 1, 3, 1),
(20, 'Bicicleta', 'Bicicleta de montaña con suspensión', 8, 223344556, 45000.00, 3, 0, 5, 3, 4, 4),
(21, 'Cámara de Seguridad', 'Cámara de vigilancia con visión nocturna', 20, 445566778, 15000.00, 5, 0, 1, 3, 3, 1),
(22, 'Colchón', 'Colchón ortopédico tamaño queen', 10, 667788990, 30000.00, 2, 0, 2, 2, 2, 2),
(23, 'Impresora', 'Impresora multifuncional con Wi-Fi', 15, 889900112, 20000.00, 3, 0, 3, 3, 3, 1),
(24, 'Mochila', 'Mochila resistente al agua', 50, 112233445, 5000.00, 10, 0, 4, 1, 4, 4),
(25, 'Microondas', 'Microondas con grill', 25, 334455667, 15000.00, 5, 0, 5, 1, 1, 2),
(26, 'Lámpara de Escritorio', 'Lámpara LED con ajuste de brillo', 20, 556677889, 3000.00, 5, 0, 1, 1, 2, 2),
(27, 'Teclado Mecánico', 'Teclado para gaming con retroiluminación', 18, 778899001, 8000.00, 3, 0, 2, 1, 3, 1),
(28, 'Mouse Inalámbrico', 'Mouse ergonómico con batería recargable', 30, 990011223, 4000.00, 5, 0, 3, 1, 3, 1),
(29, 'Parlante Bluetooth', 'Parlante portátil con sonido envolvente', 25, 223344556, 10000.00, 5, 0, 4, 1, 3, 1),
(30, 'Cámara Deportiva', 'Cámara resistente al agua para deportes extremos', 15, 445566778, 25000.00, 3, 0, 5, 3, 3, 1),
(31, 'Reloj de Pared', 'Reloj decorativo para sala', 20, 667788990, 2000.00, 5, 0, 1, 1, 2, 2),
(32, 'Plancha de Vapor', 'Plancha con control de temperatura', 10, 889900112, 5000.00, 3, 0, 2, 1, 1, 2),
(33, 'Cortinas', 'Cortinas opacas para ventanas', 12, 112233445, 8000.00, 2, 0, 3, 2, 2, 2),
(34, 'Cargador Rápido', 'Cargador USB con carga rápida', 30, 334455667, 3000.00, 5, 0, 4, 1, 3, 1),
(35, 'Batería Externa', 'Power bank de 10000mAh', 25, 556677889, 7000.00, 5, 0, 5, 1, 3, 1),
(36, 'Cámara Instantánea', 'Cámara que imprime fotos al instante', 15, 778899001, 30000.00, 3, 0, 1, 3, 3, 1),
(37, 'Cinturón de Cuero', 'Cinturón elegante para hombre', 50, 990011223, 2000.00, 10, 0, 2, 1, 4, 4),
(38, 'Juego de Ollas', 'Ollas de acero inoxidable', 20, 223344556, 15000.00, 5, 0, 3, 1, 1, 2),
(39, 'Ventilador', 'Ventilador de pedestal con control remoto', 10, 445566778, 10000.00, 3, 0, 4, 1, 1, 2),
(40, 'Cámara Web', 'Cámara para videollamadas', 15, 667788990, 8000.00, 3, 0, 5, 1, 3, 1),
(41, 'Cojines Decorativos', 'Cojines suaves para sofá', 12, 889900112, 5000.00, 2, 0, 1, 2, 2, 2),
(42, 'Juego de Sábanas', 'Sábanas de algodón tamaño king', 30, 112233445, 8000.00, 5, 0, 2, 2, 2, 2),
(43, 'Cámara de Acción', 'Cámara compacta para grabaciones en movimiento', 25, 334455667, 25000.00, 5, 0, 3, 3, 3, 1),
(44, 'Zapatillas Running', 'Zapatillas ligeras para correr', 50, 556677889, 4000.00, 10, 0, 4, 1, 4, 4),
(45, 'Cafetera Italiana', 'Cafetera clásica de aluminio', 20, 778899001, 3000.00, 5, 0, 5, 1, 1, 2),
(46, 'Cámara Profesional', 'Cámara DSLR con lente de 50mm', 10, 990011223, 120000.00, 3, 0, 1, 3, 3, 1),
(47, 'Silla Gamer', 'Silla ergonómica para gaming', 15, 223344556, 25000.00, 3, 0, 2, 2, 2, 2),
(48, 'Lámpara de Pie', 'Lámpara decorativa para sala', 12, 445566778, 15000.00, 2, 0, 3, 2, 2, 2),
(49, 'Cámara de Seguridad Wi-Fi', 'Cámara con conexión remota', 20, 667788990, 20000.00, 5, 0, 4, 3, 3, 1),
(50, 'Auriculares Gaming', 'Auriculares con micrófono para juegos', 30, 889900112, 10000.00, 5, 0, 5, 1, 3, 1);

-- Insert Users
INSERT INTO usuario (id, nombre, contrasenia, rol, fecha_nacimiento, estado, created_at) VALUES
(1, 'admin', 'admin123', 'ADMIN', '1980-01-01', 0, '2023-01-01'),
(2, 'user1', 'user123', 'USER', '1990-05-15', 0, '2023-01-01'),
(3, 'user2', 'user456', 'USER', '1995-08-20', 0, '2023-01-01');

-- Insert Orders
INSERT INTO pedido (id, usuario_id, estado, created_at) VALUES
(1, 2, 'PENDIENTE', '2023-01-10'),
(2, 3, 'COMPLETADO', '2023-01-15');

-- Insert Order Details
INSERT INTO detalle_pedido (id, pedido_id, producto_id, cantidad) VALUES
(1, 1, 1, 2),
(2, 1, 3, 1),
(3, 2, 2, 1),
(4, 2, 4, 3);

-- Insert Stock Movements
INSERT INTO movimiento_stock (id, producto_id, cantidad, motivo, tipo_movimiento, created_at) VALUES
(1, 1, 5, 'Ingreso inicial', 'ENTRADA', '2023-01-01'),
(2, 2, 3, 'Venta', 'SALIDA', '2023-01-10'),
(3, 3, 10, 'Reabastecimiento', 'ENTRADA', '2023-01-20');
