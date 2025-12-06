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
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `difficulty_level` enum('BEGINNER','INTERMEDIATE','ADVANCED') DEFAULT NULL,
  `points_reward` int DEFAULT '10',
  `estimated_duration` int DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `is_active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`course_id`),
  KEY `idx_course_category` (`category`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Foundations of Mindfulness','Essential mindfulness meditation practices teaching present-moment awareness with openness and non-judgment.','Meditation','BEGINNER',120,60,NULL,'2025-11-29 00:13:19',1),(2,'Sleep and Relaxation','Evidence-based mindfulness practices to improve sleep quality and promote deep relaxation.','Sleep','BEGINNER',120,50,NULL,'2025-11-29 00:13:19',1),(3,'Anxiety Management','Mindfulness and anxiety through mindfulness practices that reduce reactivity.','Mental Health','BEGINNER',120,55,NULL,'2025-11-29 00:13:19',1),(4,'Stress Relief and Resilience','Comprehensive tools to manage acute stress and build long-term psychological resilience.','Stress Management','INTERMEDIATE',120,65,NULL,'2025-11-29 00:13:19',1),(5,'Self-Love and Self-Compassion','Treat yourself with the same kindness you would offer a good friend.','Self-Care','INTERMEDIATE',120,70,NULL,'2025-11-29 00:13:19',1),(6,'Morning Mindfulness Rituals','Establish energizing morning mindfulness practices that set a positive tone for the day.','Daily Practice','BEGINNER',120,55,NULL,'2025-11-29 00:13:19',1),(7,'Emotional Regulation','Work skillfully with anger, frustration, and other difficult emotions through mindfulness.','Emotional Wellness','INTERMEDIATE',120,60,NULL,'2025-11-29 00:13:19',1),(8,'Breathing Techniques','Comprehensive training in pranayama and modern breathing techniques.','Breathing','BEGINNER',120,60,NULL,'2025-11-29 00:13:19',1),(9,'Body Awareness and Grounding','Develop interoceptive awareness through body scan meditation and grounding techniques.','Body-Mind Connection','INTERMEDIATE',120,65,NULL,'2025-11-29 00:13:19',1),(10,'Healing and Recovery','Gentle, trauma-informed mindfulness practices for navigating difficult life experiences.','Healing','INTERMEDIATE',120,70,NULL,'2025-11-29 00:13:19',1);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
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
