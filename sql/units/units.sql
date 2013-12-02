/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50614
Source Host           : localhost:3306
Source Database       : units

Target Server Type    : MYSQL
Target Server Version : 50614
File Encoding         : 65001

Date: 2013-11-30 15:05:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `units`
-- ----------------------------
DROP TABLE IF EXISTS `units`;
CREATE TABLE `units` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `player_id` int(10) NOT NULL,
  `areaX` int(10) NOT NULL DEFAULT '0',
  `areaY` int(10) NOT NULL DEFAULT '0',
  `bodyType` varchar(10) NOT NULL DEFAULT '0',
  `code_id` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of units
-- ----------------------------
INSERT INTO `units` VALUES ('1', '1', '0', '0', 'sturm', '1');
INSERT INTO `units` VALUES ('2', '1', '0', '0', 'scout', '2');
INSERT INTO `units` VALUES ('3', '1', '0', '0', 'art', '3');
INSERT INTO `units` VALUES ('4', '2', '0', '0', 'sturm', '4');
INSERT INTO `units` VALUES ('5', '2', '0', '0', 'scout', '5');
INSERT INTO `units` VALUES ('6', '2', '0', '0', 'art', '6');
INSERT INTO `units` VALUES ('7', '3', '0', '0', 'sturm', '7');
INSERT INTO `units` VALUES ('8', '3', '0', '0', 'scout', '8');
INSERT INTO `units` VALUES ('9', '3', '0', '0', 'art', '9');
INSERT INTO `units` VALUES ('10', '4', '0', '0', 'sturm', '10');
INSERT INTO `units` VALUES ('11', '4', '0', '0', 'scout', '11');
INSERT INTO `units` VALUES ('12', '4', '0', '0', 'art', '12');
