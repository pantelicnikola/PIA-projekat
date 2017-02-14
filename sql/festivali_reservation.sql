CREATE DATABASE  IF NOT EXISTS `festivali` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `festivali`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: festivali
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reservation` (
  `IdReservation` int(11) NOT NULL AUTO_INCREMENT,
  `IdFestival` int(11) DEFAULT NULL,
  `IdUser` int(11) DEFAULT NULL,
  `Type` int(11) DEFAULT NULL COMMENT '1..n - redni broj dana za koji je rezervisana karta\n0 - karta rezervisana za sve dana',
  `ReservationTime` timestamp NULL DEFAULT NULL,
  `Bought` int(11) DEFAULT '0',
  `Expired` int(11) DEFAULT '0',
  `TicketCount` int(11) DEFAULT NULL,
  `Reviewed` int(11) DEFAULT '0',
  PRIMARY KEY (`IdReservation`),
  UNIQUE KEY `IdReservation_UNIQUE` (`IdReservation`),
  KEY `IdFestival_idx` (`IdFestival`),
  KEY `IdUser_idx` (`IdUser`),
  CONSTRAINT `IdFestivalReservation` FOREIGN KEY (`IdFestival`) REFERENCES `festival` (`IdFestival`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `IdUserReservation` FOREIGN KEY (`IdUser`) REFERENCES `user` (`IdUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (1,3,3,1,'2017-01-13 17:25:16',0,1,2,0),(2,3,3,0,'2017-01-13 17:39:55',0,1,4,0),(4,5,3,2,'2017-01-18 17:15:27',1,0,3,0),(5,6,3,2,'2017-01-18 17:24:43',1,0,3,1),(10,4,3,5,'2017-01-18 18:44:33',0,1,6,0),(11,3,3,2,'2017-01-20 14:19:08',0,1,3,0),(13,3,3,1,'2017-01-20 14:52:57',0,1,8,0),(14,14,3,0,'2017-01-20 14:51:23',1,0,3,1),(15,32,2,0,'2015-08-30 13:12:12',1,0,2,1),(17,4,5,3,'2017-01-23 10:52:08',1,0,4,0);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-26 14:17:59
