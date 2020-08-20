-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: banmaytinh
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `confirmationtoken`
--

DROP TABLE IF EXISTS `confirmationtoken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `confirmationtoken` (
  `token_id` int NOT NULL AUTO_INCREMENT,
  `confirmation_token` varchar(255) DEFAULT NULL,
  `created_date` timestamp NOT NULL,
  `id_nguoidung` int NOT NULL,
  PRIMARY KEY (`token_id`),
  KEY `id_nguoidung` (`id_nguoidung`),
  CONSTRAINT `confirmationtoken_ibfk_1` FOREIGN KEY (`id_nguoidung`) REFERENCES `nguoidung` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confirmationtoken`
--

LOCK TABLES `confirmationtoken` WRITE;
/*!40000 ALTER TABLE `confirmationtoken` DISABLE KEYS */;
INSERT INTO `confirmationtoken` VALUES (1,'915cb329-5864-4733-8200-6aef40395df5','2020-08-02 08:20:33',17),(2,'91447f52-a804-4e24-bfcf-2f82bc8ed834','2020-08-02 08:23:46',17),(3,'c8ffc57b-105c-49e6-a8b8-752665b28803','2020-08-02 08:29:10',17),(4,'0e80c986-3014-4fbc-aab0-89edea2aa5d1','2020-08-02 10:41:25',17),(5,'4caa52d5-5120-4be7-a13e-2b7753524922','2020-08-03 09:02:44',21),(6,'d6aa51ff-501e-48b6-bd76-43a5f33e1805','2020-08-04 04:31:51',17),(7,'eafc5182-fc38-45ab-94cf-efc14d3bca05','2020-08-05 03:17:45',17),(8,'52fa466a-2dda-433d-9bb6-5ce678152971','2020-08-05 03:19:24',17),(9,'dc266440-675e-448d-837d-94ec2de0da89','2020-08-05 03:20:41',17),(10,'6cace6fc-94c8-4944-a60a-d2f84930bcf0','2020-08-05 03:21:28',17),(11,'c1b7a64a-834e-41c2-afe1-5a7ade594fba','2020-08-14 06:07:46',17),(12,'201660f8-220f-4e50-b0e7-e2ca8781e06d','2020-08-14 09:33:21',17);
/*!40000 ALTER TABLE `confirmationtoken` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-18 11:21:36
