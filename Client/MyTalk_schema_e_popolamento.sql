-- MySQL dump 10.13  Distrib 5.5.29, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: MyTalk
-- ------------------------------------------------------
-- Server version	5.5.29-0ubuntu0.12.10.1

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
-- Table structure for table `Blacklists`
--

DROP TABLE IF EXISTS `Blacklists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Blacklists` (
  `owner` varchar(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`owner`,`username`),
  KEY `username` (`username`),
  CONSTRAINT `Blacklists_ibfk_1` FOREIGN KEY (`username`) REFERENCES `Users` (`username`) ON DELETE CASCADE,
  CONSTRAINT `Blacklists_ibfk_2` FOREIGN KEY (`owner`) REFERENCES `Users` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Blacklists`
--

LOCK TABLES `Blacklists` WRITE;
/*!40000 ALTER TABLE `Blacklists` DISABLE KEYS */;
INSERT INTO `Blacklists` VALUES ('user2','user0'),('user9','user0'),('user8','user1'),('user7','user2'),('user6','user3'),('user5','user4'),('user4','user5'),('user3','user6'),('user2','user7'),('user1','user8'),('user0','user9');
/*!40000 ALTER TABLE `Blacklists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Calls`
--

DROP TABLE IF EXISTS `Calls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Calls` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `caller` varchar(20) DEFAULT NULL,
  `receiver` varchar(20) DEFAULT NULL,
  `duration` int(10) NOT NULL,
  `startdate` varchar(20) NOT NULL,
  `byteSent` int(10) NOT NULL,
  `byteReceived` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `caller` (`caller`),
  KEY `receiver` (`receiver`),
  CONSTRAINT `Calls_ibfk_1` FOREIGN KEY (`caller`) REFERENCES `Users` (`username`) ON DELETE SET NULL,
  CONSTRAINT `Calls_ibfk_2` FOREIGN KEY (`receiver`) REFERENCES `Users` (`username`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Calls`
--

LOCK TABLES `Calls` WRITE;
/*!40000 ALTER TABLE `Calls` DISABLE KEYS */;
INSERT INTO `Calls` VALUES (1,'user0','user1',600,'02-02-2013 16:44:56',10000,10000),(2,'user0','user1',600,'02-02-2013 10:53:32',10000,10000),(3,'user1','user0',500,'06-02-2013 15:03:22',50000,40000),(4,'user0','user2',700,'06-02-2013 11:31:41',80000,75000);
/*!40000 ALTER TABLE `Calls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ListNames`
--

DROP TABLE IF EXISTS `ListNames`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ListNames` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `owner` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `owner` (`owner`),
  CONSTRAINT `ListNames_ibfk_1` FOREIGN KEY (`owner`) REFERENCES `Users` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ListNames`
--

LOCK TABLES `ListNames` WRITE;
/*!40000 ALTER TABLE `ListNames` DISABLE KEYS */;
INSERT INTO `ListNames` VALUES (2,'friends','user1'),(3,'friends','user2'),(4,'friends','user0'),(5,'nuovaLista','user0');
/*!40000 ALTER TABLE `ListNames` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OnlineUsers`
--

DROP TABLE IF EXISTS `OnlineUsers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OnlineUsers` (
  `ip` varchar(40) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ip`),
  KEY `username` (`username`),
  CONSTRAINT `OnlineUsers_ibfk_1` FOREIGN KEY (`username`) REFERENCES `Users` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OnlineUsers`
--

LOCK TABLES `OnlineUsers` WRITE;
/*!40000 ALTER TABLE `OnlineUsers` DISABLE KEYS */;
INSERT INTO `OnlineUsers` VALUES ('127.0.0.1:56921','user0');
/*!40000 ALTER TABLE `OnlineUsers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserLists`
--

DROP TABLE IF EXISTS `UserLists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserLists` (
  `idList` int(9) NOT NULL,
  `username` varchar(20) NOT NULL,
  PRIMARY KEY (`idList`,`username`),
  KEY `username` (`username`),
  CONSTRAINT `UserLists_ibfk_1` FOREIGN KEY (`idList`) REFERENCES `ListNames` (`id`) ON DELETE CASCADE,
  CONSTRAINT `UserLists_ibfk_2` FOREIGN KEY (`username`) REFERENCES `Users` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserLists`
--

LOCK TABLES `UserLists` WRITE;
/*!40000 ALTER TABLE `UserLists` DISABLE KEYS */;
INSERT INTO `UserLists` VALUES (2,'user0'),(4,'user1'),(5,'user1'),(2,'user2'),(4,'user2'),(2,'user3'),(4,'user3'),(2,'user4');
/*!40000 ALTER TABLE `UserLists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `username` varchar(20) NOT NULL,
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `password` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `emailhash` varchar(32) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('user0',1,'61149c31d4900d53c7586e9e999feee3','Mattia','Agostinetto','agomat@gmail.com','699b1b3f9c23e21f13b2ac9267942b01'),('user1',2,'24c9e15e52afc47c225b757e7bee1f9d','user1','user1','user1@mytalk.com','emailhash123123123'),('user2',3,'7e58d63b60197ceb55a1c487989a3720','user2','user2','user2@mytalk.com','emailhash123123123'),('user3',4,'92877af70a45fd6a2ed7fe81e1236b78','user3','user3','user3@mytalk.com','emailhash123123123'),('user4',5,'3f02ebe3d7929b091e3d8ccfde2f3bc6','user4','user4','user4@mytalk.com','emailhash123123123'),('user5',6,'a791842f52a0acfbb3a783378c066b8','user5','user5','user5@mytalk.com','emailhash123123123'),('user6',7,'affec3b64cf90492377a8114c86fc093','user6','user6','user6@mytalk.com','emailhash123123123'),('user7',8,'3e0469fb134991f8f75a2760e409c6ed','user7','user7','user7@mytalk.com','emailhash123123123'),('user8',9,'7668f673d5669995175ef91b5d171945','user8','user8','user8@mytalk.com','emailhash123123123'),('user9',10,'8808a13b854c2563da1a5f6cb2130868','user9','user9','user9@mytalk.com','emailhash123123123');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-05-27 20:54:38
