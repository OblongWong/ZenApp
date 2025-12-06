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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `profile_icon` varchar(255) DEFAULT NULL,
  `role` enum('USER','ADMIN') DEFAULT 'USER',
  `total_points` int DEFAULT '0',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `last_login` timestamp NULL DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`),
  KEY `idx_user_role` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin@mindfulness.com','admin123','System Admin Name Change Test','?','ADMIN',865,'2025-11-20 17:29:54','2025-11-29 15:44:51',1),(2,'john','john@email.com','password123','John Doe','?','USER',10,'2025-11-22 15:30:30','2025-11-22 15:30:43',1),(3,'Iroh','IrohSpyro@gmail.com','Iroh123','Iroh Spyro','?','USER',10,'2025-11-25 15:15:41','2025-11-25 21:50:53',1),(4,'zenmaster2024','emma.thompson@testmail.com','Zen@2024!','Emma Thompson',NULL,'USER',725,'2025-07-15 12:30:00','2025-11-27 13:15:00',1),(5,'mindful_warrior','david.chen@testmail.com','Warrior#123','David Chen',NULL,'USER',680,'2025-08-01 14:00:00','2025-11-27 22:45:00',1),(6,'peacelover88','sarah.williams@testmail.com','Peace*88','Sarah Williams',NULL,'USER',620,'2025-07-20 18:20:00','2025-11-27 11:30:00',1),(7,'yoga_journey','jessica.patel@testmail.com','Yoga@Journey','Jessica Patel',NULL,'USER',595,'2025-08-10 11:00:00','2025-11-27 10:00:00',1),(8,'inner_calm_pro','steven.clark@testmail.com','Calm$Pro99','Steven Clark',NULL,'USER',710,'2025-07-25 12:15:00','2025-11-27 12:30:00',1),(9,'breath_easy','mike.rodriguez@testmail.com','EasyBreath1','Michael Rodriguez',NULL,'USER',385,'2025-09-15 13:00:00','2025-11-28 00:00:00',1),(10,'serenity_now','catherine.lee@testmail.com','Serenity!Now','Catherine Lee',NULL,'USER',295,'2025-10-01 15:30:00','2025-11-27 16:00:00',1),(11,'daily_mediator','maria.martinez@testmail.com','Daily#Med22','Maria Martinez',NULL,'USER',420,'2025-09-05 12:00:00','2025-11-27 12:00:00',1),(12,'harmony_seeker','hannah.brown@testmail.com','Harmony@Seek','Hannah Brown',NULL,'USER',340,'2025-09-28 16:00:00','2025-11-27 15:00:00',1),(13,'focus_first','kevin.obrien@testmail.com','Focus1st!','Kevin O\'Brien',NULL,'USER',365,'2025-10-08 15:00:00','2025-11-27 17:30:00',1),(14,'mindful_mom','grace.murphy@testmail.com','MomMind#2024','Grace Murphy',NULL,'USER',275,'2025-10-12 14:30:00','2025-11-27 20:00:00',1),(15,'calm_commuter','chris.lee@testmail.com','Commute@Calm','Christopher Lee',NULL,'USER',310,'2025-10-05 18:30:00','2025-11-27 21:00:00',1),(16,'zen_student','jennifer.davis@testmail.com','Student!Zen','Jennifer Davis',NULL,'USER',255,'2025-10-18 13:45:00','2025-11-27 18:30:00',1),(17,'peaceful_pro','omar.hassan@testmail.com','ProPeace#88','Omar Hassan',NULL,'USER',390,'2025-09-22 13:30:00','2025-11-27 23:00:00',1),(18,'balance_life','alex.wong@testmail.com','Life@Balance','Alexander Wong',NULL,'USER',405,'2025-09-18 11:45:00','2025-11-27 11:00:00',1),(19,'just_started','robert.johnson@testmail.com','Start2024!','Robert Johnson',NULL,'USER',125,'2025-11-01 20:45:00','2025-11-26 14:30:00',1),(20,'curious_mind','daniel.kim@testmail.com','Curious@Mind','Daniel Kim',NULL,'USER',95,'2025-11-08 14:00:00','2025-11-26 18:00:00',1),(21,'trying_this','betty.taylor@testmail.com','TryThis#123','Elizabeth Taylor',NULL,'USER',110,'2025-11-05 19:30:00','2025-11-27 14:00:00',1),(22,'new_to_zen','peter.anderson@testmail.com','NewZen!2024','Peter Anderson',NULL,'USER',80,'2025-11-12 13:00:00','2025-11-26 22:00:00',1),(23,'evening_peace','rachel.green@testmail.com','Evening@Peace','Rachel Green',NULL,'USER',135,'2025-10-25 17:00:00','2025-11-27 16:30:00',1),(24,'weekend_zen','amy.robinson@testmail.com','Weekend#Zen','Amy Robinson',NULL,'USER',70,'2025-11-15 15:00:00','2025-11-25 19:00:00',1),(25,'stress_relief','will.jackson@testmail.com','Relief@Stress','William Jackson',NULL,'USER',145,'2025-10-30 16:15:00','2025-11-27 13:30:00',1),(26,'lunch_break','lisa.moore@testmail.com','LunchBreak1','Lisa Moore',NULL,'USER',90,'2025-11-10 18:00:00','2025-11-26 16:00:00',1),(27,'bedtime_calm','james.white@testmail.com','Bedtime@Calm','James White',NULL,'USER',120,'2025-11-07 00:00:00','2025-11-28 01:00:00',1),(28,'morning_ritual','sophia.garcia@testmail.com','Morning!Ritual','Sophia Garcia',NULL,'USER',105,'2025-11-09 11:00:00','2025-11-27 11:15:00',1),(29,'just_browsing','nathan.thomas@testmail.com','Browse2024','Nathan Thomas',NULL,'USER',15,'2025-11-20 20:00:00','2025-11-21 14:00:00',1),(30,'checking_out','olivia.martin@testmail.com','CheckOut#1','Olivia Martin',NULL,'USER',0,'2025-11-22 18:30:00','2025-11-22 18:35:00',1),(31,'first_timer','lucas.harris@testmail.com','First@Timer','Lucas Harris',NULL,'USER',25,'2025-11-18 15:00:00','2025-11-23 19:00:00',1),(32,'test_account','mia.thompson@testmail.com','TestAcc#123','Mia Thompson',NULL,'USER',10,'2025-11-24 13:00:00','2025-11-24 13:15:00',1),(33,'maybe_later','ethan.wilson@testmail.com','Later@Maybe','Ethan Wilson',NULL,'USER',0,'2025-11-25 22:00:00','2025-11-25 22:05:00',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
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
