-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: link_and_celebrate
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `detalles_invitacion`
--

DROP TABLE IF EXISTS `detalles_invitacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalles_invitacion` (
  `detalle_id` int NOT NULL AUTO_INCREMENT,
  `invitacion_id` int NOT NULL,
  `musica_url` varchar(250) DEFAULT NULL,
  `ubicacion_waze_url` varchar(255) DEFAULT NULL,
  `ubicacion_maps_url` varchar(255) DEFAULT NULL,
  `frase_bienvenida` text,
  `vestimenta_sugerida` varchar(100) DEFAULT NULL,
  `link_mesa_regalo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`detalle_id`),
  UNIQUE KEY `invitacion_id` (`invitacion_id`),
  CONSTRAINT `detalles_invitacion_ibfk_1` FOREIGN KEY (`invitacion_id`) REFERENCES `invitaciones` (`invitacion_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalles_invitacion`
--

LOCK TABLES `detalles_invitacion` WRITE;
/*!40000 ALTER TABLE `detalles_invitacion` DISABLE KEYS */;
INSERT INTO `detalles_invitacion` VALUES (1,1,'https://spotify.com/track/dancing-queen','https://waze.com/ul/hb5ee99','https://maps.google.com/xv-valerie','Una noche mágica que guardar en el corazón.','Gala / Vestido Largo','Lluvia de sobres.');
/*!40000 ALTER TABLE `detalles_invitacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invitaciones`
--

DROP TABLE IF EXISTS `invitaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invitaciones` (
  `invitacion_id` int NOT NULL AUTO_INCREMENT,
  `usuario_id` int NOT NULL,
  `plantilla_id` int DEFAULT NULL,
  `titulo_evento` varchar(250) NOT NULL,
  `tipo_paquete` enum('basico','premium','vip') NOT NULL,
  `fecha_evento` datetime NOT NULL,
  `slug_url` varchar(100) NOT NULL,
  `estado_pago` enum('pendiente','completado','reembolsado') DEFAULT 'pendiente',
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`invitacion_id`),
  UNIQUE KEY `titulo_evento` (`titulo_evento`),
  UNIQUE KEY `slug_url` (`slug_url`),
  KEY `usuario_id` (`usuario_id`),
  KEY `plantilla_id` (`plantilla_id`),
  CONSTRAINT `invitaciones_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`usuario_id`) ON DELETE CASCADE,
  CONSTRAINT `invitaciones_ibfk_2` FOREIGN KEY (`plantilla_id`) REFERENCES `plantillas_catalogo` (`plantilla_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invitaciones`
--

LOCK TABLES `invitaciones` WRITE;
/*!40000 ALTER TABLE `invitaciones` DISABLE KEYS */;
INSERT INTO `invitaciones` VALUES (1,2,3,'Mis 15 Años - Valerie','vip','2027-02-14 19:00:00','xv-valerie','completado','2026-07-16 04:40:15');
/*!40000 ALTER TABLE `invitaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invitados`
--

DROP TABLE IF EXISTS `invitados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invitados` (
  `invitado_id` int NOT NULL AUTO_INCREMENT,
  `invitacion_id` int NOT NULL,
  `nombre_invitado` varchar(155) NOT NULL,
  `telefono` varchar(25) DEFAULT NULL,
  `asistencia` enum('confirmado','cancelado','pendiente') DEFAULT 'pendiente',
  `comentarios` text,
  `fecha_confirmacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`invitado_id`),
  KEY `invitacion_id` (`invitacion_id`),
  CONSTRAINT `invitados_ibfk_1` FOREIGN KEY (`invitacion_id`) REFERENCES `invitaciones` (`invitacion_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invitados`
--

LOCK TABLES `invitados` WRITE;
/*!40000 ALTER TABLE `invitados` DISABLE KEYS */;
INSERT INTO `invitados` VALUES (1,1,'Gabriel Calderón','+50250501122','confirmado','¡Ahí estaré sin falta!','2026-07-16 04:40:15'),(2,1,'David Quintanilla','+50240403344','confirmado','Menú vegetariano, por favor.','2026-07-16 04:40:15'),(3,1,'Andrés Callejas','+50230305566','cancelado','Lo siento, no puedo acudir.','2026-07-16 04:40:15'),(4,1,'abigail corado','+50220207788','pendiente','Confirmamos en la semana.','2026-07-16 04:40:15');
/*!40000 ALTER TABLE `invitados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pagos`
--

DROP TABLE IF EXISTS `pagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagos` (
  `pago_id` int NOT NULL AUTO_INCREMENT,
  `invitacion_id` int NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `metodo_pago` enum('tarjeta','transferencia','deposito','paypal') NOT NULL,
  `referencia_transaccion` varchar(100) NOT NULL,
  `fecha_pago` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`pago_id`),
  UNIQUE KEY `referencia_transaccion` (`referencia_transaccion`),
  KEY `invitacion_id` (`invitacion_id`),
  CONSTRAINT `pagos_ibfk_1` FOREIGN KEY (`invitacion_id`) REFERENCES `invitaciones` (`invitacion_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagos`
--

LOCK TABLES `pagos` WRITE;
/*!40000 ALTER TABLE `pagos` DISABLE KEYS */;
INSERT INTO `pagos` VALUES (1,1,349.99,'paypal','PAYID-LMN456789OP','2026-07-16 04:40:15');
/*!40000 ALTER TABLE `pagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plantillas_catalogo`
--

DROP TABLE IF EXISTS `plantillas_catalogo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plantillas_catalogo` (
  `plantilla_id` int NOT NULL AUTO_INCREMENT,
  `nombre_diseno` varchar(100) NOT NULL,
  `categoria` enum('quinceanos','boda','cumpleanos','graduacion','otro') NOT NULL,
  `imagen_preview_url` varchar(255) NOT NULL,
  `es_premium_vip` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`plantilla_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plantillas_catalogo`
--

LOCK TABLES `plantillas_catalogo` WRITE;
/*!40000 ALTER TABLE `plantillas_catalogo` DISABLE KEYS */;
INSERT INTO `plantillas_catalogo` VALUES (1,'Elegancia Clásica','boda','https://images.unsplash.com/photo-1519741497674-611481863552?auto=format&fit=crop&q=80&w=400',0),(2,'Neon Night Party','cumpleanos','https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?auto=format&fit=crop&q=80&w=400',0),(3,'Mis 15 Rosas','quinceanos','https://images.unsplash.com/photo-1549417229-aa67d3263c09?auto=format&fit=crop&q=80&w=400',1),(4,'Minimalist Golden','boda','https://images.unsplash.com/photo-1511285560929-80b456fea0bc?auto=format&fit=crop&q=80&w=400',1),(5,'Graduation Day','graduacion','https://images.unsplash.com/photo-1523050854058-8df90110c9f1?auto=format&fit=crop&q=80&w=400',0);
/*!40000 ALTER TABLE `plantillas_catalogo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `usuario_id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `gmail` varchar(100) NOT NULL,
  `contrasena` varchar(100) NOT NULL,
  `telefono` varchar(100) NOT NULL,
  `fecha_registro` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`usuario_id`),
  UNIQUE KEY `gmail` (`gmail`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Jeremy Alexander','jeremy@email.com','$2a$12$EjemploHashPasswordFuerte','+50255551234','2026-07-16 04:40:15'),(2,'Valerie Calderón','valerie@email.com','$2a$12$EjemploHashPasswordFuerte2','+50244445678','2026-07-16 04:40:15');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'link_and_celebrate'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_asistencia_manual` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`JeremyAquino`@`localhost` PROCEDURE `sp_actualizar_asistencia_manual`(
    in p_invitado_id int,
    in p_nueva_asistencia enum('confirmado', 'cancelado', 'pendiente')
)
begin
    update invitados 
    set asistencia = p_nueva_asistencia
    where invitado_id = p_invitado_id;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_actualizar_perfil_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`JeremyAquino`@`localhost` PROCEDURE `sp_actualizar_perfil_usuario`(
    in p_usuario_id int,
    in p_nombre varchar(100),
    in p_telefono varchar(100)
)
begin
    update usuarios 
    set nombre = p_nombre, telefono = p_telefono 
    where usuario_id = p_usuario_id;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_crear_invitacion_con_pago` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`JeremyAquino`@`localhost` PROCEDURE `sp_crear_invitacion_con_pago`(
    in p_usuario_id int,
    in p_plantilla_id int,
    in p_titulo_evento varchar(250),
    in p_tipo_paquete enum('basico', 'premium', 'vip'),
    in p_fecha_evento datetime,
    in p_slug_url varchar(100),
    in p_monto decimal(10, 2),
    in p_metodo_pago enum('tarjeta', 'transferencia', 'deposito', 'paypal'),
    in p_referencia varchar(100)
)
begin
    declare v_invitacion_id int;

    insert into invitaciones (usuario_id, plantilla_id, titulo_evento, tipo_paquete, fecha_evento, slug_url, estado_pago)
    values (p_usuario_id, p_plantilla_id, p_titulo_evento, p_tipo_paquete, p_fecha_evento, p_slug_url, 'completado');
    
    set v_invitacion_id = last_insert_id();
    
    insert into pagos (invitacion_id, monto, metodo_pago, referencia_transaccion)
    values (v_invitacion_id, p_monto, p_metodo_pago, p_referencia);
    

    insert into detalles_invitacion (invitacion_id)
    values (v_invitacion_id);
    
    select v_invitacion_id as nuevo_evento_id;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_eliminar_invitado_vip` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`JeremyAquino`@`localhost` PROCEDURE `sp_eliminar_invitado_vip`(
    in p_invitado_id int
)
begin
    delete from invitados 
    where invitado_id = p_invitado_id;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_guardar_detalles_invitacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`JeremyAquino`@`localhost` PROCEDURE `sp_guardar_detalles_invitacion`(
    in p_invitacion_id int,
    in p_musica_url varchar(250),
    in p_waze_url varchar(255),
    in p_maps_url varchar(255),
    in p_frase text,
    in p_vestimenta varchar(100),
    in p_regalos varchar(255)
)
begin
    insert into detalles_invitacion (invitacion_id, musica_url, ubicacion_waze_url, ubicacion_maps_url, frase_bienvenida, vestimenta_sugerida, link_mesa_regalo)
    values (p_invitacion_id, p_musica_url, p_waze_url, p_maps_url, p_frase, p_vestimenta, p_regalos)
    on duplicate key update
        musica_url = p_musica_url,
        ubicacion_waze_url = p_waze_url,
        ubicacion_maps_url = p_maps_url,
        frase_bienvenida = p_frase,
        vestimenta_sugerida = p_vestimenta,
        link_mesa_regalo = p_regalos;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_catalogo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`JeremyAquino`@`localhost` PROCEDURE `sp_listar_catalogo`()
begin
    select plantilla_id, nombre_diseno, categoria, imagen_preview_url, es_premium_vip 
    from plantillas_catalogo
    order by categoria, nombre_diseno;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_invitaciones_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`JeremyAquino`@`localhost` PROCEDURE `sp_listar_invitaciones_usuario`(
    in p_usuario_id int
)
begin
    select 
        i.invitacion_id,
        i.titulo_evento,
        i.tipo_paquete,
        i.fecha_evento,
        i.slug_url,
        i.estado_pago,
        pc.nombre_diseno,
        pc.imagen_preview_url
    from invitaciones i
    left join plantillas_catalogo pc on i.plantilla_id = pc.plantilla_id
    where i.usuario_id = p_usuario_id
    order by i.fecha_creacion desc;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_listar_invitados_vip` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`JeremyAquino`@`localhost` PROCEDURE `sp_listar_invitados_vip`(
    in p_invitacion_id int
)
begin
    select 
        invitado_id,
        nombre_invitado,
        telefono,
        asistencia,
        comentarios,
        fecha_confirmacion
    from invitados
    where invitacion_id = p_invitacion_id
    order by fecha_confirmacion desc;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_obtener_configuracion_invitacion` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`JeremyAquino`@`localhost` PROCEDURE `sp_obtener_configuracion_invitacion`(
    in p_slug_url varchar(100)
)
begin
    select 
        i.invitacion_id,
        i.titulo_evento,
        i.tipo_paquete,
        i.fecha_evento,
        i.slug_url,
        d.musica_url,
        d.ubicacion_waze_url,
        d.ubicacion_maps_url,
        d.frase_bienvenida, -- Tu columna corregida
        d.vestimenta_sugerida,
        d.link_mesa_regalo   -- Tu columna corregida
    from invitaciones i
    left join detalles_invitacion d on i.invitacion_id = d.invitacion_id
    where i.slug_url = p_slug_url;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_obtener_estadisticas_vip` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`JeremyAquino`@`localhost` PROCEDURE `sp_obtener_estadisticas_vip`(
    in p_invitacion_id int
)
begin
    select 
        count(*) as total_invitados,
        coalesce(sum(case when asistencia = 'confirmado' then 1 else 0 end), 0) as total_confirmados,
        coalesce(sum(case when asistencia = 'cancelado' then 1 else 0 end), 0) as total_cancelados,
        coalesce(sum(case when asistencia = 'pendiente' then 1 else 0 end), 0) as total_pendientes
    from invitados
    where invitacion_id = p_invitacion_id;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_obtener_usuario_por_email` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`JeremyAquino`@`localhost` PROCEDURE `sp_obtener_usuario_por_email`(
    in p_gmail varchar(100)
)
begin
    select usuario_id, nombre, gmail, contrasena, telefono, fecha_registro 
    from usuarios 
    where gmail = p_gmail;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_registrar_rsvp` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`JeremyAquino`@`localhost` PROCEDURE `sp_registrar_rsvp`(
    in p_invitacion_id int,
    in p_nombre_invitado varchar(155),
    in p_telefono varchar(25),
    in p_asistencia enum('confirmado', 'cancelado', 'pendiente'),
    in p_comentarios text
)
begin
    insert into invitados (invitacion_id, nombre_invitado, telefono, asistencia, comentarios)
    values (p_invitacion_id, p_nombre_invitado, p_telefono, p_asistencia, p_comentarios);
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_registrar_usuario` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`JeremyAquino`@`localhost` PROCEDURE `sp_registrar_usuario`(
    in p_nombre varchar(100),
    in p_gmail varchar(100),
    in p_contrasena varchar(100),
    in p_telefono varchar(100)
)
begin
    insert into usuarios (nombre, gmail, contrasena, telefono)
    values (p_nombre, p_gmail, p_contrasena, p_telefono);
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-07-23  7:31:28
