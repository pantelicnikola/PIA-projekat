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
-- Table structure for table `festival`
--

DROP TABLE IF EXISTS `festival`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `festival` (
  `IdFestival` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `Place` varchar(45) DEFAULT NULL,
  `StartDate` date DEFAULT NULL,
  `EndDate` date DEFAULT NULL,
  `Rating` double DEFAULT '0',
  `NumRatings` int(11) DEFAULT '0',
  `Description` varchar(160) NOT NULL DEFAULT 'Lorem ipsum dolor sit amet, duo ei democritum elaboraret. Mea in ullum ridens expetenda, vim ne praesent voluptatibus. ',
  `ViewCount` int(11) DEFAULT '0',
  `TicketsSold` int(11) DEFAULT '0',
  `SingleDay` int(11) DEFAULT NULL,
  `AllDays` int(11) DEFAULT NULL,
  `TicketsPerReservation` int(11) DEFAULT NULL,
  `TicketsPerDay` int(11) DEFAULT NULL,
  PRIMARY KEY (`IdFestival`),
  UNIQUE KEY `IdFestival_UNIQUE` (`IdFestival`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `festival`
--

LOCK TABLES `festival` WRITE;
/*!40000 ALTER TABLE `festival` DISABLE KEYS */;
INSERT INTO `festival` VALUES (3,'Beer Fest','Beograd','2017-08-23','2017-08-28',0,0,'Lorem ipsum dolor sit amet, duo ei democritum elaboraret. Mea in ullum ridens expetenda, vim ne praesent voluptatibus. ',57,0,12,56,10,10),(4,'Guca','Cacak','2017-08-09','2017-08-13',0,0,'Lorem ipsum dolor sit amet, duo ei democritum elaboraret. Mea in ullum ridens expetenda, vim ne praesent voluptatibus. ',3,5,1300,4500,10,10),(5,'NisVille','Nis','2017-08-10','2017-08-13',0,0,'Lorem ipsum dolor sit amet, duo ei democritum elaboraret. Mea in ullum ridens expetenda, vim ne praesent voluptatibus. ',3,6,3000,8000,10,10),(6,'FilmFest','Pristina','2016-01-10','2016-01-31',7,1,'Lorem ipsum dolor sit amet, duo ei democritum elaboraret. Mea in ullum ridens expetenda, vim ne praesent voluptatibus. ',8,8,500,4500,10,10),(7,'Sausage Fest','Zajecar','2016-03-10','2016-03-21',0,0,'Lorem ipsum dolor sit amet, duo ei democritum elaboraret. Mea in ullum ridens expetenda, vim ne praesent voluptatibus. ',0,12,300,1000,10,10),(14,'Mile voli disko','asd','2017-01-15','2017-01-15',10,1,'asd',0,0,12,12,10,10),(20,' Bravo',' Mile','2017-01-15','2017-01-15',0,0,'Sto volis disko?',0,0,12312,123123,10,10),(21,' Bravo',' Mravo','2017-01-15','2017-01-15',0,0,'asd',0,0,12,12,10,10),(22,' Bravo',' Mravo','2017-01-15','2017-01-15',0,0,'asd',0,0,12,12,10,10),(23,' Bravo',' Mravo','2017-01-15','2017-01-15',0,0,'asd',0,0,12,12,10,10),(24,'AS','asd','2017-01-15','2017-01-15',0,0,'asd',0,0,12,12,10,10),(25,'Dragane mooooj','asd','2017-01-15','2017-01-15',0,0,'asd',1,0,2,3,10,10),(26,' Bravo',' Mravo','2017-01-15','2017-01-15',0,0,'asd',0,0,12,12,10,10),(27,'asdaaaa','asd','2017-01-15','2017-01-15',0,0,'asd',1,0,12,12,10,10),(28,'Srpska Nova Godina','Trg Republike','2017-01-12','2017-01-13',0,0,'Ziveli',0,0,0,0,10,10),(29,'sdfsdfsdf','asd','2017-01-15','2017-01-15',0,0,'asd',0,0,1212121,12,10,10),(30,'Najlepsi festival','Jagodini','2017-01-19','2017-01-20',0,0,'asd',1,0,12,12,10,10),(31,'Beer Fest','Beograd','2016-08-23','2016-08-28',7.4,56,'asd',0,56,12,56,10,10),(32,'Beer Fest','Beograd','2015-08-23','2015-08-28',4.2,12,'asd',0,12,12,56,10,10),(33,'Beer Festasd','Belgrade, Usce','2017-08-21','2017-08-25',0,0,'Bla bla',0,0,800,3000,10,10);
/*!40000 ALTER TABLE `festival` ENABLE KEYS */;
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
