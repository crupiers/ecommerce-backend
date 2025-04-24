-- Create database
CREATE DATABASE IF NOT EXISTS pa_db;
USE pa_db;

-- Desactivar validación de claves foráneas temporalmente
SET FOREIGN_KEY_CHECKS = 0;

-- Tabla: categoria
DROP TABLE IF EXISTS `categoria`;
CREATE TABLE `categoria` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) NOT NULL,
  `estado` int NOT NULL,
  `nombre` varchar(32) NOT NULL,
  `updated_at` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_categoria_nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabla: color
DROP TABLE IF EXISTS `color`;
CREATE TABLE `color` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) NOT NULL,
  `estado` int NOT NULL,
  `nombre` varchar(32) NOT NULL,
  `updated_at` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_color_nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabla: marca
DROP TABLE IF EXISTS `marca`;
CREATE TABLE `marca` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) NOT NULL,
  `estado` int NOT NULL,
  `nombre` varchar(32) NOT NULL,
  `updated_at` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_marca_nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabla: tamanio
DROP TABLE IF EXISTS `tamanio`;
CREATE TABLE `tamanio` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) NOT NULL,
  `estado` int NOT NULL,
  `nombre` varchar(32) NOT NULL,
  `updated_at` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_tamanio_nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabla: producto
DROP TABLE IF EXISTS `producto`;
CREATE TABLE `producto` (
  `id` int NOT NULL AUTO_INCREMENT,
  `codigo_barra` int NOT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) NOT NULL,
  `estado` int NOT NULL,
  `nombre` varchar(64) NOT NULL,
  `precio` double NOT NULL,
  `stock` int NOT NULL,
  `umbral` int NOT NULL,
  `updated_at` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `id_categoria` int DEFAULT NULL,
  `id_color` int DEFAULT NULL,
  `id_marca` int DEFAULT NULL,
  `id_tamanio` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_producto_nombre` (`nombre`),
  KEY `FK_producto_categoria` (`id_categoria`),
  KEY `FK_producto_color` (`id_color`),
  KEY `FK_producto_marca` (`id_marca`),
  KEY `FK_producto_tamanio` (`id_tamanio`),
  CONSTRAINT `FK_producto_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`),
  CONSTRAINT `FK_producto_color` FOREIGN KEY (`id_color`) REFERENCES `color` (`id`),
  CONSTRAINT `FK_producto_marca` FOREIGN KEY (`id_marca`) REFERENCES `marca` (`id`),
  CONSTRAINT `FK_producto_tamanio` FOREIGN KEY (`id_tamanio`) REFERENCES `tamanio` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabla: imagen
DROP TABLE IF EXISTS `imagen`;
CREATE TABLE `imagen` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_producto` int DEFAULT NULL,
  `imagen_base64` longtext,
  PRIMARY KEY (`id`),
  KEY `FK_imagen_producto` (`id_producto`),
  CONSTRAINT `FK_imagen_producto` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabla: usuario
DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `contrasenia` varchar(255) DEFAULT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `deleted_at` varchar(255) DEFAULT NULL,
  `estado` int DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `rol` tinyint DEFAULT NULL,
  `updated_at` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_usuario_nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabla: pedido
DROP TABLE IF EXISTS `pedido`;
CREATE TABLE `pedido` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` varchar(255) DEFAULT NULL,
  `estado` int NOT NULL,
  `fecha_pedido` varchar(255) DEFAULT NULL,
  `hora_pedido` varchar(255) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `updated_at` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_pedido_usuario` (`id_usuario`),
  CONSTRAINT `FK_pedido_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabla: detalle_pedido
DROP TABLE IF EXISTS `detalle_pedido`;
CREATE TABLE `detalle_pedido` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `created_at` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `deleted_at` varchar(255) DEFAULT NULL,
  `estado` int NOT NULL,
  `subtotal` double NOT NULL,
  `updated_at` varchar(255) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `id_producto` int DEFAULT NULL,
  `id_detalles_pedido` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_detalle_pedido_producto` (`id_producto`),
  KEY `FK_detalle_pedido_pedido` (`id_detalles_pedido`),
  CONSTRAINT `FK_detalle_pedido_producto` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id`),
  CONSTRAINT `FK_detalle_pedido_pedido` FOREIGN KEY (`id_detalles_pedido`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Tabla: movimiento_stock
DROP TABLE IF EXISTS `movimiento_stock`;
CREATE TABLE `movimiento_stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `fecha_pedido` varchar(255) DEFAULT NULL,
  `hora_pedido` varchar(255) DEFAULT NULL,
  `motivo` varchar(255) DEFAULT NULL,
  `tipo_movimiento` varchar(255) DEFAULT NULL,
  `producto_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_movimiento_stock_producto` (`producto_id`),
  CONSTRAINT `FK_movimiento_stock_producto` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Reactivar validación de claves foráneas
SET FOREIGN_KEY_CHECKS = 1;
