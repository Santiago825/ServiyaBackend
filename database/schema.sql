-- ============================================================================
-- ServiYa - esquema MySQL 8 RECONSTRUIDO a partir de las entidades JPA.
-- Generado por Hibernate (ddl-auto=update) sobre una base vacia y volcado con
-- mysqldump --no-data, es decir: es exactamente lo que crean las entidades.
-- Uso:  mysql -u root -p -e 'CREATE DATABASE serviya CHARACTER SET utf8mb4'
--       mysql -u root -p serviya < database/schema.sql
-- En produccion (ddl-auto=validate) la base debe existir con este esquema.
-- ============================================================================
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS `contrato` (
  `id_contrato` bigint NOT NULL AUTO_INCREMENT,
  `arch_contrato` longblob,
  `descripcion` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `nombre_archivo` varchar(255) DEFAULT NULL,
  `precio` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `tipo_archivo` varchar(255) DEFAULT NULL,
  `id_colaborador` bigint DEFAULT NULL,
  `id_contratante` bigint DEFAULT NULL,
  PRIMARY KEY (`id_contrato`),
  KEY `FKf5mvxp1oyoar4xihcxsxnj690` (`id_colaborador`),
  KEY `FK4dacjyjwemcqihcjrmq7uxt06` (`id_contratante`),
  CONSTRAINT `FK4dacjyjwemcqihcjrmq7uxt06` FOREIGN KEY (`id_contratante`) REFERENCES `persona` (`id_persona`),
  CONSTRAINT `FKf5mvxp1oyoar4xihcxsxnj690` FOREIGN KEY (`id_colaborador`) REFERENCES `persona` (`id_persona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE IF NOT EXISTS `departamento` (
  `id_departamento` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_departamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE IF NOT EXISTS `mensaje` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `message` varchar(1000) NOT NULL,
  `receiver_name` varchar(50) NOT NULL,
  `sender_name` varchar(50) NOT NULL,
  `timestamp` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_mensaje_emisor_receptor_id` (`sender_name`,`receiver_name`,`id`),
  KEY `idx_mensaje_receptor_emisor_id` (`receiver_name`,`sender_name`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE IF NOT EXISTS `municipio` (
  `id_municipio` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `id_departamento` bigint DEFAULT NULL,
  PRIMARY KEY (`id_municipio`),
  KEY `FKe1way3pa23l5j480h48x5tp65` (`id_departamento`),
  CONSTRAINT `FKe1way3pa23l5j480h48x5tp65` FOREIGN KEY (`id_departamento`) REFERENCES `departamento` (`id_departamento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE IF NOT EXISTS `persona` (
  `id_persona` bigint NOT NULL AUTO_INCREMENT,
  `apellido` varchar(255) NOT NULL,
  `cv` longblob,
  `descripcion` varchar(255) DEFAULT NULL,
  `foto` longblob,
  `nombre` varchar(255) NOT NULL,
  `numero_documento` bigint DEFAULT NULL,
  `telefono` varchar(255) NOT NULL,
  `id_documento` bigint NOT NULL,
  `id_municipio` bigint NOT NULL,
  `id_servicio` bigint DEFAULT NULL,
  PRIMARY KEY (`id_persona`),
  KEY `FKate2d89aq3ptjjvga0a6w2h9n` (`id_documento`),
  KEY `FKntcv6l5xkda2d4awvhqnuphrg` (`id_municipio`),
  KEY `FKh5mkyjrvxyrb5vvfrt7g6s2j8` (`id_servicio`),
  CONSTRAINT `FKate2d89aq3ptjjvga0a6w2h9n` FOREIGN KEY (`id_documento`) REFERENCES `tipo_documento` (`id_documento`),
  CONSTRAINT `FKh5mkyjrvxyrb5vvfrt7g6s2j8` FOREIGN KEY (`id_servicio`) REFERENCES `servicio` (`id_servicio`),
  CONSTRAINT `FKntcv6l5xkda2d4awvhqnuphrg` FOREIGN KEY (`id_municipio`) REFERENCES `municipio` (`id_municipio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE IF NOT EXISTS `resea` (
  `id_resea` bigint NOT NULL AUTO_INCREMENT,
  `comentario` varchar(255) DEFAULT NULL,
  `puntuacion` int NOT NULL,
  `id_colaborador` bigint DEFAULT NULL,
  `id_contratante` bigint DEFAULT NULL,
  `id_contrato` bigint DEFAULT NULL,
  PRIMARY KEY (`id_resea`),
  KEY `FK39mqmjd4a6t0j9nfp7w9fj4js` (`id_colaborador`),
  KEY `FK400hol7fa8a9c0rb43m31ysxv` (`id_contratante`),
  KEY `FK4g4p7tcs0herp2ahx3fm6ir8u` (`id_contrato`),
  CONSTRAINT `FK39mqmjd4a6t0j9nfp7w9fj4js` FOREIGN KEY (`id_colaborador`) REFERENCES `persona` (`id_persona`),
  CONSTRAINT `FK400hol7fa8a9c0rb43m31ysxv` FOREIGN KEY (`id_contratante`) REFERENCES `persona` (`id_persona`),
  CONSTRAINT `FK4g4p7tcs0herp2ahx3fm6ir8u` FOREIGN KEY (`id_contrato`) REFERENCES `contrato` (`id_contrato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE IF NOT EXISTS `seguimiento` (
  `id_seguimiento` bigint NOT NULL AUTO_INCREMENT,
  `id_colaborador` bigint NOT NULL,
  `id_contratante` bigint NOT NULL,
  PRIMARY KEY (`id_seguimiento`),
  KEY `FK2g2dsk0a5ojmv31xv5mahkd5q` (`id_colaborador`),
  KEY `FK29mcruenq8sw9jrn7qyu0kwdd` (`id_contratante`),
  CONSTRAINT `FK29mcruenq8sw9jrn7qyu0kwdd` FOREIGN KEY (`id_contratante`) REFERENCES `persona` (`id_persona`),
  CONSTRAINT `FK2g2dsk0a5ojmv31xv5mahkd5q` FOREIGN KEY (`id_colaborador`) REFERENCES `persona` (`id_persona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE IF NOT EXISTS `servicio` (
  `id_servicio` bigint NOT NULL AUTO_INCREMENT,
  `icono` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `usos` bigint DEFAULT NULL,
  PRIMARY KEY (`id_servicio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE IF NOT EXISTS `tipo_documento` (
  `id_documento` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_documento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE IF NOT EXISTS `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `registro_completo` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','COLABORADOR','CONTRATANTE') DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `id_persona` bigint DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UK863n1y3x0jalatoir4325ehal` (`username`),
  UNIQUE KEY `UK33gathdlc33wn52w45op1r397` (`id_persona`),
  CONSTRAINT `FKagix3q8yqktlyj3yp1sn0mcd9` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
