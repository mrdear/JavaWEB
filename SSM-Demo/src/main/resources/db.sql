/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.24 : Database - ssm
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`ssm` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ssm`;



/*Table structure for table `book` */

CREATE TABLE `book` (
  `book_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '图书ID',
  `name` varchar(100) NOT NULL COMMENT '图书名称',
  `number` int(11) NOT NULL COMMENT '馆藏数量',
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8 COMMENT='图书表';

/*Data for the table `book` */

LOCK TABLES `book` WRITE;

insert  into `book`(`book_id`,`name`,`number`) values (1000,'Java程序设计',10),(1001,'数据结构',10),(1002,'设计模式',10),(1003,'编译原理',10);

UNLOCK TABLES;
