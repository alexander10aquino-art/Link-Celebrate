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
  `estado_pago` varchar(20) DEFAULT NULL,
  `fecha_creacion` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`invitacion_id`),
  UNIQUE KEY `titulo_evento` (`titulo_evento`),
  UNIQUE KEY `slug_url` (`slug_url`),
  KEY `usuario_id` (`usuario_id`),
  KEY `plantilla_id` (`plantilla_id`),
  CONSTRAINT `invitaciones_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`usuario_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invitaciones`
--

LOCK TABLES `invitaciones` WRITE;
/*!40000 ALTER TABLE `invitaciones` DISABLE KEYS */;
INSERT INTO `invitaciones` VALUES (1,2,3,'Mis 15 Años - Valerie','vip','2027-02-14 19:00:00','xv-valerie','completado','2026-08-19 14:06:54'),(2,3,1,'BODA JEREMY ABI','vip','2027-02-10 06:00:00','https://plantilla1xvlinkandcelebrate.netlify.app/','Pagado','2026-08-19 14:14:16'),(4,3,1,' JEREMY Y ABI','premium','2027-02-10 06:00:00','evento-1787162334629','Pendiente','2026-08-19 17:58:54'),(5,3,1,'Jeremy','premium','2027-02-24 06:00:00','evento-1787162465356','completado','2026-08-19 18:01:05'),(12,3,18,'BODA VIVI Y LEO','premium','2027-02-10 06:00:00','evento-1787624411026','completado','2026-08-25 02:20:11'),(13,7,6,'15 abi','basico','2026-08-23 06:00:00','evento-1787629982993','completado','2026-08-25 03:53:03'),(14,8,6,'XV ABYY','basico','2027-02-10 06:00:00','https://plantilla1xvlinkandcelebrate.netlify.app','Pagado','2026-08-25 04:23:22'),(15,9,3,'ALISON Y ALEX','vip','2027-02-10 06:00:00','https://plantilla1viplinkandcelebrate.netlify.app/','Pagado','2026-09-04 05:24:16'),(16,10,3,'BODA JEREMY Y ABIGAIL','vip','2027-02-10 06:00:00','evento-1788645881875','pendiente','2026-09-05 22:04:41');
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
  `acompanantes` int DEFAULT '0',
  `token_qr` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`invitado_id`),
  UNIQUE KEY `token_qr` (`token_qr`),
  KEY `invitacion_id` (`invitacion_id`),
  CONSTRAINT `invitados_ibfk_1` FOREIGN KEY (`invitacion_id`) REFERENCES `invitaciones` (`invitacion_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invitados`
--

LOCK TABLES `invitados` WRITE;
/*!40000 ALTER TABLE `invitados` DISABLE KEYS */;
INSERT INTO `invitados` VALUES (1,1,'Gabriel Calderón','+50250501122','confirmado','¡Ahí estaré sin falta!','2026-08-19 14:06:54',0,NULL),(2,1,'Jeremy Aquino','+50240403344','confirmado','Menú vegetariano, por favor.','2026-08-19 14:06:54',0,NULL),(3,1,'Gustavo Aquino','+50230305566','cancelado','Lo siento, no puedo acudir.','2026-08-19 14:06:54',0,NULL),(4,1,'abigail corado','+50220207788','pendiente','Confirmamos en la semana.','2026-08-19 14:06:54',0,NULL),(5,2,'JEREM','5464646','confirmado','SSS','2026-08-19 16:31:41',0,NULL),(9,2,'ALISON CORADOOO','36095150','pendiente',NULL,'2026-08-19 19:06:23',2,NULL),(10,13,'Jeremy ','36095150','pendiente',NULL,'2026-08-25 03:54:27',2,NULL),(11,14,'ALEX AQUINO','45321123','pendiente',NULL,'2026-08-25 04:24:05',1,NULL),(12,2,'RODRIGO','45321123','confirmado',NULL,'2026-08-27 03:42:33',3,NULL),(16,2,'Jeremy alexander','30092675','confirmado',NULL,'2026-09-04 00:47:53',2,NULL),(17,2,'rodrigo muralles','45321123','pendiente',NULL,'2026-09-04 03:14:04',2,'66dd083f-a741-4cd4-aba7-ca4a4df81519'),(18,15,'ALEX AQUINO','30092675','confirmado',NULL,'2026-09-04 05:50:23',2,'9ceb7c60-987b-4596-a0e6-1dddc19d61a3'),(19,16,'ANGELA OCHOA ','30092675','pendiente',NULL,'2026-09-05 22:05:19',2,'74b8fb2f-b861-4b10-ac3a-5e6b8ea05f22');
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
INSERT INTO `pagos` VALUES (1,1,349.99,'paypal','PAYID-LMN456789OP','2026-08-19 14:06:54');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plantillas_catalogo`
--

