/*
 Navicat Premium Data Transfer

 Source Server         : localhostM
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : countrycity

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 22/02/2020 19:16:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city_name` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `country_code` char(2) COLLATE utf8_unicode_ci DEFAULT NULL,
  `enabled` bit(1) NOT NULL DEFAULT b'0',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `fk_city_4_country` (`country_code`),
  CONSTRAINT `fk_city_4_country` FOREIGN KEY (`country_code`) REFERENCES `country` (`iso_code`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of city
-- ----------------------------
BEGIN;
INSERT INTO `city` VALUES (1, 'Paris', 'FR', b'1', b'0');
INSERT INTO `city` VALUES (2, 'London', 'GB', b'1', b'0');
INSERT INTO `city` VALUES (3, 'New York', 'US', b'1', b'0');
INSERT INTO `city` VALUES (4, 'Berlin', 'DE', b'1', b'0');
INSERT INTO `city` VALUES (5, 'Istanbul', 'TR', b'1', b'0');
INSERT INTO `city` VALUES (6, 'Brussels', 'BE', b'1', b'0');
INSERT INTO `city` VALUES (7, 'Brasilia', 'BR', b'1', b'0');
INSERT INTO `city` VALUES (8, 'Roma', 'IT', b'1', b'0');
INSERT INTO `city` VALUES (9, 'Bursa', 'TR', b'1', b'0');
INSERT INTO `city` VALUES (10, 'Oslo', 'NO', b'1', b'0');
INSERT INTO `city` VALUES (11, 'Copenhagen', 'DK', b'1', b'0');
INSERT INTO `city` VALUES (12, 'Madrid', 'ES', b'1', b'0');
COMMIT;

-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `iso_code` char(2) COLLATE utf8_unicode_ci NOT NULL,
  `country_name` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `enabled` bit(1) NOT NULL DEFAULT b'1',
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`iso_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of country
-- ----------------------------
BEGIN;
INSERT INTO `country` VALUES ('AT', 'Austria', b'1', b'0');
INSERT INTO `country` VALUES ('AU', 'Australia', b'1', b'0');
INSERT INTO `country` VALUES ('BE', 'Belgium', b'1', b'0');
INSERT INTO `country` VALUES ('BR', 'Brazil', b'1', b'0');
INSERT INTO `country` VALUES ('DE', 'Germany', b'0', b'0');
INSERT INTO `country` VALUES ('DK', 'Denmark', b'1', b'0');
INSERT INTO `country` VALUES ('ES', 'Spain', b'1', b'0');
INSERT INTO `country` VALUES ('FR', 'France', b'1', b'0');
INSERT INTO `country` VALUES ('GB', 'United Kingdom', b'1', b'0');
INSERT INTO `country` VALUES ('IT', 'Italy', b'1', b'0');
INSERT INTO `country` VALUES ('LU', 'Luxembourg', b'1', b'0');
INSERT INTO `country` VALUES ('N4', 'Neverland', b'1', b'0');
INSERT INTO `country` VALUES ('NL', 'Netherlands', b'1', b'0');
INSERT INTO `country` VALUES ('NO', 'Norway', b'1', b'0');
INSERT INTO `country` VALUES ('PT', 'Portugal', b'0', b'0');
INSERT INTO `country` VALUES ('RO', 'Romania', b'1', b'0');
INSERT INTO `country` VALUES ('SE', 'Sweden', b'1', b'0');
INSERT INTO `country` VALUES ('TR', 'Turkey', b'1', b'0');
INSERT INTO `country` VALUES ('US', 'United States of America', b'1', b'0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
