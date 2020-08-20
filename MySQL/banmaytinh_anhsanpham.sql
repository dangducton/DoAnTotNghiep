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
-- Table structure for table `anhsanpham`
--

DROP TABLE IF EXISTS `anhsanpham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anhsanpham` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_sanpham` int NOT NULL,
  `url_anh` varchar(255) NOT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_sanpham` (`id_sanpham`),
  CONSTRAINT `anhsanpham_ibfk_1` FOREIGN KEY (`id_sanpham`) REFERENCES `sanpham` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=278 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anhsanpham`
--

LOCK TABLES `anhsanpham` WRITE;
/*!40000 ALTER TABLE `anhsanpham` DISABLE KEYS */;
INSERT INTO `anhsanpham` VALUES (161,44,'/upload/47297_vivobook_a512da_ej422t__1_.png',1),(162,44,'/upload/47297_vivobook_a512da_ej422t__2_.png',1),(164,44,'/upload/47297_vivobook_a512da_ej422t__4_.png',1),(165,44,'/upload/47297_vivobook_a512da_ej422t__5_.png',1),(167,44,'/upload/47297_vivobook_a512da_ej422t__3_.png',1),(168,45,'/upload/48309_dell_xps_13_9370_415px3__1_.png',1),(169,45,'/upload/48309_dell_xps_13_9370_415px3__2_.png',1),(170,45,'/upload/48309_dell_xps_13_9370_415px3__3_.png',1),(171,45,'/upload/48309_dell_xps_13_9370_415px3__4_.png',1),(172,45,'/upload/48309_dell_xps_13_9370_415px3__5_.png',1),(173,45,'/upload/120_48309_dell_xps_13_9370_415px3__6_.png',1),(174,45,'/upload/120_48309_dell_xps_13_9370_415px3__7_.png',1),(175,46,'/upload/48048_mat_ngoai_vivobook_a512da_1.png',1),(176,46,'/upload/48048_mat_ngoai_vivobook_a512da_2.png',1),(177,46,'/upload/48048_mat_trong_vivobook_a512da_2.png',1),(178,46,'/upload/48048_mat_trong_vivobook_a512da_1.png',1),(179,47,'/upload/52607_tuf_fa506ih__1_.jpg',1),(180,47,'/upload/52607_tuf_fa506ih__2_.jpg',1),(181,47,'/upload/52607_tuf_fa506ih__4_.jpg',1),(182,47,'/upload/52607_tuf_fa506ih__5_.jpg',1),(183,47,'/upload/52607_tuf_fa506ih__6_.jpg',1),(184,47,'/upload/52607_tuf_fa506ih__7_.jpg',1),(185,47,'/upload/52607_tuf_fa506ih__8_.jpg',1),(186,47,'/upload/52607_tuf_fa506ih__9_.jpg',1),(187,48,'/upload/53307_zephyrus_ga401ii_he155t__1_.jpg',1),(188,48,'/upload/53307_zephyrus_ga401ii_he155t__3_.jpg',1),(189,48,'/upload/53307_zephyrus_ga401ii_he155t__4_.jpg',1),(190,48,'/upload/53307_zephyrus_ga401ii_he155t__5_.jpg',1),(191,48,'/upload/53307_zephyrus_ga401ii_he155t__7_.jpg',1),(192,48,'/upload/53307_zephyrus_ga401ii_he155t__8_.jpg',1),(193,48,'/upload/53307_zephyrus_ga401ii_he155t__9_.jpg',1),(194,49,'/upload/53534_zephyrus_ga401iu__3_.jpg',1),(195,49,'/upload/53534_zephyrus_ga401iu__4_.jpg',1),(196,49,'/upload/53534_zephyrus_ga401iu__5_.jpg',1),(197,49,'/upload/53534_zephyrus_ga401iu__7_.jpg',1),(198,49,'/upload/53534_zephyrus_ga401iu__8_.jpg',1),(199,49,'/upload/53534_zephyrus_ga401iu__9_.jpg',1),(200,50,'/upload/46886_acer_swift_3_sf314_56_38ue__2_.png',1),(201,50,'/upload/46886_acer_swift_3_sf314_56_38ue__3_.png',1),(202,50,'/upload/46886_acer_swift_3_sf314_56_38ue__4_.png',1),(203,50,'/upload/46886_acer_swift_3_sf314_56_38ue__5_.png',1),(204,50,'/upload/46886_acer_swift_3_sf314_56_38ue__7_.png',1),(205,50,'/upload/46886_acer_swift_3_sf314_56_38ue__6_.png',1),(206,51,'/upload/49909_acer_nitro_5_an515_54_53p6__1_.png',1),(207,51,'/upload/49909_acer_nitro_5_an515_54_53p6__2_.png',1),(208,51,'/upload/49909_acer_nitro_5_an515_54_53p6__5_.png',1),(209,51,'/upload/49909_acer_nitro_5_an515_54_53p6__6_.png',1),(210,51,'/upload/49909_acer_nitro_5_an515_54_53p6__7_.png',1),(211,52,'/upload/48252_hp_14s__1_.jpg',1),(212,52,'/upload/48252_hp_14s__2_.jpg',1),(213,52,'/upload/48252_hp_14s__3_.jpg',1),(214,52,'/upload/48252_hp_14s__4_.jpg',1),(215,53,'/upload/51689_laptop_lenovo_ideapad_s540_15iml__81ng004rvn__i5_xam_01.png',1),(216,53,'/upload/51689_laptop_lenovo_ideapad_s540_15iml__81ng004rvn__i5_xam_02.png',1),(217,53,'/upload/51689_laptop_lenovo_ideapad_s540_15iml__81ng004rvn__i5_xam_03.png',1),(218,53,'/upload/51689_laptop_lenovo_ideapad_s540_15iml__81ng004rvn__i5_xam_04.png',1),(219,53,'/upload/51689_laptop_lenovo_ideapad_s540_15iml__81ng004rvn__i5_xam_05.png',1),(220,53,'/upload/51689_laptop_lenovo_ideapad_s540_15iml__81ng004rvn__i5_xam_08.jpg',1),(221,54,'/upload/43995_laptop_lg_gram_14z980_g_ah52a5_i5_01.jpg',1),(222,54,'/upload/43995_laptop_lg_gram_14z980_g_ah52a5_i5_02.jpg',1),(223,54,'/upload/43995_laptop_lg_gram_14z980_g_ah52a5_i5_03.jpg',1),(224,54,'/upload/43995_laptop_lg_gram_14z980_g_ah52a5_i5_04.jpg',1),(225,54,'/upload/43995_laptop_lg_gram_14z980_g_ah52a5_i5_05.jpg',1),(226,54,'/upload/43995_laptop_lg_gram_14z980_g_ah52a5_i5.jpg',1),(227,55,'/upload/52020_laptop_lg_gram_15zd90n_v_ax56a5_i5_trang_02.jpg',1),(228,55,'/upload/52020_laptop_lg_gram_15zd90n_v_ax56a5_i5_trang_03.jpg',1),(229,55,'/upload/52020_laptop_lg_gram_15zd90n_v_ax56a5_i5_trang_04.jpg',1),(230,55,'/upload/52020_laptop_lg_gram_15zd90n_v_ax56a5_i5_trang_05.jpg',1),(231,55,'/upload/52020_laptop_lg_gram_15zd90n_v_ax56a5_i5_trang_06.jpg',1),(232,55,'/upload/52020_15zd90n_v_ax56a5logo.png',1),(233,56,'/upload/51489_macbook_pro_16_touchbar_mvvk2sa__1_.png',1),(234,56,'/upload/51489_macbook_pro_16_touchbar_mvvk2sa__2_.png',1),(235,56,'/upload/51489_macbook_pro_16_touchbar_mvvk2sa__3_.png',1),(236,56,'/upload/51489_macbook_pro_16_touchbar_mvvk2sa__4_.png',1),(237,56,'/upload/120_51489_macbook_pro_16_touchbar_mvvk2sa__5_.png',1),(238,57,'/upload/51553_laptop_lenovo_legion_y540_15irh__81uv005avn__i7_den_01.jpg',1),(239,57,'/upload/51553_laptop_lenovo_legion_y540_15irh__81uv005avn__i7_den_02.jpg',1),(240,57,'/upload/51553_laptop_lenovo_legion_y540_15irh__81uv005avn__i7_den_03.jpg',1),(241,57,'/upload/51553_laptop_lenovo_legion_y540_15irh__81uv005avn__i7_den_04.jpg',1),(242,57,'/upload/51553_laptop_lenovo_legion_y540_15irh__81uv005avn__i7_den.jpg',1),(243,58,'/upload/52388_msi_gaming_gf63_9sc_400vn__8_.jpg',1),(244,58,'/upload/52388_msi_gaming_gf63_9sc_400vn__1_.jpg',1),(245,58,'/upload/52388_msi_gaming_gf63_9sc_400vn__2_.jpg',1),(246,58,'/upload/52388_msi_gaming_gf63_9sc_400vn__3_.jpg',1),(247,58,'/upload/52388_msi_gaming_gf63_9sc_400vn__4_.jpg',1),(248,58,'/upload/52388_msi_gaming_gf63_9sc_400vn__5_.jpg',1),(249,59,'/upload/52057_lenovo_t530_01 (1).jpg',1),(250,59,'/upload/52057_lenovo_t530.jpg',1),(251,59,'/upload/52057_lenovo_t530_01.jpg',1),(252,60,'/upload/52516_pc_asus_rog_strix_g15dh__1_.jpg',1),(253,60,'/upload/52516_pc_asus_rog_strix_g15dh__2_.jpg',1),(254,60,'/upload/52516_pc_asus_rog_strix_g15dh__3_ (1).jpg',1),(255,60,'/upload/52516_pc_asus_rog_strix_g15dh__3_.jpg',1),(256,61,'/upload/49334_pc_asus_rog_huracan_g21cx_i5_9400_5.jpg',1),(257,61,'/upload/49334_pc_asus_rog_huracan_g21cx_i5_9400_6.jpg',1),(258,61,'/upload/49334_pc_asus_rog_huracan_g21cx_i5_9400_7.jpg',1),(259,61,'/upload/49334_pc_asus_rog_huracan_g21cx_i5_9400_8.jpg',1),(260,61,'/upload/49334_pc_asus_rog_huracan_g21cx_i5_9400_9.jpg',1),(261,62,'/upload/52603_47428_dell_poweredge_r240_021.png',1),(262,63,'/upload/45357_dell_precision_3630_021 (1).png',1),(263,63,'/upload/45357_dell_precision_3630_022.png',1),(264,63,'/upload/45357_dell_precision_3630_021.png',1),(265,64,'/upload/50153_47124_dell_poweredge_t140_021 (1).png',1),(266,64,'/upload/50153_47124_dell_poweredge_t140_022.png',1),(267,64,'/upload/50153_47124_dell_poweredge_t140_021.png',1),(268,65,'/upload/44362_pc_hp_prodesk_400_h__0_ (1).jpg',1),(269,65,'/upload/44362_pc_hp_prodesk_400_h__1_.jpg',1),(270,65,'/upload/44362_pc_hp_prodesk_400_h__0_.jpg',1),(271,66,'/upload/46502_elitedesk_01 (1).png',1),(272,66,'/upload/46502_elitedesk_02.png',1),(273,66,'/upload/46502_elitedesk_01.png',1),(274,67,'/upload/49711_dell_optiplex_3070_sff.jpg',1),(275,68,'/upload/47650_dell_inspiron_3470_021 (1).jpg',1),(276,68,'/upload/47650_dell_inspiron_3470_022.jpg',1),(277,68,'/upload/47650_dell_inspiron_3470_021.jpg',1);
/*!40000 ALTER TABLE `anhsanpham` ENABLE KEYS */;
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
