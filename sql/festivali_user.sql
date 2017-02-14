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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `Username` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Surname` varchar(45) NOT NULL,
  `Phone` varchar(45) NOT NULL,
  `IdUser` int(11) NOT NULL AUTO_INCREMENT,
  `Approved` int(11) DEFAULT '0',
  `Blocked` int(11) DEFAULT '0',
  `Lives` int(11) DEFAULT '3' COMMENT 'Broj dozvoljenih isteklih rezervacija do banovanja',
  `LastLogin` timestamp NULL DEFAULT NULL,
  `AdminMessage` varchar(160) DEFAULT NULL,
  PRIMARY KEY (`IdUser`),
  UNIQUE KEY `IdUser_UNIQUE` (`IdUser`),
  UNIQUE KEY `Username_UNIQUE` (`Username`),
  UNIQUE KEY `UK_3fwf1qdgfhtmivqs75d6runwx` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('nydzis','nikolapa94@hotmail.com','asd','Nikola','Pantelic','0637605559',2,1,0,3,'2017-01-21 14:32:07',NULL),('a','a','a','a','a','a',3,1,1,0,'2017-01-21 15:54:25',NULL),('drasko','prt@gmail.com','asd','Drazen','Petrovic','123234345',4,1,0,3,NULL,NULL),('midza','mdrzdic@gmail.com','asd','Mirko','Drzdic','1123123',5,1,0,3,'2017-01-23 10:52:55',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
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
