/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.24 : Database - haikong
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`haikong` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `haikong`;

/*Table structure for table `switchdevice` */

CREATE TABLE `switchdevice` (
  `device_id` varchar(255) NOT NULL,
  `device_type` varchar(255) NOT NULL,
  `onoffStatus` tinyint(1) DEFAULT NULL,
  `data` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `asy_error_code` int(10) DEFAULT '50013',
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `switchdevice` */

LOCK TABLES `switchdevice` WRITE;

insert  into `switchdevice`(`device_id`,`device_type`,`onoffStatus`,`data`,`create_time`,`asy_error_code`) values ('gh_4f7d3885c0a9_24bf775bf31306a6','gh_4f7d3885c0a9',0,'{\"ct\":\"0-5-1\",\"ene\":0,\"power\":0,\"t1\":\"1-920-922-127\",\"t2\":\"1-510-810-127\",\"t3\":\"1-520-820-127\",\"t4\":\"0-530-830-127\",\"vol\":0}',NULL,50013),('gh_4f7d3885c0a9_5cb36c1e609fc5e4','gh_4f7d3885c0a9',0,'{\"vol\":2330,\"power\":0.0,\"ene\":123,\"t1\":\"1-485-495-127\",\"t2\":\"0-950-955-127\",\"t3\":\"1-960-965-127\",\"t4\":\"0-970-975-127\",\"ct\":\"0-30-0\"}','2016-07-26 00:00:02',0),('gh_4f7d3885c0a9_6862ecf1431908aa','gh_4f7d3885c0a9',0,'{\"vol\":2277,\"power\":0,\"ene\":0,\"t1\":\"1-590-592-127\",\"t2\":\"1-595-597-127\",\"t3\":\"1-600-602-127\",\"t4\":\"1-605-610-127\",\"ct\":\"0-5-1\"}','2016-06-08 09:28:23',50013),('gh_4f7d3885c0a9_6aae48686d7aa518','gh_4f7d3885c0a9',0,'{\"vol\":0,\"power\":0,\"ene\":0,\"t1\":\"0-920-922-127\",\"t2\":\"0-510-810-127\",\"t3\":\"0-520-820-127\",\"t4\":\"0-530-830-127\",\"ct\":\"0-5-1\"}','2016-06-13 19:53:54',50013),('gh_4f7d3885c0a9_6c5ecdde0b08ee85','gh_4f7d3885c0a9',1,'{\"vol\":2688,\"power\":41,\"ene\":0,\"t1\":\"\",\"t2\":\"\",\"t3\":\"\",\"t4\":\"\",\"ct\":\"\"}','2016-07-01 17:35:49',0),('gh_4f7d3885c0a9_9ea2808950a2dce5','gh_4f7d3885c0a9',0,'{\"vol\":2250,\"power\":4,\"ene\":0,\"t1\":\"0-920-922-127\",\"t2\":\"0-510-810-127\",\"t3\":\"0-520-820-127\",\"t4\":\"0-530-830-127\",\"ct\":\"0-5-1\"}','2016-06-07 21:04:43',50013),('gh_4f7d3885c0a9_9eeb6cd5b2248acc','gh_4f7d3885c0a9',0,'{\"vol\":42,\"power\":0.0,\"ene\":2,\"t1\":\"0-4-14341-0\",\"t2\":\"159--22525-10753-2\",\"t3\":\"224--20480-0-4\",\"t4\":\"0-0-0-0\",\"ct\":\"0-8032-31\"}','2016-07-11 15:38:33',0),('gh_4f7d3885c0a9_b5d8814afb0644d5','gh_4f7d3885c0a9',0,'{\"ct\":\"0-5-1\",\"ene\":0,\"power\":0,\"t1\":\"1-920-922-127\",\"t2\":\"1-510-810-127\",\"t3\":\"1-520-820-127\",\"t4\":\"0-530-830-127\",\"vol\":0}',NULL,50013),('gh_4f7d3885c0a9_bc77b4f0d2d7a89e','gh_4f7d3885c0a9',0,'{\"vol\":2288,\"power\":0.0,\"ene\":3,\"t1\":\"0-590-592-127\",\"t2\":\"1-595-597-127\",\"t3\":\"1-600-602-127\",\"t4\":\"1-605-610-127\",\"ct\":\"0-2-0\"}','2016-07-23 18:09:25',0),('gh_4f7d3885c0a9_d5abb053ddb1bf6b','gh_4f7d3885c0a9',0,'{\"vol\":2342,\"power\":0.0,\"ene\":71,\"t1\":\"1-920-922-127\",\"t2\":\"0-510-810-127\",\"t3\":\"1-520-820-127\",\"t4\":\"0-530-830-127\",\"ct\":\"0-5-1\"}','2016-07-12 16:44:59',0),('gh_4f7d3885c0a9_ddc94c4cbd475075','gh_4f7d3885c0a9',0,'{\"vol\":2319,\"power\":0.0,\"ene\":13,\"t1\":\"1-551-553-2\",\"t2\":\"0-0-0-0\",\"t3\":\"0-0-0-0\",\"t4\":\"0-0-0-0\",\"ct\":\"0-2-0\"}','2016-07-09 15:04:35',50012),('gh_4f7d3885c0a9_df131bbd83974bc8','gh_4f7d3885c0a9',0,'{\"vol\":0,\"power\":0,\"ene\":0,\"t1\":\"\",\"t2\":\"\",\"t3\":\"\",\"t4\":\"\",\"ct\":\"\"}','2016-06-27 16:06:21',50013),('gh_4f7d3885c0a9_e0242e4ca8017c8d','gh_4f7d3885c0a9',0,'{\"vol\":2319,\"power\":0.0,\"ene\":13,\"t1\":\"1-551-553-2\",\"t2\":\"0-0-0-0\",\"t3\":\"0-0-0-0\",\"t4\":\"0-0-0-0\",\"ct\":\"0-2-0\"}',NULL,50012);

UNLOCK TABLES;

/*Table structure for table `user` */

CREATE TABLE `user` (
  `openid` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `sex` int(1) DEFAULT NULL,
  `language` varchar(20) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `headimgurl` varchar(255) DEFAULT NULL,
  `subscribe_time` datetime NOT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `groupid` int(5) DEFAULT NULL,
  `tagid_list` varchar(50) DEFAULT NULL,
  `subscribe` int(1) NOT NULL,
  `device_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

LOCK TABLES `user` WRITE;

insert  into `user`(`openid`,`nickname`,`sex`,`language`,`country`,`province`,`city`,`headimgurl`,`subscribe_time`,`remark`,`groupid`,`tagid_list`,`subscribe`,`device_id`) values ('ocT6Ivwa8DBigrwt2vJQfnQYZ_Ho','MR.dear',1,'zh_CN','中国','安徽','淮南','http://wx.qlogo.cn/mmopen/ajNVdqHZLLABYMN0Tsttb5HHrlPG2aU9oDhDJMfFaYKbu4aVpvcsTYP6KibfBrwQWyLGr3ib8sXWoLqZKSPpzPmQ/0','1970-01-18 09:19:26','',0,'[]',0,NULL),('ov4a6wg2snvQ3eJiZcxJI31noVZk','梅斯塔利亚球童',1,'zh_CN','中国','安徽','淮南','http://wx.qlogo.cn/mmopen/Q3auHgzwzM6zC3QlibnibafA7EtI3vayCVVZiamd9r2ulYR5G4uVkVYvhUZ1XuHfwb8WTHkYw1fjw8o9Jgr41XlEw/0','2016-06-13 10:55:52','',0,'[]',1,NULL),('ov4a6wiikevsn_Wev3xVQz1UEAhY','洛齐',1,'zh_CN','中国','安徽','淮南','http://wx.qlogo.cn/mmopen/wotl6RIXwvTP6uQEHEmpDHg4oVlLMHaDNRicTwD03tG4JGJoLqCuOIRQicIB2C9s001J2sPAGU4j4w4IaGKxiccMeBeNlL3FaicE/0','2016-07-09 12:29:57','',0,'[]',1,NULL),('ov4a6wi_ZdkGdIxAlyVwlHkisFXI','MR.dear',1,'zh_CN','中国','安徽','淮南','http://wx.qlogo.cn/mmopen/ajNVdqHZLLDVFjDEYwqoOKMrhHbBwK2WhN8I89Tdu8iajz1rI244L26qrvSFmshDX8WlW542TlvA8w4VibXKR0Sg/0','2016-05-20 10:52:57','',0,'[]',1,NULL),('ov4a6wl9hqiaIjq35gqKBfq8V__g','木字柏',1,'zh_CN','中国','河南','郑州','http://wx.qlogo.cn/mmopen/wotl6RIXwvTP6uQEHEmpDH1WU61QmVpehnPO2gv8hX0vXXTJa12QgHFy82jst6It5sVgkos110xh14T0SlojX3RLdiae3wLt9/0','2016-06-28 09:37:05','',0,'[]',1,NULL),('ov4a6wnuLygS245t7p8JsKJmzAhM','丁丁',1,'zh_CN','中国','北京','','http://wx.qlogo.cn/mmopen/JVDECnNjedHtic678xUWBMyW2HEXHbdVSPzl7acIznEzXEX0iabMyJzuicmjcXKMM6xlBmEnK5X3Bwm0GfFcLmWgw/0','2016-07-02 23:35:00','',0,'[]',1,NULL),('ov4a6woxlwp3vo_rTu6nL_HK1Hw8','童鑫',1,'zh_CN','中国','安徽','淮南','http://wx.qlogo.cn/mmopen/ajNVdqHZLLAOPbQXZP7mNpeNfdiaoaL1x3cTmv1bgfC6htRW5WFZ31oxFRnStQeqF78iaLMZRianrI6bDjcIrUw2Q/0','2016-07-04 10:56:36','',0,'[]',1,NULL),('ov4a6wq0aYpL4yVXuNdsWnx50O1w','笑笑',2,'zh_CN','中国','浙江','嘉兴','http://wx.qlogo.cn/mmopen/wotl6RIXwvTP6uQEHEmpDHce2C5RcP8Q6F67ZQOHwEV8xGvtoKQCHKMvmB3FTnZ3tM25FicMgIWQsrChUgYXFR6qibeY7ibQJY4/0','2016-05-20 10:56:35','',0,'[]',1,NULL),('ov4a6wqwZnD4A40ptII5HRfcl5P4','星之辉',1,'zh_CN','中国','河南','郑州','http://wx.qlogo.cn/mmopen/ajNVdqHZLLCsgtZribtLYslXHibVxN9pQs8ju3EI5Iju5JgV33DWF8B9KYO0f3ChiasPA7ibrrpCx5ca4TQq1swFUg/0','2016-06-27 17:32:58','',0,'[]',1,NULL),('ov4a6wriUlQK7c2b4J9GdlFMT-lM','张馨元',2,'zh_CN','中国','安徽','淮南','http://wx.qlogo.cn/mmopen/PiajxSqBRaEL095aT9yJXxjolfHPwmEI5DvB8qahl4mQQSYeCSvV5pUaXmHNGJBniaXF9yGBBUPic7amwAZlD0lNw/0','2016-06-07 22:57:22','',0,'[]',1,NULL),('ov4a6wsY4w_iD887DUlJAmtcqX0E','丽丽',2,'zh_CN','中国','安徽','淮南','http://wx.qlogo.cn/mmopen/ajNVdqHZLLBZ4nibrIsryUwia5ibmmkHZx1cqt4m8nN5oMIicmJGC5LOaANcgWb86hJHZ4yRziaYqYBDWewGTnXysMw/0','2016-05-20 12:59:11','',0,'[]',1,NULL),('ov4a6wszjYYE3oTZ-g7dvvlfVHU8','づヤ↘',1,'zh_CN','冰岛','','','http://wx.qlogo.cn/mmopen/C6GINSn24pTeWxlFk1AbM19ZtIOzibv5IuhQgzp4ordS2Gbh50SbGiaLEobib3ys5vMBfbibqAiafVh4azibmhxoPdtQbQoqGIldibz/0','2016-07-01 20:31:13','',0,'[]',1,NULL),('ov4a6wtqBple7-0p-Wk_dwWdJo9w','MR.dear',1,'zh_CN','中国','安徽','淮南','http://wx.qlogo.cn/mmopen/ajNVdqHZLLDVFjDEYwqoOKMrhHbBwK2WhN8I89Tdu8iajz1rI244L26qrvSFmshDX8WlW542TlvA8w4VibXKR0Sg/0','2016-07-12 23:25:42','',0,'[]',1,NULL),('ov4a6wuj8FYpUFWW48gCP0iM2Vdo','查查网-姚多桂',1,'zh_CN','中国','安徽','淮南','http://wx.qlogo.cn/mmopen/wotl6RIXwvTP6uQEHEmpDG34jfISWkLlLjwV54XXBjVOjjiaUlbFxpl1Be40yuFTsuIwcmXBvvUtnTfyllDndtDGFJRTRg1ll/0','2016-05-31 16:11:56','',0,'[]',1,NULL),('ov4a6wvc5HVK7Dgghk8NxC22vG4g','lens',1,'zh_CN','中国','浙江','绍兴','http://wx.qlogo.cn/mmopen/C6GINSn24pQEmfQSSPIJqjLiauloPYLPcMicLZXvGq8wBZCYicjl6CYYnRtKPmBv578NEnwDopzqb3icZYYwbexqEQ/0','2016-06-29 16:49:53','',0,'[]',1,NULL);

UNLOCK TABLES;

/*Table structure for table `user_device` */

CREATE TABLE `user_device` (
  `device_id` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `device_type` varchar(255) DEFAULT NULL,
  `bz` varchar(255) DEFAULT '我的插座'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_device` */

LOCK TABLES `user_device` WRITE;

insert  into `user_device`(`device_id`,`user`,`device_type`,`bz`) values ('gh_4f7d3885c0a9_9ea2808950a2dce5','ov4a6wsY4w_iD887DUlJAmtcqX0E','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_9ea2808950a2dce5','ov4a6wi_ZdkGdIxAlyVwlHkisFXI','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_6aae48686d7aa518','ov4a6wuj8FYpUFWW48gCP0iM2Vdo','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_6aae48686d7aa518','ov4a6wriUlQK7c2b4J9GdlFMT-lM','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_9ea2808950a2dce5','ov4a6wriUlQK7c2b4J9GdlFMT-lM','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_5cb36c1e609fc5e4','ov4a6wriUlQK7c2b4J9GdlFMT-lM','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_6c5ecdde0b08ee85','ov4a6wuNSuFHzF06WH5s6SGgXiw0','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_5cb36c1e609fc5e4','ov4a6wg2snvQ3eJiZcxJI31noVZk','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_5cb36c1e609fc5e4','ov4a6wg2snvQ3eJiZcxJI31noVZk','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_5cb36c1e609fc5e4','ov4a6wvc5HVK7Dgghk8NxC22vG4g','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_ddc94c4cbd475075','ov4a6woxlwp3vo_rTu6nL_HK1Hw8','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_d5abb053ddb1bf6b','ov4a6woxlwp3vo_rTu6nL_HK1Hw8','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_9eeb6cd5b2248acc','ov4a6woxlwp3vo_rTu6nL_HK1Hw8','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_bc77b4f0d2d7a89e','ov4a6woxlwp3vo_rTu6nL_HK1Hw8','gh_4f7d3885c0a9','卧室'),('gh_4f7d3885c0a9_d5abb053ddb1bf6b','ov4a6wnuLygS245t7p8JsKJmzAhM','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_bc77b4f0d2d7a89e','ov4a6wtqBple7-0p-Wk_dwWdJo9w','gh_4f7d3885c0a9','卧室'),('gh_4f7d3885c0a9_bc77b4f0d2d7a89e','ov4a6wi_ZdkGdIxAlyVwlHkisFXI','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_d5abb053ddb1bf6b','ov4a6wi_ZdkGdIxAlyVwlHkisFXI','gh_4f7d3885c0a9','我的插座'),('gh_4f7d3885c0a9_d5abb053ddb1bf6b','ocT6Ivwa8DBigrwt2vJQfnQYZ_Ho','gh_4f7d3885c0a9','嘻嘻'),('gh_4f7d3885c0a9_ddc94c4cbd475075','ocT6Ivwa8DBigrwt2vJQfnQYZ_Ho','gh_4f7d3885c0a9','嘻嘻'),('gh_4f7d3885c0a9_ddc94c4cbd475075','ocT6Ivwa8DBigrwt2vJQfnQYZ_Ho','gh_4f7d3885c0a9','嘻嘻');

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
