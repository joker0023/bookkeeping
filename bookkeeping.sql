/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : bookkeeping

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2020-07-17 17:48:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bill`
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `shop_id` bigint(20) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `remark` varchar(128) DEFAULT NULL,
  `detail` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bill
-- ----------------------------

-- ----------------------------
-- Table structure for `rel_buyer`
-- ----------------------------
DROP TABLE IF EXISTS `rel_buyer`;
CREATE TABLE `rel_buyer` (
  `shop_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `created` datetime DEFAULT NULL,
  `balance` double DEFAULT NULL,
  PRIMARY KEY (`user_id`,`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rel_buyer
-- ----------------------------

-- ----------------------------
-- Table structure for `rel_seller`
-- ----------------------------
DROP TABLE IF EXISTS `rel_seller`;
CREATE TABLE `rel_seller` (
  `user_id` bigint(20) NOT NULL,
  `shop_id` bigint(20) NOT NULL,
  `created` datetime DEFAULT NULL,
  `role` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rel_seller
-- ----------------------------
INSERT INTO `rel_seller` VALUES ('1', '1', '2020-07-14 15:08:21', '1');
INSERT INTO `rel_seller` VALUES ('2', '6', '2020-07-17 16:12:06', '1');
INSERT INTO `rel_seller` VALUES ('3', '1', '2020-07-14 15:08:21', '2');

-- ----------------------------
-- Table structure for `shop`
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  `img` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES ('1', 'ss01', null, '../bb.png', 'test01', '2020-07-14 15:07:08');
INSERT INTO `shop` VALUES ('6', 'aaasssss', null, null, null, '2020-07-17 16:12:06');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `nick` varchar(64) DEFAULT NULL,
  `avatar` varchar(128) DEFAULT NULL,
  `phone` varchar(64) DEFAULT NULL,
  `remark` varchar(128) DEFAULT NULL,
  `user_status` tinyint(4) DEFAULT NULL,
  `default_role` tinyint(4) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_openid` (`open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '01', 'joker', '/bookkeeping/bb.png', '15017564850', 'test', '1', '1', '1', '2020-07-14 10:57:36');
INSERT INTO `user` VALUES ('2', '02', 'lu', '/bookkeeping/bb.png', '15017564851', null, '1', '1', null, '2020-07-14 10:57:36');
INSERT INTO `user` VALUES ('3', '03', 'jo', '/bookkeeping/bb.png', '15017564852', null, '1', '1', null, '2020-07-14 10:57:36');
INSERT INTO `user` VALUES ('4', '04', 'uu01', '/bookkeeping/bb.png', '14017564853', null, '1', '1', null, '2020-07-14 10:57:36');
INSERT INTO `user` VALUES ('5', '05', 'uu02', '/bookkeeping/bb.png', '14017564854', null, '1', '1', null, '2020-07-14 10:57:36');
