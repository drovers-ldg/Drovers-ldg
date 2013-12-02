/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50614
Source Host           : localhost:3306
Source Database       : units

Target Server Type    : MYSQL
Target Server Version : 50614
File Encoding         : 65001

Date: 2013-11-30 15:05:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `squads`
-- ----------------------------
DROP TABLE IF EXISTS `squads`;
CREATE TABLE `squads` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `unit1` int(10) unsigned NOT NULL,
  `unit2` int(10) unsigned NOT NULL,
  `unit3` int(10) unsigned NOT NULL,
  `accountId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of squads
-- ----------------------------
INSERT INTO `squads` VALUES ('1', '1', '2', '3', '1');
INSERT INTO `squads` VALUES ('2', '4', '5', '6', '2');
INSERT INTO `squads` VALUES ('3', '7', '8', '9', '3');
INSERT INTO `squads` VALUES ('4', '10', '11', '12', '4');
