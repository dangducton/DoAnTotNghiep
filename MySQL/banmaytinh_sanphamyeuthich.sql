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
-- Table structure for table `sanphamyeuthich`
--

DROP TABLE IF EXISTS `sanphamyeuthich`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sanphamyeuthich` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_sanpham` int NOT NULL,
  `id_nguoidung` int NOT NULL,
  `ngaytao` date NOT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_sanpham` (`id_sanpham`),
  KEY `id_nguoidung` (`id_nguoidung`),
  CONSTRAINT `sanphamyeuthich_ibfk_1` FOREIGN KEY (`id_sanpham`) REFERENCES `sanpham` (`id`),
  CONSTRAINT `sanphamyeuthich_ibfk_2` FOREIGN KEY (`id_nguoidung`) REFERENCES `nguoidung` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sanphamyeuthich`
--

LOCK TABLES `sanphamyeuthich` WRITE;
/*!40000 ALTER TABLE `sanphamyeuthich` DISABLE KEYS */;
INSERT INTO `sanphamyeuthich` VALUES (15,45,12,'2020-07-05',1),(16,46,12,'2020-07-05',1),(17,47,12,'2020-07-05',1),(18,48,12,'2020-07-05',1),(19,49,12,'2020-07-05',1),(20,50,12,'2020-07-05',1),(21,51,12,'2020-07-05',1),(22,52,12,'2020-07-05',1),(23,57,12,'2020-07-06',1),(24,61,12,'2020-07-07',1),(25,53,17,'2020-07-17',1),(26,47,17,'2020-08-13',1);
/*!40000 ALTER TABLE `sanphamyeuthich` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-18 11:21:37
