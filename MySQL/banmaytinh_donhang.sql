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
-- Table structure for table `donhang`
--

DROP TABLE IF EXISTS `donhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donhang` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_chitietdonhang` varchar(100) NOT NULL,
  `id_nguoiduyet` int DEFAULT NULL,
  `id_nguoidung` int NOT NULL,
  `tongsotien` double NOT NULL,
  `tennguoinhan` varchar(100) NOT NULL,
  `diachi` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `dienthoai` varchar(30) NOT NULL,
  `trangthaithanhtoan` tinyint(1) NOT NULL,
  `thanhtoan` int NOT NULL,
  `vanchuyen` int NOT NULL,
  `ngaytao` date NOT NULL,
  `trangthaidonhang` int NOT NULL,
  `status` int NOT NULL,
  `ghichu` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_nguoiduyet` (`id_nguoiduyet`),
  KEY `id_nguoidung` (`id_nguoidung`),
  KEY `thanhtoan` (`thanhtoan`),
  KEY `vanchuyen` (`vanchuyen`),
  CONSTRAINT `donhang_ibfk_1` FOREIGN KEY (`id_nguoiduyet`) REFERENCES `nguoidung` (`id`),
  CONSTRAINT `donhang_ibfk_2` FOREIGN KEY (`id_nguoidung`) REFERENCES `nguoidung` (`id`),
  CONSTRAINT `donhang_ibfk_3` FOREIGN KEY (`thanhtoan`) REFERENCES `thanhtoan` (`id`),
  CONSTRAINT `donhang_ibfk_4` FOREIGN KEY (`vanchuyen`) REFERENCES `vanchuyen` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donhang`
--

LOCK TABLES `donhang` WRITE;
/*!40000 ALTER TABLE `donhang` DISABLE KEYS */;
INSERT INTO `donhang` VALUES (2,'DHMT0002',14,17,99686000,'Lê Thị Huyền Trang','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',1,1,1,'2020-07-17',2,1,''),(3,'DHMT0003',14,17,0,'Lê Thị Huyền Trang','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',1,1,1,'2020-07-17',3,1,''),(4,'DHMT0004',14,17,0,'Lê Thị Huyền Trang','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',1,1,1,'2020-07-17',3,1,''),(5,'DHMT0005',14,17,128996000,'Lê Thị Huyền Trang','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',1,2,1,'2020-07-17',2,1,'Gửi nhanh đấy'),(6,'DHMT0006',14,17,82895000,'Đặng Đức Tôn','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',0,2,1,'2020-07-17',3,1,''),(7,'DHMT0007',14,17,50497000,'Đặng Đức Tôn','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',1,1,2,'2020-07-17',2,1,'Gửi nhanh hộ'),(8,'DHMT0008',14,17,13999000,'Đặng Đức Tôn','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',1,1,1,'2020-07-17',2,1,''),(9,'DHMT0009',14,12,268969000,'Đặng Đức Tôn','Thụy Phương, Bắc Từ Liêm,Hà Nội','hoanganhduc@gmail.com','0366640630',1,2,1,'2020-07-17',2,1,''),(10,'DHMT0010',14,12,69997000,'Hoàng anh đức','Đông Ngạc, Bắc Từ Liêm,Hà Nội','hoanganhduc@gmail.com','0366640630',1,1,2,'2020-07-17',0,1,''),(11,'DHMT0011',14,17,80495000,'Đặng Đức Tôn','Thụy Phương, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',1,1,1,'2020-07-17',1,1,''),(12,'DHMT0012',14,17,139990000,'Lê Thị Huyền Trang','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',1,2,1,'2020-08-02',2,1,''),(13,'DHMT0013',14,17,125991000,'Lê Thị Huyền Trang','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',1,2,2,'2020-08-02',2,1,''),(14,'DHMT0014',14,17,139990000,'Lê Thị Huyền Trang','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',1,2,2,'2020-08-02',2,1,''),(15,'DHMT0015',14,17,104096000,'Lê Thị Huyền Trang','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',1,2,1,'2020-08-03',2,1,''),(16,'DHMT0016',14,17,90996000,'Lê Thị Huyền Trang','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',1,2,1,'2020-08-03',0,1,''),(17,'DHMT0017',NULL,17,208983000,'Lê Thị Huyền Trang','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',0,2,1,'2020-08-03',0,1,''),(18,'DHMT0018',NULL,17,142294000,'Lê Thị Huyền Trang','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',0,2,1,'2020-08-03',0,1,''),(19,'DHMT0019',NULL,17,49298000,'Lê Thị Huyền Trang','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',0,2,1,'2020-08-03',0,1,''),(20,'DHMT0020',14,21,73188000,'Ngô Văn Đạt','Đông Ngạc, Bắc Từ Liêm,Hà Nội','ngodat1997bn@gmail.com','0366640630',0,2,2,'2020-08-03',0,1,''),(21,'DHMT0021',14,17,119343000,'Lê Thị Huyền Trang','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',1,2,2,'2020-08-04',0,1,''),(22,'DHMT0022',NULL,15,150294000,'Yuè Bỉn Rỉn','Đông Ngạc, Bắc Từ Liêm,Hà Nội','dangducton@gmail.com','0366640630',0,2,1,'2020-08-14',0,1,''),(23,'DHMT0023',NULL,15,38999000,'Yuè Bỉn Rỉn','Đông Ngạc, Bắc Từ Liêm,Hà Nội','dangducton@gmail.com','0366640630',1,1,2,'2020-08-14',0,1,''),(24,'DHMT0024',NULL,17,80997000,'Lê Thị Huyền Trang','Đông Ngạc, Bắc Từ Liêm,Hà Nội','trangthocton@gmail.com','0366640630',1,1,1,'2020-08-14',0,1,''),(25,'DHMT0025',NULL,15,39999000,'Yuè Bỉn Rỉn','Đông Ngạc, Bắc Từ Liêm,Hà Nội','dangducton@gmail.com','0366640630',0,2,2,'2020-08-14',0,1,'');
/*!40000 ALTER TABLE `donhang` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-18 11:21:33
