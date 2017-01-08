/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.24 : Database - test
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `test`;

/*Table structure for table `t_city` */

CREATE TABLE `t_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `state` varchar(30) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `map` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `t_city` */

LOCK TABLES `t_city` WRITE;

insert  into `t_city`(`id`,`name`,`state`,`country`,`map`) values (2,'ShangHai','China.ShangHai','China','1'),(3,'NanJing','China.NanJing','China','1'),(4,'BeiJing','China.BeiJing','China','1'),(5,'GuangDong','China.GuangDong','China','1'),(6,'ShanDong','China.ShanDong','China','0');

UNLOCK TABLES;

/*Table structure for table `t_hotel` */

CREATE TABLE `t_hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `t_hotel` */

LOCK TABLES `t_hotel` WRITE;

insert  into `t_hotel`(`id`,`name`,`address`,`city`) values (1,'ShangHai.HutaiLu','Seven.Day',2),(2,'ShangHai.HongQiao','MoTai',2),(3,'ShangHai.JiangXi','HanTing',2),(4,'ShangHai.RenMin','Eight.Day',2),(5,'ShangHai','Nine.Day',2),(8,'BeiJing','Seven.Day',3),(9,'BeiJing','Nine.Day',3),(10,'NanJing','MoTai',4),(11,'NanJing','HanTing',4),(12,'GuangDong','Nine.Day',5),(13,'GuangDong','Eight.Day',5);

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
