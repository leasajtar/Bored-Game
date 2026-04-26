/*
SQLyog Community v13.3.1 (64 bit)
MySQL - 8.0.45 : Database - boredgame
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`boredgame` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `boredgame`;

/*Table structure for table `cafes` */

DROP TABLE IF EXISTS `cafes`;

CREATE TABLE `cafes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `house_number` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `cafes` */

insert  into `cafes`(`id`,`name`,`city`,`house_number`,`street`) values 
(21,'Biljarski klub Fortius','Zagreb','32','ul. Hrvatskog proljeća'),
(22,'Cat Caffe','Zagreb','6','ul. Dragojla Kušlana'),
(23,'Crni Mačak','Zagreb','12','Mesnička ul.'),
(24,'Carta Magica','Zagreb','20','Frankopanska ul.'),
(25,'Raspjevane kokoši','Zagreb','4','Poljička ul.'),
(26,'Magic Omens','Zagreb','144A','Savska cesta'),
(27,'Drito bar','Zagreb','180','Savska cesta'),
(28,'Dive In','Zagreb','23a','Horvaćanska cesta');

/*Table structure for table `events` */

DROP TABLE IF EXISTS `events`;

CREATE TABLE `events` (
  `id` int NOT NULL AUTO_INCREMENT,
  `event_datetime` datetime(6) DEFAULT NULL,
  `game_name` varchar(255) DEFAULT NULL,
  `max_players` int DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `cafe_id` int DEFAULT NULL,
  `organizer_id` int DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi7wrc3qwbbmupsv60ft6a45oi` (`cafe_id`),
  KEY `FKdocju8m76a3f8o6ljh2jrn2ra` (`organizer_id`),
  CONSTRAINT `FKdocju8m76a3f8o6ljh2jrn2ra` FOREIGN KEY (`organizer_id`) REFERENCES `users` (`userID`),
  CONSTRAINT `FKi7wrc3qwbbmupsv60ft6a45oi` FOREIGN KEY (`cafe_id`) REFERENCES `cafes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `events` */

insert  into `events`(`id`,`event_datetime`,`game_name`,`max_players`,`status`,`cafe_id`,`organizer_id`,`level`) values 
(52,'2026-05-10 18:00:00.000000','Catan',4,'OPEN',23,2,'2'),
(53,'2026-05-11 19:30:00.000000','Uno',8,'OPEN',22,5,'1'),
(54,'2026-05-12 17:00:00.000000','Poker',6,'OPEN',21,7,'3'),
(55,'2026-05-13 20:00:00.000000','Scrabble',4,'OPEN',24,10,'2'),
(56,'2026-05-14 18:30:00.000000','Exploding Kittens',5,'OPEN',26,12,'1');

/*Table structure for table `joinings` */

DROP TABLE IF EXISTS `joinings`;

CREATE TABLE `joinings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `event_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgxlhgukacrhprvyg8vh7d8no` (`event_id`),
  KEY `FKk79q6rmx6etormod7r9oebkij` (`user_id`),
  CONSTRAINT `FKgxlhgukacrhprvyg8vh7d8no` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`),
  CONSTRAINT `FKk79q6rmx6etormod7r9oebkij` FOREIGN KEY (`user_id`) REFERENCES `users` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `joinings` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `ime` varchar(255) DEFAULT NULL,
  `prezime` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `gender` int NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `users` */

insert  into `users`(`userID`,`username`,`ime`,`prezime`,`pass`,`email`,`phone`,`gender`) values 
(1,'marko92','Marko','Horvat','pass123','marko.horvat@gmail.com','0911234567',1),
(2,'ana_k','Ana','Kovačić','ana123','ana.kovacic@gmail.com','0922345678',0),
(3,'ivan_zg','Ivan','Babić','ivanpass','ivan.babic@gmail.com','0953456789',1),
(4,'petra99','Petra','Marić','petra99','petra.maric@gmail.com','0974567890',0),
(5,'lukasplit','Luka','Novak','luka321','luka.novak@gmail.com','0985678901',1),
(6,'maja_r','Maja','Perić','majapass','maja.peric@gmail.com','0996789012',0),
(7,'dario88','Dario','Knežević','dario88','dario.knezevic@gmail.com','0917890123',1),
(8,'tea_ri','Tea','Jurić','teapass','tea.juric@gmail.com','0928901234',0),
(9,'filip_os','Filip','Božić','filip123','filip.bozic@gmail.com','0959012345',1),
(10,'iva_zd','Iva','Vuković','iva321','iva.vukovic@gmail.com','0970123456',0),
(12,'Side_hOwO','Lea','Sajtar','password','lea.sajtar@gmail.com','',0),
(13,'leadinic','Lea','Dinic','leapassword','ldjinic@gmail.com','',0),
(14,'username','Jo','Do','password','email@email.email','',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
