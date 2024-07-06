-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.27-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for j2ee
CREATE DATABASE IF NOT EXISTS `j2ee` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `j2ee`;

-- Dumping structure for table j2ee.comment
CREATE TABLE IF NOT EXISTS `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL DEFAULT 0,
  `post` int(11) NOT NULL DEFAULT 0,
  `content` varchar(255) NOT NULL DEFAULT '0',
  `timeline` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `FK_comment_user` (`user`),
  KEY `FK_comment_post` (`post`),
  CONSTRAINT `FK_comment_post` FOREIGN KEY (`post`) REFERENCES `post` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_comment_user` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.comment: ~0 rows (approximately)

-- Dumping structure for table j2ee.friend
CREATE TABLE IF NOT EXISTS `friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user1` int(11) NOT NULL,
  `user2` int(11) NOT NULL,
  `timeline` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `isfriend` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_friend_user` (`user1`),
  KEY `FK_friend_user_2` (`user2`),
  CONSTRAINT `FK_friend_user` FOREIGN KEY (`user1`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_friend_user_2` FOREIGN KEY (`user2`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.friend: ~0 rows (approximately)

-- Dumping structure for table j2ee.group
CREATE TABLE IF NOT EXISTS `group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '0',
  `admin` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_groupchat_user_2` (`admin`),
  CONSTRAINT `FK_groupchat_user_2` FOREIGN KEY (`admin`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.group: ~0 rows (approximately)

-- Dumping structure for table j2ee.joining
CREATE TABLE IF NOT EXISTS `joining` (
  `group` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `timeline` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`group`,`user`),
  KEY `FK_join_group_user` (`user`),
  CONSTRAINT `FK_join_group_groupchat` FOREIGN KEY (`group`) REFERENCES `group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_join_group_user` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.joining: ~0 rows (approximately)

-- Dumping structure for table j2ee.message
CREATE TABLE IF NOT EXISTS `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender` int(11) NOT NULL DEFAULT 0,
  `receiver` int(11) DEFAULT 0,
  `content` varchar(255) NOT NULL DEFAULT '0',
  `timeline` timestamp NOT NULL DEFAULT current_timestamp(),
  `group` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_message_user` (`sender`),
  KEY `FK_message_user_2` (`receiver`),
  KEY `FK_message_group` (`group`),
  CONSTRAINT `FK_message_group` FOREIGN KEY (`group`) REFERENCES `group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_message_user` FOREIGN KEY (`sender`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_message_user_2` FOREIGN KEY (`receiver`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.message: ~0 rows (approximately)

-- Dumping structure for table j2ee.notification
CREATE TABLE IF NOT EXISTS `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL DEFAULT 0,
  `content` varchar(255) NOT NULL DEFAULT '0',
  `is_read` int(11) NOT NULL DEFAULT 0,
  `timeline` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `FK_notification_user` (`user`),
  CONSTRAINT `FK_notification_user` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.notification: ~0 rows (approximately)

-- Dumping structure for table j2ee.post
CREATE TABLE IF NOT EXISTS `post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL DEFAULT 0,
  `content` varchar(255) DEFAULT '0',
  `image` varchar(255) DEFAULT '0',
  `timeline` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `FK_post_user` (`user`),
  CONSTRAINT `FK_post_user` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.post: ~0 rows (approximately)

-- Dumping structure for table j2ee.reaction
CREATE TABLE IF NOT EXISTS `reaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL DEFAULT 0,
  `post` int(11) NOT NULL DEFAULT 0,
  `timeline` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `FK_reaction_user` (`user`),
  KEY `FK_reaction_post` (`post`),
  CONSTRAINT `FK_reaction_post` FOREIGN KEY (`post`) REFERENCES `post` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_reaction_user` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.reaction: ~0 rows (approximately)

-- Dumping structure for table j2ee.user
CREATE TABLE IF NOT EXISTS `user` (
  `name` varchar(50) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `birth` date DEFAULT NULL,
  `avt` varchar(255) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `isonline` int(11) NOT NULL DEFAULT 1,
  `last_active` timestamp NOT NULL DEFAULT current_timestamp(),
  `password` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `social` varchar(255) DEFAULT NULL,
  `education` varchar(255) DEFAULT NULL,
  `relationship` varchar(255) DEFAULT NULL,
  `timejoin` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.user: ~0 rows (approximately)

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
