-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: mindfulness_app
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `user_streaks`
--

DROP TABLE IF EXISTS `user_streaks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_streaks` (
  `streak_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `current_streak` int DEFAULT '0',
  `longest_streak` int DEFAULT '0',
  `last_activity_date` date DEFAULT NULL,
  PRIMARY KEY (`streak_id`),
  UNIQUE KEY `unique_user_streak` (`user_id`),
  CONSTRAINT `user_streaks_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_streaks`
--

LOCK TABLES `user_streaks` WRITE;
/*!40000 ALTER TABLE `user_streaks` DISABLE KEYS */;
INSERT INTO `user_streaks` VALUES (1,2,1,1,'2025-11-24'),(2,3,1,1,'2025-11-25'),(3,1,3,3,'2025-11-29'),(4,4,28,30,'2025-11-27'),(5,5,25,25,'2025-11-27'),(6,6,22,24,'2025-11-27'),(7,7,30,30,'2025-11-27'),(8,8,27,28,'2025-11-27'),(9,9,14,15,'2025-11-27'),(10,10,10,12,'2025-11-27'),(11,11,15,16,'2025-11-27'),(12,12,12,14,'2025-11-27'),(13,13,11,13,'2025-11-27'),(14,14,9,11,'2025-11-27'),(15,15,13,14,'2025-11-27'),(16,16,8,10,'2025-11-27'),(17,17,14,16,'2025-11-27'),(18,18,12,15,'2025-11-27'),(19,19,5,6,'2025-11-26'),(20,20,3,4,'2025-11-26'),(21,21,6,7,'2025-11-27'),(22,22,4,5,'2025-11-26'),(23,23,7,8,'2025-11-27'),(24,24,3,4,'2025-11-25'),(25,25,6,7,'2025-11-27'),(26,26,4,5,'2025-11-26'),(27,27,5,6,'2025-11-27'),(28,28,7,8,'2025-11-27'),(29,29,0,1,'2025-11-21'),(30,30,0,0,NULL),(31,31,0,2,'2025-11-23'),(32,32,0,1,'2025-11-24'),(33,33,0,0,NULL);
/*!40000 ALTER TABLE `user_streaks` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-29 11:48:54
