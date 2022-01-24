/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 5.7.35-log : Database - fmbank
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fmbank` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `fmbank`;

/*Table structure for table `userinfo` */

DROP TABLE IF EXISTS `userinfo`;

CREATE TABLE `userinfo` (
  `cardid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(10) NOT NULL,
  `userpassword` varchar(20) NOT NULL,
  `userid` varchar(12) NOT NULL,
  `phonenum` varchar(11) DEFAULT NULL,
  `sex` char(1) NOT NULL,
  `birthday` varchar(10) NOT NULL,
  `money` double NOT NULL DEFAULT '2000',
  `memo` varchar(10) DEFAULT NULL,
  `registertime` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`cardid`),
  UNIQUE KEY `userid` (`userid`),
  UNIQUE KEY `phonenum` (`phonenum`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000015 DEFAULT CHARSET=gbk;

/*Data for the table `userinfo` */

insert  into `userinfo`(`cardid`,`username`,`userpassword`,`userid`,`phonenum`,`sex`,`birthday`,`money`,`memo`,`registertime`) values 
(100000000,'lp','1234','320200946111','15759021889','M','2001-08-23',66666,'管理员','2020'),
(1000000001,'张三','123456','320200946110','15645698753','M','2000-01-01',1100,NULL,'2021'),
(1000000002,'王五','123456','320200946112','14596578954','M','2000-02-10',2000,'','2021'),
(1000000003,'李四','1234','320200946115','15755555555','M','2001-03-08',2000,NULL,'2021'),
(1000000004,'fjb','1234','320200946011','18956561234','F','2000-03-01',6070,'老赖','2021'),
(1000000013,'lfr','123456','320255654777','15759021845','M','2001-02-22',2200,NULL,'2021'),
(1000000014,'tmc','1234','320200946999','15748963212','F','1992-02-22',2000,NULL,'2021');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