LOCK TABLES `plantillas_catalogo` WRITE;
/*!40000 ALTER TABLE `plantillas_catalogo` DISABLE KEYS */;
INSERT INTO `plantillas_catalogo` VALUES (1,'Elegancia Clásica','boda','https://images.unsplash.com/photo-1519741497674-611481863552?auto=format&fit=crop&q=80&w=400',0),(2,'Neon Night Party','cumpleanos','https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?auto=format&fit=crop&q=80&w=400',0),(3,'Mis 15 Rosas','quinceanos','https://images.unsplash.com/photo-1549417229-aa67d3263c09?auto=format&fit=crop&q=80&w=400',1),(4,'Minimalist Golden','boda','https://images.unsplash.com/photo-1511285560929-80b456fea0bc?auto=format&fit=crop&q=80&w=400',1),(5,'Graduation Day','graduacion','https://images.unsplash.com/photo-1523050854058-8df90110c9f1?auto=format&fit=crop&q=80&w=400',0),(6,'','quinceanos','',0),(7,'','quinceanos','',0),(8,'','quinceanos','',0),(9,'','quinceanos','',0),(10,'','quinceanos','',0);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Jeremy Alexander','jeremy@email.com','$2a$12$EjemploHashPasswordFuerte','+50255551234','2026-08-19 14:06:54'),(2,'Valerie Calderón','valerie@email.com','$2a$12$EjemploHashPasswordFuerte2','+50244445678','2026-08-19 14:06:54'),(3,'Jeremy Alexander Aquino Ochoa','alexander10aquino@gmail.com','$2a$10$YRyTLV0B/PCrpB0dM1FcWuqYn0uW7/B6nMtveBFZ1NKEIHm2znb6C','46464646','2026-08-19 14:13:26'),(6,'Rodrigo Muralles','rodrigodavidsosamuralle@gmail.com','$2a$10$5TNwWzrX8GeUTkkVcwSm6.kweA7lDTh.hivBVBM4x7vP3TkVEVmvm','45321123','2026-08-20 02:05:54'),(7,'Alison corado ','abigailcoradosarceno@gmail.com','$2a$10$HZJbFcmlUSb2mwLLKH3jHup9L.9/FAcXbulk58f0K7ro9XV4LUqAe','36095150','2026-08-25 03:44:31'),(8,'jose alexander ramirez chep ','alexander100aquino@gmail.com','$2a$10$hgBhVL.DNVhyR71GrsQx9.nmEArJyXt9w3BJ9DgSDxhFLoEzwK7M2','46098451','2026-08-25 04:15:54'),(9,'JEREMY ALEXANDER','jeremy10aquino@gmail.com','$2a$10$JyLQJa.oxMI0itePd2Q.6OGjawhYHzHGb/GQR21QeiMdmgxqWPxRO','46098451','2026-09-04 05:21:57'),(10,'alexander aquino','alexander20aquino@gmail.com','$2a$10$fJsOJTa/Ow/t8UD6G6yUwu81Lh6hEGMaGMDiIG822aFdTJt08FB06','30092675','2026-09-05 21:40:04');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-05 17:53:00
