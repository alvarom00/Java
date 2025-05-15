-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: manterola
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `detalle_factura`
--

DROP TABLE IF EXISTS `detalle_factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_factura` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int DEFAULT NULL,
  `precio_unitario` decimal(38,2) DEFAULT NULL,
  `factura_id` bigint DEFAULT NULL,
  `producto_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf67v8cd1i3pidi2rk3iu2jvhc` (`factura_id`),
  KEY `FKt42rg2ha5kdrb6t1v35dwn27x` (`producto_id`),
  CONSTRAINT `FKf67v8cd1i3pidi2rk3iu2jvhc` FOREIGN KEY (`factura_id`) REFERENCES `facturas` (`id`),
  CONSTRAINT `FKt42rg2ha5kdrb6t1v35dwn27x` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_factura`
--

LOCK TABLES `detalle_factura` WRITE;
/*!40000 ALTER TABLE `detalle_factura` DISABLE KEYS */;
INSERT INTO `detalle_factura` VALUES (1,6,50.00,6,1),(2,3,50.00,7,1),(3,1,20.00,7,4),(4,2,10.00,7,5),(5,3,50.00,8,1),(6,3,50.00,9,1),(7,1,50.00,10,1);
/*!40000 ALTER TABLE `detalle_factura` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-15  4:31:57
