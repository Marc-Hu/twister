-- MySQL dump 10.13  Distrib 5.5.58, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: twister
-- ------------------------------------------------------
-- Server version	5.5.58-0+deb8u1

DROP DATABASE IF EXISTS `twister`;
CREATE DATABASE twister;
USE twister;
--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `id_user` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `last_name` varchar(30) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(64) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `Connection`
--

DROP TABLE IF EXISTS `Connection`;

CREATE TABLE `Connection` (
  `keycnx` varchar(32) NOT NULL,
  `id` int(11) unsigned NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `root` tinyint(1) NOT NULL,
  PRIMARY KEY (`keycnx`),
  UNIQUE KEY `idx_id` (`id`),
  KEY `idx_connection` (`keycnx`,`id`,`timestamp`),
  CONSTRAINT `fk_cnx_id` FOREIGN KEY (`id`) REFERENCES `User` (`id_user`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `Friends`
--

DROP TABLE IF EXISTS `Friends`;

CREATE TABLE `Friends` (
  `from_id` int(11) unsigned NOT NULL,
  `to_id` int(11) unsigned NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`from_id`,`to_id`),
  KEY `fk_to_id` (`to_id`),
  CONSTRAINT `fk_to_id` FOREIGN KEY (`to_id`) REFERENCES `User` (`id_user`) ON DELETE CASCADE,
  CONSTRAINT `fk_from_id` FOREIGN KEY (`from_id`) REFERENCES `User` (`id_user`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `twister`.`user`
  ADD COLUMN `profile_pic` VARCHAR(45) NULL AFTER `password`;
