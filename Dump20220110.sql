-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: localhost    Database: telco_app_db
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `Attribute`
--

DROP TABLE IF EXISTS `Attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Attribute` (
  `id` int NOT NULL AUTO_INCREMENT,
  `service_id` int NOT NULL,
  `quantity` float NOT NULL,
  `extra_value` float DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_attribute_service_idx` (`service_id`),
  CONSTRAINT `fk_attribute_service` FOREIGN KEY (`service_id`) REFERENCES `Service` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Attribute`
--

LOCK TABLES `Attribute` WRITE;
/*!40000 ALTER TABLE `Attribute` DISABLE KEYS */;
INSERT INTO `Attribute` VALUES (1,1,300,0.05,'Minutes'),(2,1,100,0.01,'SMS'),(3,3,100,10,'GB'),(4,4,1000,1,'GB');
/*!40000 ALTER TABLE `Attribute` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AudTable`
--

DROP TABLE IF EXISTS `AudTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `AudTable` (
  `email` varchar(256) NOT NULL,
  `ammount` float NOT NULL,
  `last_rejection` datetime NOT NULL,
  `user_id` int NOT NULL,
  `id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AudTable`
--

LOCK TABLES `AudTable` WRITE;
/*!40000 ALTER TABLE `AudTable` DISABLE KEYS */;
/*!40000 ALTER TABLE `AudTable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Order`
--

DROP TABLE IF EXISTS `Order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `package_id` int NOT NULL,
  `period_id` int NOT NULL,
  `purchase_date` datetime NOT NULL,
  `total` float NOT NULL,
  `suscription_start_date` datetime NOT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_order_user_idx` (`user_id`),
  KEY `fk_order_package_idx` (`package_id`),
  KEY `fk_order_period_idx` (`period_id`),
  CONSTRAINT `fk_order_package` FOREIGN KEY (`package_id`) REFERENCES `TelcoPackage` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_order_period` FOREIGN KEY (`period_id`) REFERENCES `Period` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Order`
--

LOCK TABLES `Order` WRITE;
/*!40000 ALTER TABLE `Order` DISABLE KEYS */;
INSERT INTO `Order` VALUES (34,2,2,2,'2022-01-09 23:51:25',1943.76,'2022-01-08 00:00:01',1);
/*!40000 ALTER TABLE `Order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderProduct`
--

DROP TABLE IF EXISTS `OrderProduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderProduct` (
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`order_id`,`product_id`),
  KEY `product_id_order` (`product_id`),
  CONSTRAINT `oerder_id_product` FOREIGN KEY (`order_id`) REFERENCES `Order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `product_id_order` FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderProduct`
--

LOCK TABLES `OrderProduct` WRITE;
/*!40000 ALTER TABLE `OrderProduct` DISABLE KEYS */;
INSERT INTO `OrderProduct` VALUES (34,1),(34,2),(34,3);
/*!40000 ALTER TABLE `OrderProduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PackagePeriod`
--

DROP TABLE IF EXISTS `PackagePeriod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PackagePeriod` (
  `package_id` int NOT NULL,
  `period_id` int NOT NULL,
  `value` float NOT NULL,
  PRIMARY KEY (`package_id`,`period_id`),
  KEY `period_id_idx` (`period_id`),
  CONSTRAINT `package_id` FOREIGN KEY (`package_id`) REFERENCES `TelcoPackage` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `period_id` FOREIGN KEY (`period_id`) REFERENCES `Period` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PackagePeriod`
--

LOCK TABLES `PackagePeriod` WRITE;
/*!40000 ALTER TABLE `PackagePeriod` DISABLE KEYS */;
INSERT INTO `PackagePeriod` VALUES (1,1,12.99),(1,2,9.99),(1,3,6.99),(2,1,50),(2,2,40),(3,1,9),(4,1,10),(4,2,9),(4,3,8);
/*!40000 ALTER TABLE `PackagePeriod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Period`
--

DROP TABLE IF EXISTS `Period`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Period` (
  `id` int NOT NULL AUTO_INCREMENT,
  `months` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `period_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Period`
--

LOCK TABLES `Period` WRITE;
/*!40000 ALTER TABLE `Period` DISABLE KEYS */;
INSERT INTO `Period` VALUES (1,12),(2,24),(3,36);
/*!40000 ALTER TABLE `Period` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Product`
--

DROP TABLE IF EXISTS `Product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `value` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Product`
--

LOCK TABLES `Product` WRITE;
/*!40000 ALTER TABLE `Product` DISABLE KEYS */;
INSERT INTO `Product` VALUES (1,'STAR Channel',12),(2,'Serie A',25),(3,'SuperInternetProtection',3.99);
/*!40000 ALTER TABLE `Product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProductPackage`
--

DROP TABLE IF EXISTS `ProductPackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ProductPackage` (
  `product_id` int NOT NULL,
  `package_id` int NOT NULL,
  PRIMARY KEY (`product_id`,`package_id`),
  KEY `package_id_product` (`package_id`),
  CONSTRAINT `package_id_product` FOREIGN KEY (`package_id`) REFERENCES `TelcoPackage` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `product_id` FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProductPackage`
--

LOCK TABLES `ProductPackage` WRITE;
/*!40000 ALTER TABLE `ProductPackage` DISABLE KEYS */;
INSERT INTO `ProductPackage` VALUES (1,1),(2,1),(3,1),(1,2),(2,2),(3,2),(1,3),(3,3),(1,4);
/*!40000 ALTER TABLE `ProductPackage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Schedule`
--

DROP TABLE IF EXISTS `Schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Schedule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Schedule_id_uindex` (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `Order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Schedule`
--

LOCK TABLES `Schedule` WRITE;
/*!40000 ALTER TABLE `Schedule` DISABLE KEYS */;
INSERT INTO `Schedule` VALUES (28,34);
/*!40000 ALTER TABLE `Schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Service`
--

DROP TABLE IF EXISTS `Service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Service` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Service`
--

LOCK TABLES `Service` WRITE;
/*!40000 ALTER TABLE `Service` DISABLE KEYS */;
INSERT INTO `Service` VALUES (1,'Mobile Phone'),(2,'Fixed Phone'),(3,'Mobile Internet'),(4,'Fixed Internet');
/*!40000 ALTER TABLE `Service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServicePackage`
--

DROP TABLE IF EXISTS `ServicePackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ServicePackage` (
  `package_id` int NOT NULL,
  `service_id` int NOT NULL,
  PRIMARY KEY (`package_id`,`service_id`),
  KEY `fk_service_idx` (`service_id`),
  CONSTRAINT `fk_package` FOREIGN KEY (`package_id`) REFERENCES `TelcoPackage` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_service` FOREIGN KEY (`service_id`) REFERENCES `Service` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServicePackage`
--

LOCK TABLES `ServicePackage` WRITE;
/*!40000 ALTER TABLE `ServicePackage` DISABLE KEYS */;
INSERT INTO `ServicePackage` VALUES (2,1),(2,2),(3,2),(2,3),(4,3),(1,4),(2,4),(3,4),(4,4);
/*!40000 ALTER TABLE `ServicePackage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TelcoPackage`
--

DROP TABLE IF EXISTS `TelcoPackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TelcoPackage` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `package_id_uindex` (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TelcoPackage`
--

LOCK TABLES `TelcoPackage` WRITE;
/*!40000 ALTER TABLE `TelcoPackage` DISABLE KEYS */;
INSERT INTO `TelcoPackage` VALUES (1,'Super Internet'),(2,'All-in-One'),(3,'Internet+Phone'),(4,'Navigation Freak');
/*!40000 ALTER TABLE `TelcoPackage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `role` int NOT NULL,
  `insolvent` tinyint DEFAULT NULL,
  `failed_payment` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `password_UNIQUE` (`password`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (2,'user1pass','user1',1,0,0);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-10 13:03:08
