# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.30)
# Database: db_core
# Generation Time: 2020-12-23 08:48:29 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table hibernate_sequence
# ------------------------------------------------------------

DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;

INSERT INTO `hibernate_sequence` (`next_val`)
VALUES
	(17);

/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tbl_card
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tbl_card`;

CREATE TABLE `tbl_card` (
  `id_card` int(11) NOT NULL AUTO_INCREMENT,
  `card_type` enum('VISA','GPN','AMEX') DEFAULT NULL,
  `card_category` enum('Silver','Gold','Platinum') DEFAULT NULL,
  `monthly_fee` int(11) DEFAULT NULL,
  `card_daily_limit` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` enum('1','0') DEFAULT '1',
  `card_limit_daily` int(11) NOT NULL,
  PRIMARY KEY (`id_card`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `tbl_card` WRITE;
/*!40000 ALTER TABLE `tbl_card` DISABLE KEYS */;

INSERT INTO `tbl_card` (`id_card`, `card_type`, `card_category`, `monthly_fee`, `card_daily_limit`, `created_at`, `status`, `card_limit_daily`)
VALUES
	(1,'GPN','Silver',2000,25000000,'2020-12-17 22:27:43','1',0),
	(2,'VISA','Gold',5000,100000000,'2020-12-17 22:29:18','1',0),
	(3,'AMEX','Platinum',15000,250000000,'2020-12-17 22:29:46','1',0);

/*!40000 ALTER TABLE `tbl_card` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tbl_card_stock
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tbl_card_stock`;

CREATE TABLE `tbl_card_stock` (
  `id_card_stock` int(11) NOT NULL AUTO_INCREMENT,
  `id_card` int(11) DEFAULT NULL,
  `card_no` varchar(16) DEFAULT NULL,
  `expired_at` varchar(5) DEFAULT NULL,
  `cvv` varchar(5) DEFAULT NULL,
  `status` enum('1','0') DEFAULT '1',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_card_stock`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `tbl_card_stock` WRITE;
/*!40000 ALTER TABLE `tbl_card_stock` DISABLE KEYS */;

INSERT INTO `tbl_card_stock` (`id_card_stock`, `id_card`, `card_no`, `expired_at`, `cvv`, `status`, `created_at`)
VALUES
	(1,2,'1334242424323','08/23','576','1','2020-12-18 00:11:54'),
	(2,3,'5678912345678','07/24','467','1','2020-12-18 15:24:34'),
	(3,3,'5678912349892','09/25','476','1','2020-12-23 04:00:48');

/*!40000 ALTER TABLE `tbl_card_stock` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tbl_nasabah
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tbl_nasabah`;

CREATE TABLE `tbl_nasabah` (
  `id_nasabah` int(11) NOT NULL AUTO_INCREMENT,
  `no_rekening` varchar(20) NOT NULL DEFAULT '',
  `no_ktp` varchar(20) NOT NULL DEFAULT '',
  `status` enum('1','0') NOT NULL DEFAULT '1',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_nasabah`),
  UNIQUE KEY `no_ktp` (`no_ktp`),
  UNIQUE KEY `no_rekening` (`no_rekening`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `tbl_nasabah` WRITE;
/*!40000 ALTER TABLE `tbl_nasabah` DISABLE KEYS */;

INSERT INTO `tbl_nasabah` (`id_nasabah`, `no_rekening`, `no_ktp`, `status`, `created_at`)
VALUES
	(1,'000000007653','327806','1','2020-12-18 00:11:54'),
	(2,'000000002169','327807','1','2020-12-18 00:13:12'),
	(3,'000000000029','327808','1','2020-12-18 01:36:41'),
	(4,'000000004194','327809','1','2020-12-18 13:41:32'),
	(8,'000000008936','3278010','1','2020-12-18 14:03:11'),
	(10,'000000008625','327801011','1','2020-12-23 02:22:28'),
	(11,'000000003786','3278060000','1','2020-12-23 02:27:25'),
	(16,'000000001262','3278062707970010','1','2020-12-23 03:02:58');

/*!40000 ALTER TABLE `tbl_nasabah` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tbl_nasabah_card
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tbl_nasabah_card`;

CREATE TABLE `tbl_nasabah_card` (
  `id_nasabah_card` int(11) NOT NULL AUTO_INCREMENT,
  `id_nasabah` int(11) NOT NULL,
  `id_card_stock` int(11) DEFAULT NULL,
  `request_card` int(11) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('1','0') DEFAULT '1',
  PRIMARY KEY (`id_nasabah_card`),
  KEY `id_nasabah` (`id_nasabah`),
  KEY `id_card_stock` (`id_card_stock`),
  KEY `request_card` (`request_card`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `tbl_nasabah_card` WRITE;
/*!40000 ALTER TABLE `tbl_nasabah_card` DISABLE KEYS */;

INSERT INTO `tbl_nasabah_card` (`id_nasabah_card`, `id_nasabah`, `id_card_stock`, `request_card`, `created_at`, `status`)
VALUES
	(1,3,1,2,'2020-12-18 00:11:54','1'),
	(2,1,2,3,'2020-12-18 15:25:34','1'),
	(3,10,NULL,3,'2020-12-23 02:22:28','1'),
	(4,11,NULL,3,'2020-12-23 02:27:25','1'),
	(8,16,3,3,'2020-12-23 03:02:59','1');

/*!40000 ALTER TABLE `tbl_nasabah_card` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table tbl_transaksi
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tbl_transaksi`;

CREATE TABLE `tbl_transaksi` (
  `id_transaksi` int(11) NOT NULL AUTO_INCREMENT,
  `trx_refferal_code` varchar(20) DEFAULT NULL,
  `nama_transaksi` varchar(50) DEFAULT NULL,
  `nominal` int(11) DEFAULT NULL,
  `status_transaksi` enum('kredit','debit') DEFAULT NULL,
  `id_nasabah_card` int(11) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('1','0') NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_transaksi`),
  KEY `id_nasabah_card` (`id_nasabah_card`),
  CONSTRAINT `tbl_transaksi_ibfk_1` FOREIGN KEY (`id_nasabah_card`) REFERENCES `tbl_nasabah_card` (`id_nasabah_card`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `tbl_transaksi` WRITE;
/*!40000 ALTER TABLE `tbl_transaksi` DISABLE KEYS */;

INSERT INTO `tbl_transaksi` (`id_transaksi`, `trx_refferal_code`, `nama_transaksi`, `nominal`, `status_transaksi`, `id_nasabah_card`, `created_at`, `status`)
VALUES
	(1,'trx001','Beli Token',115000,'debit',1,'2020-11-10 15:06:05','1'),
	(2,'trx002','Beli Token',115000,'debit',1,'2020-11-11 15:06:21','1'),
	(3,'trx003','Setoran Tunai',5000000,'kredit',1,'2020-12-10 15:08:53','1'),
	(4,'trx004','Setoran Tunai',15000000,'kredit',1,'2020-12-11 15:08:57','1'),
	(5,'trx005','Setoran Tunai',15000000,'kredit',1,'2020-12-12 15:08:59','1'),
	(6,'trx006','Penarikan Tunai',2000000,'debit',1,'2020-12-18 15:09:14','1'),
	(7,'trx007','Setoran Tunai',2000000,'kredit',2,'2020-12-18 15:26:21','1'),
	(8,'trx008','Setoran Tunai',3000000,'kredit',2,'2020-12-18 15:26:24','1'),
	(10,'trxj60sw39oegup872','Setoran Tunai',3000000,'kredit',2,'2020-12-18 16:01:04','1'),
	(11,'trx7n9y1v3wpexh2re','Setoran Tunai',3000000,'kredit',2,'2020-12-18 16:20:51','1'),
	(12,'trxb3e8tl0y3v83jco','Tarik Tunai',100000,'debit',2,'2020-12-18 16:22:54','1'),
	(13,'trxph0y8w49j0c7fgk','Tarik Tunai',100000,'debit',2,'2020-12-18 16:29:44','1');

/*!40000 ALTER TABLE `tbl_transaksi` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table view_transaksi
# ------------------------------------------------------------

DROP VIEW IF EXISTS `view_transaksi`;

CREATE TABLE `view_transaksi` (
   `no_rekening` VARCHAR(20) NOT NULL DEFAULT '',
   `nama_transaksi` VARCHAR(50) NULL DEFAULT NULL,
   `nominal` INT(11) NULL DEFAULT NULL,
   `status_transaksi` ENUM('kredit','debit') NULL DEFAULT NULL,
   `card_no` VARCHAR(16) NULL DEFAULT NULL,
   `tgl_transaksi` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=MyISAM;





# Replace placeholder table for view_transaksi with correct view syntax
# ------------------------------------------------------------

DROP TABLE `view_transaksi`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_transaksi`
AS SELECT
   `c`.`no_rekening` AS `no_rekening`,
   `a`.`nama_transaksi` AS `nama_transaksi`,
   `a`.`nominal` AS `nominal`,
   `a`.`status_transaksi` AS `status_transaksi`,
   `D`.`card_no` AS `card_no`,
   `a`.`created_at` AS `tgl_transaksi`
FROM (((`tbl_transaksi` `a` join `tbl_nasabah_card` `b` on((`a`.`id_nasabah_card` = `b`.`id_nasabah_card`))) join `tbl_nasabah` `c` on((`b`.`id_nasabah` = `c`.`id_nasabah`))) join `tbl_card_stock` `D` on((`b`.`id_card_stock` = `D`.`id_card_stock`))) where ((`a`.`status` = 1) and (`b`.`status` = 1) and (`c`.`status` = 1));

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
