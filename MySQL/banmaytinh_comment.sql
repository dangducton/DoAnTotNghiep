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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tennguoidung` varchar(100) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `noidung` text,
  `id_sanpham` int NOT NULL,
  `id_nguoidung` int NOT NULL,
  `ngaytao` date NOT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_sanpham` (`id_sanpham`),
  KEY `id_nguoidung` (`id_nguoidung`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`id_sanpham`) REFERENCES `sanpham` (`id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`id_nguoidung`) REFERENCES `nguoidung` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'Hoàng anh đức','hoanganhduc@gmail.com','Sản phẩm tốt',44,12,'2020-07-06',1),(2,'Hoàng anh đức','hoanganhduc@gmail.com','Sản phẩm rất tốt\r\n',44,12,'2020-07-06',1),(3,'Hoàng anh đức','hoanganhduc@gmail.com','Sản phẩm tốt trong tầm giá',44,12,'2020-07-06',1),(4,'Hoàng anh đức','hoanganhduc@gmail.com','Sản phẩm hay',44,12,'2020-07-06',1),(5,'Hoàng anh đức','hoanganhduc@gmail.com','Sản phẩm oke',44,12,'2020-07-06',1),(6,'Hoàng anh đức','hoanganhduc@gmail.com','Sản phẩm rất tốt đấy\r\n',44,12,'2020-07-06',1),(7,'Hoàng anh đức','hoanganhduc@gmail.com','Cảm ơn đã tư vấn nhiệt tình\r\n',44,12,'2020-07-06',1),(8,'Đặng Đức Tôn','abc1234@gmail.com','san pham tot',52,16,'2020-07-06',1),(9,'Đặng Đức Tôn','admin@gmail.com','sản phẩm tốt',47,14,'2020-07-17',1),(10,'Đặng Đức Tôn','admin@gmail.com','máy dởm',47,14,'2020-07-17',1),(11,'Đặng Đức Tôn','admin@gmail.com','đắt',47,14,'2020-07-17',1),(12,'Lê Thị Huyền Trang','trangthocton@gmail.com','sản phẩm tốt nhưng đắt và dởm',45,17,'2020-08-03',1),(13,'Lê Thị Huyền Trang','trangthocton@gmail.com','giá hơi đắt, nhưng phục vụ tốt và máy ngon',47,17,'2020-08-03',1),(14,'Yuè Bỉn Rỉn','dangducton@gmail.com','xấu',44,15,'2020-08-14',0),(15,'Lê Thị Huyền Trang','trangthocton@gmail.com','xấu',44,17,'2020-08-14',0),(16,'Lê Thị Huyền Trang','trangthocton@gmail.com','xấu\r\n',44,17,'2020-08-14',0),(17,'Lê Thị Huyền Trang','trangthocton@gmail.com','xấu',44,17,'2020-08-14',0),(18,'Lê Thị Huyền Trang','trangthocton@gmail.com','tốt',44,17,'2020-08-14',1),(19,'Đặng Đức Tôn','admin@gmail.com','máy tính cấu hình cao, nhưng hơi nặng,màu sắc không đẹp',47,14,'2020-08-14',1),(20,'Đặng Đức Tôn','admin@gmail.com','xấu',47,14,'2020-08-14',0),(21,'Đặng Đức Tôn','admin@gmail.com','màu sắc đẹp, cấu hình thì xấu',47,14,'2020-08-14',0);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-18 11:21:32
