CREATE DATABASE  IF NOT EXISTS `gma` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gma`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: gma
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Username_UNIQUE` (`Username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','password');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answer` (
  `EntryId` int NOT NULL,
  `QuestionId` int NOT NULL,
  `Answer` varchar(200) NOT NULL,
  PRIMARY KEY (`EntryId`,`QuestionId`),
  KEY `EntryFK_idx` (`EntryId`),
  KEY `QuestionFK_idx` (`QuestionId`),
  CONSTRAINT `fk_answer_entry_entryid` FOREIGN KEY (`EntryId`) REFERENCES `entry` (`Id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_answer_question_questionid` FOREIGN KEY (`QuestionId`) REFERENCES `question` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`dev`@`localhost`*/ /*!50003 TRIGGER `answer_BEFORE_INSERT` BEFORE INSERT ON `answer` FOR EACH ROW BEGIN
    IF (SELECT COUNT(*) FROM `offensiveword` WHERE LOWER(NEW.Answer) REGEXP CONCAT('\\b', LOWER(word), '\\b')) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Offensive word detected!';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`dev`@`localhost`*/ /*!50003 TRIGGER `answer_AFTER_INSERT` AFTER INSERT ON `answer` FOR EACH ROW BEGIN
    UPDATE `entry` e
    SET e.Points = e.Points + 1
    WHERE e.Id = NEW.EntryId;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `entry`
--

DROP TABLE IF EXISTS `entry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entry` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `UserId` int NOT NULL,
  `QuestionnaireId` int NOT NULL,
  `Points` int NOT NULL DEFAULT '0',
  `IsSubmitted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`),
  KEY `QuestionnaireFK2_idx` (`QuestionnaireId`),
  KEY `UserFK_idx` (`UserId`),
  CONSTRAINT `fk_entry_questionnaire_questionnaireid` FOREIGN KEY (`QuestionnaireId`) REFERENCES `questionnaire` (`Id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_entry_user_userid` FOREIGN KEY (`UserId`) REFERENCES `user` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entry`
--

LOCK TABLES `entry` WRITE;
/*!40000 ALTER TABLE `entry` DISABLE KEYS */;
/*!40000 ALTER TABLE `entry` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`dev`@`localhost`*/ /*!50003 TRIGGER `entry_AFTER_UPDATE` AFTER UPDATE ON `entry` FOR EACH ROW BEGIN
    IF NEW.Points <> OLD.Points THEN
		UPDATE `user` u
		SET u.Points = u.Points - OLD.Points + NEW.Points
		WHERE u.Id = NEW.UserId;
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`dev`@`localhost`*/ /*!50003 TRIGGER `entry_AFTER_DELETE` AFTER DELETE ON `entry` FOR EACH ROW BEGIN
	UPDATE `user` u
		SET u.Points = u.Points - OLD.Points
		WHERE u.Id = OLD.UserId
			AND OLD.IsSubmitted = 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `loginlog`
--

DROP TABLE IF EXISTS `loginlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loginlog` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `UserId` int NOT NULL,
  `LogTime` datetime NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_loginlog_user_userid_idx` (`UserId`),
  CONSTRAINT `fk_loginlog_user_userid` FOREIGN KEY (`UserId`) REFERENCES `user` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loginlog`
--

LOCK TABLES `loginlog` WRITE;
/*!40000 ALTER TABLE `loginlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `loginlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offensiveword`
--

DROP TABLE IF EXISTS `offensiveword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offensiveword` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Word` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Word_UNIQUE` (`Word`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offensiveword`
--

LOCK TABLES `offensiveword` WRITE;
/*!40000 ALTER TABLE `offensiveword` DISABLE KEYS */;
INSERT INTO `offensiveword` VALUES (1,'apple'),(3,'banana'),(8,'blackberry'),(7,'blueberry'),(9,'kiwi'),(2,'orange'),(5,'peach'),(4,'pear'),(10,'pineapple'),(6,'strawberry');
/*!40000 ALTER TABLE `offensiveword` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Image` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Name_UNIQUE` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Razor','razor.png'),(2,'Foam','foam.png'),(3,'Brush','brush.png');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `QuestionnaireId` int NOT NULL,
  `Question` varchar(200) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `QuestionnaireFK_idx` (`QuestionnaireId`),
  CONSTRAINT `fk_question_questionnaire_questionnaireid` FOREIGN KEY (`QuestionnaireId`) REFERENCES `questionnaire` (`Id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionnaire`
--

DROP TABLE IF EXISTS `questionnaire`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questionnaire` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `ProductId` int NOT NULL,
  `Date` date NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Date_UNIQUE` (`Date`),
  KEY `ProductFK2_idx` (`ProductId`),
  CONSTRAINT `fk_questionnaire_product_productid` FOREIGN KEY (`ProductId`) REFERENCES `product` (`Id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionnaire`
--

LOCK TABLES `questionnaire` WRITE;
/*!40000 ALTER TABLE `questionnaire` DISABLE KEYS */;
/*!40000 ALTER TABLE `questionnaire` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `ProductId` int NOT NULL,
  `UserId` int NOT NULL,
  `Review` varchar(200) NOT NULL,
  PRIMARY KEY (`ProductId`,`UserId`),
  KEY `UserFK_idx` (`UserId`),
  CONSTRAINT `fk_review_product_productid` FOREIGN KEY (`ProductId`) REFERENCES `product` (`Id`),
  CONSTRAINT `fk_review_user_userid` FOREIGN KEY (`UserId`) REFERENCES `user` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,1,'What a great razor. I was buying Harry???s Razors before, however, his blades are attached to the blade cartridge with rubber, which began to stretch in a few weeks. Great product!'),(1,2,'The Best razor brand around in my opinion, I will not use anything else, you can always depend on a close, nick free shave. I would highly recommend this set, great price and excellent performance.'),(2,1,'Been using this for a few weeks. It comes out a little too fast. But I love it. I trim a permanent 5 o\'clock shadow, so I only shave twice a week, in the shower.'),(3,2,'This device makes it much easier to apply shaving cream to my face. I also have noticed that I use half as much shaving cream than before.');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stats`
--

DROP TABLE IF EXISTS `stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stats` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Age` int DEFAULT NULL,
  `Sex` enum('FEMALE','MALE','OTHER') DEFAULT NULL,
  `ExpertiseLevel` enum('LOW','MEDIUM','HIGH') DEFAULT NULL,
  `EntryId` int NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_stats_entry_entryid_idx` (`EntryId`),
  CONSTRAINT `fk_stats_entry_entryid` FOREIGN KEY (`EntryId`) REFERENCES `entry` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stats`
--

LOCK TABLES `stats` WRITE;
/*!40000 ALTER TABLE `stats` DISABLE KEYS */;
/*!40000 ALTER TABLE `stats` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`dev`@`localhost`*/ /*!50003 TRIGGER `stats_AFTER_INSERT` AFTER INSERT ON `stats` FOR EACH ROW BEGIN
    UPDATE `entry` e
    SET e.Points = e.Points + 2 * ((NOT ISNULL(NEW.Age)) + (NOT ISNULL(NEW.Sex)) + (NOT ISNULL(NEW.ExpertiseLevel)))
    WHERE e.Id = NEW.EntryId;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Email` varchar(90) NOT NULL,
  `Points` int NOT NULL DEFAULT '0',
  `IsBlocked` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Username_UNIQUE` (`Username`),
  UNIQUE KEY `Email_UNIQUE` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'user1','password','user1@example.org',0,0),(2,'user2','password','user2@example.org',0,0);
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

-- Dump completed on 2021-04-09 23:02:12
