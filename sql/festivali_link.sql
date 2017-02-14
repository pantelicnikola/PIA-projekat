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
-- Table structure for table `link`
--

DROP TABLE IF EXISTS `link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `link` (
  `IdFestival` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Link` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`IdFestival`,`Name`),
  CONSTRAINT `IdFestivalLink` FOREIGN KEY (`IdFestival`) REFERENCES `festival` (`IdFestival`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `link`
--

LOCK TABLES `link` WRITE;
/*!40000 ALTER TABLE `link` DISABLE KEYS */;
INSERT INTO `link` VALUES (3,'Facebook','https://www.facebook.com/belgradebeerfest/?fr'),(21,'facebook','asd'),(21,'instagram','asd'),(21,'twitter','asd'),(21,'youtube','asd'),(22,'facebook','asd'),(22,'instagram','asd'),(22,'twitter','sd'),(22,'youtube','asd'),(29,'twitter','laksdlaksjdlakdjlakjdla'),(29,'youtube','lakjsdlaksjdlaksjdlaksjd'),(30,'facebook','facebook.com'),(30,'twitter','twitter.com'),(30,'youtube','youtube.com'),(33,'Facebook','https://www.facebook.com/belgradebeerfest'),(33,'Instagram','http://instagram.com/belgradebeerfest'),(33,'Twitter','http://twitter.com/bgbeerfest'),(33,'YouTube','http://www.youtube.com/Belgradebeerfest');
/*!40000 ALTER TABLE `link` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-26 14:17:58
