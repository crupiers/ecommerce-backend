-- Insertar usuario administrador por defecto
INSERT INTO usuario (nombre, contrasenia, rol, fecha_nacimiento, estado, created_at) 
VALUES 
('admin', '$2a$12$PLt18mE3FxgX9Bqp.eaHReiVGf/NbfPAw/NuM3y5dTnPLehTSUafa', 0, '1990-01-01', 0, '01/01/2023, 00:00'),
('user1', '$2a$10$N.zmdr9k7uOCQb.gMeLbUeWbr5qFBKQQ5PZpuUJQ9lrNkFHT8HQcG', 1, '1995-05-15', 0, '01/01/2023, 00:00'),
('user2', '$2a$10$N.zmdr9k7uOCQb.gMeLbUeWbr5qFBKQQ5PZpuUJQ9lrNkFHT8HQcG', 1, '1992-10-20', 0, '01/01/2023, 00:00');
-- Administrador: Usuario: admin, Contraseña: Hola1234_
-- Usuario regular: Usuario: user1 o user2, Contraseña: admin123