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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.comment: ~6 rows (approximately)
INSERT INTO `comment` (`id`, `user`, `post`, `content`, `timeline`) VALUES
	(1, 1, 4, 'is this joke?', '2024-07-22 12:09:42'),
	(2, 4, 1, 'nice', '2024-07-22 12:09:56'),
	(3, 2, 2, 'hey you', '2024-07-22 12:10:17'),
	(4, 3, 3, 'clapping', '2024-07-22 12:10:39'),
	(5, 1, 1, 'a', '2024-07-30 15:32:50'),
	(6, 1, 1, 'a', '2024-07-30 15:32:51'),
	(7, 1, 1, 'a', '2024-07-30 15:33:02');

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.friend: ~2 rows (approximately)
INSERT INTO `friend` (`id`, `user1`, `user2`, `timeline`, `isfriend`) VALUES
	(4, 1, 4, '2024-07-30 16:12:27', 1),
	(6, 5, 1, '2024-08-01 11:31:39', 1);

-- Dumping structure for table j2ee.friendrequest
CREATE TABLE IF NOT EXISTS `friendrequest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender` int(11) NOT NULL,
  `receiver` int(11) NOT NULL,
  `timeline` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `FK_friendrequest_user` (`sender`),
  KEY `FK_friendrequest_user_2` (`receiver`),
  CONSTRAINT `FK_friendrequest_user` FOREIGN KEY (`sender`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_friendrequest_user_2` FOREIGN KEY (`receiver`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.friendrequest: ~2 rows (approximately)
INSERT INTO `friendrequest` (`id`, `sender`, `receiver`, `timeline`) VALUES
	(8, 1, 3, '2024-07-31 15:46:18'),
	(11, 1, 2, '2024-08-01 11:31:37');

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
  `user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_message_user` (`sender`),
  KEY `FK_message_user_2` (`receiver`),
  KEY `FK_message_group` (`group`),
  KEY `FKl1p97aqbhtd6koo13simykye4` (`user`),
  CONSTRAINT `FK_message_group` FOREIGN KEY (`group`) REFERENCES `group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_message_user` FOREIGN KEY (`sender`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_message_user_2` FOREIGN KEY (`receiver`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKl1p97aqbhtd6koo13simykye4` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.post: ~4 rows (approximately)
INSERT INTO `post` (`id`, `user`, `content`, `image`, `timeline`) VALUES
	(1, 1, 'the first post', '63b8a620-b096-4707-a96b-0775e50b0aa1-guy.jpg', '2024-07-22 12:07:19'),
	(2, 4, 'hello world', 'fb14c842-445d-4bd3-b9f8-2a32f7121f75-Screenshot 2023-09-30 101609.png', '2024-07-22 12:07:39'),
	(3, 2, 'coding social project', '29137e04-fc02-451f-8f7f-e0ce58ea7aa6-bìa fb.jpg', '2024-07-22 12:08:03'),
	(4, 3, 'comedy', '66d3d95b-f38c-46ca-94d2-d5dc2584d2d1-instagram.png', '2024-07-22 12:08:13');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.reaction: ~5 rows (approximately)
INSERT INTO `reaction` (`id`, `user`, `post`, `timeline`) VALUES
	(1, 1, 3, '2024-07-22 12:08:46'),
	(2, 4, 1, '2024-07-22 12:08:56'),
	(3, 4, 2, '2024-07-22 12:09:02'),
	(4, 3, 4, '2024-07-22 12:09:10'),
	(5, 1, 1, '2024-07-26 14:02:17');

-- Dumping structure for table j2ee.story
CREATE TABLE IF NOT EXISTS `story` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL DEFAULT 0,
  `image` varchar(255) NOT NULL DEFAULT '0',
  `timeline` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `FK_story_user` (`user`),
  CONSTRAINT `FK_story_user` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.story: ~0 rows (approximately)

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table j2ee.user: ~4 rows (approximately)
INSERT INTO `user` (`name`, `id`, `birth`, `avt`, `phone`, `email`, `gender`, `desc`, `isonline`, `last_active`, `password`, `address`, `social`, `education`, `relationship`, `timejoin`) VALUES
	('Duong Thanh Truong', 1, '2003-01-27', '1ffb222a-41c5-4ed5-93f5-e3a4b68494e8-avt.jpg', '0386094783', 'aaaa@gmail.com', 'Nam', 'abc', 1, '2024-07-22 11:56:18', '12345678', 'abc', 'github.com/B', 'Sai Gon University', 'Độc thân', '2024-07-22 11:56:49'),
	('Pham Tan Dat', 2, '2003-07-22', 'pizzashop.png', '1234567890', 'abc', 'nam', NULL, 1, '2024-07-22 12:05:35', '12345678', NULL, NULL, 'Sai Gon University', NULL, '2024-07-22 12:05:48'),
	('Pham Van Du', 3, '2003-07-22', 'pizzashop.png', '1234567890', 'aaaa', 'nam', NULL, 1, '2024-07-22 12:06:15', '', NULL, NULL, '', NULL, '2024-07-22 12:06:19'),
	('Nguyen Nhat Truong', 4, '2003-07-22', 'pizzashop.png', '1234567890', NULL, 'nam', NULL, 1, '2024-07-22 12:06:36', '', NULL, NULL, 'Sai Gon University', NULL, '2024-07-22 12:06:44'),
	('Nguyen Van A', 5, '2024-07-30', NULL, NULL, NULL, NULL, NULL, 1, '2024-07-30 16:12:50', '', NULL, NULL, NULL, NULL, NULL);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
