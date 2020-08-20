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
-- Table structure for table `nguoidung`
--

DROP TABLE IF EXISTS `nguoidung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nguoidung` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `hoten` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `dienthoai` varchar(30) DEFAULT NULL,
  `diachi` varchar(100) DEFAULT NULL,
  `gioitinh` int DEFAULT NULL,
  `ngaytao` date NOT NULL,
  `anh` varchar(255) DEFAULT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nguoidung`
--

LOCK TABLES `nguoidung` WRITE;
/*!40000 ALTER TABLE `nguoidung` DISABLE KEYS */;
INSERT INTO `nguoidung` VALUES (12,'hoanganhduc@gmail.com','Hoàng anh đức','$2a$10$d7YndyEbO5NXLyhjLGAW6.4c6j9Z1FIphIaIRYU8edPLn/AVUqmye','0366640630','Đông Ngạc, Bắc Từ Liêm,Hà Nội',NULL,'2020-07-05',NULL,1),(14,'admin@gmail.com','Đặng Đức Tôn','$2a$10$8OBkGwPDiXSnQSVxqzUH8O.14.5OwBY9W/gjjbezR0fJ6gWqln/je','0366640630','Đông Ngạc, Bắc Từ Liêm,Hà Nội',1,'2020-07-05','/upload/anh the 3x4.jpg',1),(15,'dangducton@gmail.com','Yuè Bỉn Rỉn',NULL,'0366640630','Đông Ngạc, Bắc Từ Liêm,Hà Nội',NULL,'2020-07-06','https://graph.facebook.com/1166611910367720/picture',1),(16,'abc1234@gmail.com','Đặng Đức Tôn','$2a$10$NxSE9swWaqrq9VZtrJT4OeXaF2cRloQaAPYBW5CA1SyRcbjhky29.',NULL,NULL,NULL,'2020-07-06',NULL,1),(17,'trangthocton@gmail.com','Lê Thị Huyền Trang','$2a$10$Q4Zbtmz38TE.lZBG1XhxduYRSZV6YkNs1GnRIO61tsttBB6SEJgt2','0366640630','Đông Ngạc, Bắc Từ Liêm,Hà Nội',2,'2020-08-14','/upload/10409146_407232502797437_912248228784820484_n.jpg',1),(20,'supper_admin@gmail.com','Đặng Đức Tôn','$2a$10$7TOy6WR6dEiNKbgW7JEdN.43J9LtyFx/XH.yvHddQRxEz.gJyftk.','0366640630','Khánh Yên, Văn Bàn, Lào Cai',1,'2020-08-02','/upload/10409146_407232502797437_912248228784820484_n.jpg',1),(21,'ngodat1997bn@gmail.com','Ngô Văn Đạt','$2a$10$sG81jfwZPSW8jFGACnTGneJRTLIqXeoHiZu/eKoRfB8zBDHnETXd.','0366640630','Đông Ngạc, Bắc Từ Liêm,Hà Nội',NULL,'2020-08-03',NULL,1);
/*!40000 ALTER TABLE `nguoidung` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-18 11:21:35
