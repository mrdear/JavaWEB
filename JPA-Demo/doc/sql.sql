/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.19-log : Database - pcard
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pcard` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `pcard`;

/*Table structure for table `tbl_pcard_order` */

CREATE TABLE `tbl_pcard_order` (
  `order_id` varchar(255) NOT NULL COMMENT '订单Id',
  `order_mnt_dt` varchar(255) NOT NULL COMMENT '原所属商户订单日期(追踪对账用)',
  `order_mnt_time` varchar(255) NOT NULL COMMENT ' 原所属商户订单时间(追踪对账用)',
  `order_mnt_code` varchar(255) NOT NULL COMMENT '原所属商户订单代码(追踪对账用)',
  `order_dt` varchar(255) NOT NULL COMMENT '订单日期（yyyyMMdd）',
  `order_time` varchar(255) NOT NULL COMMENT '订单时间（yyyyMMddHHMISS）',
  `order_type_id` varchar(255) NOT NULL COMMENT '订单类型，关键字段，确定订单对账户的加金还是减金',
  `acq_acct_id` varchar(255) DEFAULT NULL COMMENT '订单关联收单账号',
  `acq_channel_id` varchar(255) DEFAULT NULL COMMENT '不同渠道，当订单加金时候（账户充值，购买虚拟卡）填写',
  `order_acq_channel_code` varchar(255) DEFAULT NULL COMMENT '不同渠道的订单号，方便对账',
  `acct_id` varchar(255) DEFAULT NULL COMMENT '订单关联虚拟账号',
  `order_amt` bigint(20) DEFAULT NULL COMMENT '订单总金额，精确到分',
  `curr_cd` varchar(255) DEFAULT 'CNY' COMMENT '币别（默认CNY）',
  `order_st` varchar(2) NOT NULL,
  `order_resp_cd` varchar(255) NOT NULL COMMENT '订单交易响应码',
  `order_resp_msg` varchar(500) NOT NULL COMMENT '订单交易响应描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_modify_time` datetime NOT NULL COMMENT '最后修改时间',
  `st` varchar(1) NOT NULL COMMENT '状态(0:可用,1:不可用)',
  `vcard_id` varchar(255) DEFAULT NULL COMMENT '支付卡号,便于按卡号查询交易记录',
  `dis_amt` bigint(20) DEFAULT NULL COMMENT '优惠金额,便于统计',
  `pay_amt` bigint(20) DEFAULT NULL,
  `order_mnt_id` varchar(255) DEFAULT NULL COMMENT '商户号',
  `term_id` varchar(20) DEFAULT NULL COMMENT '终端号',
  `order_reserved` varchar(512) DEFAULT NULL COMMENT '保留域',
  `txn_type` varchar(12) DEFAULT NULL COMMENT '子交易类型',
  `resp_st` varchar(2) DEFAULT '0' COMMENT '00 ',
  `ref_no` varchar(64) DEFAULT NULL COMMENT '检索号',
  `sources` varchar(255) DEFAULT NULL COMMENT '来源',
  `versions` varchar(255) DEFAULT NULL,
  `bank_card` varchar(255) DEFAULT NULL COMMENT '所属银行卡',
  `pay_type` varchar(255) DEFAULT NULL COMMENT '支付方式 ',
  `is_scancode` char(1) DEFAULT NULL COMMENT '是否为扫码订单',
  `scancode` char(36) DEFAULT NULL COMMENT '条形码+二维码',
  `eancode` char(18) DEFAULT NULL,
  `inst_id` varchar(64) DEFAULT NULL COMMENT '机构号',
  `return_url` varchar(512) DEFAULT NULL COMMENT '同步返回',
  `notify_url` varchar(512) DEFAULT NULL COMMENT '异步通知',
  `app_id` varchar(64) DEFAULT NULL COMMENT '应用ID',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `seq_order_mnt_code__id` (`order_mnt_dt`,`order_mnt_code`) USING HASH,
  KEY `UNQ_mnt_time` (`order_mnt_time`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
