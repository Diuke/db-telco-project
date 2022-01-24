-- MySQL dump 10.13  Distrib 8.0.27, for macos12.0 (arm64)
--
-- Host: 2.tcp.ngrok.io    Database: telco_app_db
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `MvProduct`
--

DROP TABLE IF EXISTS `MvProduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MvProduct` (
  `product_id` int DEFAULT NULL,
  `product_name` varchar(80) DEFAULT NULL,
  `total_sales` float DEFAULT '0',
  KEY `MvProduct_total_sales_index` (`total_sales` DESC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MvProduct`
--

LOCK TABLES `MvProduct` WRITE;
/*!40000 ALTER TABLE `MvProduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `MvProduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MvSales`
--

DROP TABLE IF EXISTS `MvSales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MvSales` (
  `package_id` int DEFAULT NULL,
  `sales_with` float DEFAULT '0',
  `sales_without` float DEFAULT '0',
  `total_puchases` int DEFAULT '0',
  `avg_optional` float DEFAULT '0',
  `name` varchar(80) DEFAULT NULL,
  KEY `sales_package_package_id_index` (`package_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MvSales`
--

LOCK TABLES `MvSales` WRITE;
/*!40000 ALTER TABLE `MvSales` DISABLE KEYS */;
/*!40000 ALTER TABLE `MvSales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MvSalesPeriod`
--

DROP TABLE IF EXISTS `MvSalesPeriod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MvSalesPeriod` (
  `package_id` int NOT NULL,
  `period_id` int NOT NULL,
  `total_puchases` int DEFAULT '0',
  `months` int DEFAULT NULL,
  `name` varchar(80) DEFAULT NULL,
  KEY `MvSalesPeriod_package_id_period_id_index` (`package_id`,`period_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MvSalesPeriod`
--

LOCK TABLES `MvSalesPeriod` WRITE;
/*!40000 ALTER TABLE `MvSalesPeriod` DISABLE KEYS */;
/*!40000 ALTER TABLE `MvSalesPeriod` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Order`
--

LOCK TABLES `Order` WRITE;
/*!40000 ALTER TABLE `Order` DISABLE KEYS */;
INSERT INTO `Order` VALUES (34,2,2,2,'2022-01-09 23:51:25',1943.76,'2022-01-08 00:00:01',1),(35,2,1,1,'2022-01-19 16:36:51',599.88,'2022-01-28 00:00:01',1),(36,2,4,1,'2022-01-22 00:00:00',264,'2022-01-29 00:00:00',1),(37,6,1,2,'2022-01-22 00:00:00',1127.76,'2022-01-29 00:00:00',1),(38,6,1,2,'2022-01-22 00:00:00',1127.76,'2022-01-29 00:00:00',1),(39,2,2,1,'2022-01-22 12:22:14',744,'2022-01-29 00:00:00',1),(40,4,1,2,'2022-01-23 13:50:49',1127.76,'2022-01-06 00:00:00',1),(41,4,1,3,'2022-01-23 14:10:43',1727.28,'2022-01-15 00:00:00',0),(42,4,1,3,'2022-01-23 15:01:25',1727.28,'2022-01-15 00:00:00',0),(43,4,1,3,'2022-01-23 15:05:01',1727.28,'2022-01-15 00:00:00',1),(44,4,1,3,'2022-01-23 15:08:29',1727.28,'2022-01-15 00:00:00',0),(45,4,1,3,'2022-01-23 15:09:40',1727.28,'2022-01-15 00:00:00',1),(46,4,1,3,'2022-01-23 15:10:21',1727.28,'2022-01-15 00:00:00',1),(47,4,1,3,'2022-01-23 15:11:15',1727.28,'2022-01-15 00:00:00',1),(48,4,1,3,'2022-01-23 15:12:28',1727.28,'2022-01-15 00:00:00',1),(49,4,1,3,'2022-01-23 15:12:36',1727.28,'2022-01-15 00:00:00',0);
/*!40000 ALTER TABLE `Order` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `OrderTrigger` AFTER UPDATE ON `Order` FOR EACH ROW begin
    -- VARS AND DECLARATIONS
    declare name varchar(80);
    declare value float;
    declare iden integer;
    declare finished integer default 0;

    declare products cursor for
        (select p.id, p.name, p.value from telco_app_db.Product p, OrderProduct Op
        where p.id = Op.product_id and Op.order_id = NEW.id);

    declare continue handler
        for not found set finished = 1;

    set @user_id := (select User.user_id from telco_app_db.User where user_id = OLD.user_id);
    set @user_username := (select User.username from telco_app_db.User where user_id = OLD.user_id);

    -- Billing
    if NEW.status = 1 and OLD.status != 1 then
        insert into telco_app_db.Schedule(order_id)
            values (NEW.id);
    elseif NEW.status = 2 and OLD.status != 2 then
        update telco_app_db.User
            set insolvent = true
        where @user_id = NEW.user_id;
        if telco_app_db.User.failed_payment = 2 then
            update telco_app_db.User
                set User.failed_payment = 0
            where @user_id = NEW.user_id;
            insert into telco_app_db.AudTable(user_id, email, ammount, last_rejection)
                values(@user_id, @user_username, new.total, new.purchase_date);
        else
            update telco_app_db.User
                set User.failed_payment = User.failed_payment + 1
            where @user_id = NEW.user_id;
        end if;
    end if;

    if NEW.status = 1 and OLD.status != 1 then
        set @SalesWith := (NEW.total);
        set @Period := (select p.months from telco_app_db.Period p
            where p.id = NEW.period_id);
        set @PackageSales := (
            select sum(value * @Period)
            from Product p, OrderProduct op
            where p.id = op.product_id and op.order_id = NEW.id);
        set @Products := (
            select count(*)
            from Product p, OrderProduct op
            where p.id = op.product_id and op.order_id = NEW.id);
        set @MvSales := (select count(*) from telco_app_db.MvSales
            where `Order`.package_id = NEW.package_id);
        set @Package := (select name from telco_app_db.TelcoPackage
            where TelcoPackage.id = NEW.package_id);
        set @MvSalesPeriod := (select count(*) from telco_app_db.MvSalesPeriod
            where MvSalesPeriod.period_id = NEW.period_id and MvSalesPeriod.package_id = NEW.package_id);
        -- Init proc

        -- Sales MV
        if @MvSales = 0 then
            insert into telco_app_db.MvSales(package_id, sales_with, sales_without, total_puchases, avg_optional, name)
            values (NEW.package_id, @SalesWith, @SalesWith - @PackageSales, 1, @Products, @Package);
        else
            update telco_app_db.MvSales
            set
                avg_optional = (total_puchases * avg_optional + @Products / (total_puchases + 1)),
                total_puchases = total_puchases + 1,
                sales_without = sales_without + @SalesWith - @PackageSales,
                sales_with = sales_with + @SalesWith
            where package_id = NEW.package_id;
        end if;

        -- Optional products MV
        open products;
        getProducts: LOOP
            fetch products into iden, name, value;
            set @MvProduct := (select count(*) from telco_app_db.OrderProduct Op
                where Op.product_id = iden);
            if @MvProduct = 0 then
                insert into MvProduct (product_id, product_name, total_sales)
                    values (iden, name, value * @Period);
            else
                update telco_app_db.MvProduct
                set
                    total_sales = total_sales + value * @Period
                where product_id = iden;
            end if;
        end loop getProducts;
        close products;

        -- Products Period MV
        if @MvSalesPeriod = 0 then
            insert into telco_app_db.MvSalesPeriod(package_id, period_id, months, total_puchases)
            values (NEW.package_id, NEW.period_id, @Period, 1);
        else
            update telco_app_db.MvSalesPeriod
            set
               total_puchases = total_puchases + 1
            where package_id = NEW.package_id and period_id = NEW.period_id;
        end if;
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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
INSERT INTO `OrderProduct` VALUES (34,1),(35,1),(36,1),(37,1),(38,1),(39,1),(40,1),(41,1),(42,1),(43,1),(44,1),(45,1),(46,1),(47,1),(48,1),(49,1),(50,1),(51,1),(57,1),(58,1),(59,1),(34,2),(35,2),(37,2),(38,2),(40,2),(41,2),(42,2),(43,2),(44,2),(45,2),(46,2),(47,2),(48,2),(49,2),(51,2),(34,3),(41,3),(42,3),(43,3),(44,3),(45,3),(46,3),(47,3),(48,3),(49,3),(50,3),(51,3),(57,3),(58,3),(59,3);
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
INSERT INTO `PackagePeriod` VALUES (1,1,12.99),(1,2,9.99),(1,3,6.99),(2,1,50),(2,2,40),(3,1,9),(4,1,10),(4,2,9),(4,3,8),(10,1,30),(10,2,20),(10,3,15);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Product`
--

LOCK TABLES `Product` WRITE;
/*!40000 ALTER TABLE `Product` DISABLE KEYS */;
INSERT INTO `Product` VALUES (1,'STAR Channel',12),(2,'Serie A',25),(3,'SuperInternetProtection',3.99),(4,'Champions League',10.99);
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
INSERT INTO `ProductPackage` VALUES (1,1),(2,1),(3,1),(1,2),(2,2),(3,2),(1,3),(3,3),(1,4),(1,10),(2,10),(3,10),(4,10);
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
  `activation` date DEFAULT NULL,
  `deactivation` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Schedule_id_uindex` (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `Order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Schedule`
--

LOCK TABLES `Schedule` WRITE;
/*!40000 ALTER TABLE `Schedule` DISABLE KEYS */;
INSERT INTO `Schedule` VALUES (28,34,NULL,NULL),(29,35,NULL,NULL),(30,36,NULL,NULL),(31,37,NULL,NULL),(32,38,NULL,NULL),(33,39,NULL,NULL),(34,40,NULL,NULL);
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
INSERT INTO `ServicePackage` VALUES (2,1),(10,1),(2,2),(3,2),(10,2),(2,3),(4,3),(10,3),(1,4),(2,4),(3,4),(4,4),(10,4);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TelcoPackage`
--

LOCK TABLES `TelcoPackage` WRITE;
/*!40000 ALTER TABLE `TelcoPackage` DISABLE KEYS */;
INSERT INTO `TelcoPackage` VALUES (1,'Super Internet'),(2,'All-in-One'),(3,'Internet+Phone'),(4,'Navigation Freak'),(10,'Ultra Package');
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
  `insolvent` tinyint DEFAULT '0',
  `failed_payment` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `password_UNIQUE` (`password`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (2,'user1pass','user1',1,0,0),(3,'user2pass','user2',1,0,0),(4,'user3pass','user3',1,0,1),(5,'user4pass','user4',1,0,0),(6,'user5pass','user5',1,0,0),(7,'user8pass','user8',1,0,2),(8,'admin','admin',2,0,0);
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

-- Dump completed on 2022-01-24 14:47:40
