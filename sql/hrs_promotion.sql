/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80028
Source Host           : localhost:3306
Source Database       : hrs_promotion

Target Server Type    : MYSQL
Target Server Version : 80028
File Encoding         : 65001

Date: 2023-06-03 15:58:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for house_promotion_function
-- ----------------------------
DROP TABLE IF EXISTS `house_promotion_function`;
CREATE TABLE `house_promotion_function` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '推广标题',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '推广描述',
  `number` bigint unsigned DEFAULT '0' COMMENT '推广次数',
  `price` decimal(10,2) unsigned DEFAULT '0.00' COMMENT '推广价格',
  `show_state` tinyint DEFAULT '1' COMMENT '状态 0-隐藏 1-显示',
  `is_deleted` tinyint unsigned DEFAULT '0' COMMENT '0-未删除 1-已删除',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='推广活动表';

-- ----------------------------
-- Records of house_promotion_function
-- ----------------------------
INSERT INTO `house_promotion_function` VALUES ('1', '【体验套餐】', '此页面购买成功后，推荐功能会作用到所有已发布的房源中。', '10', '1.00', '1', '0', '2023-04-16 00:00:00', '2023-04-16 17:17:29');
INSERT INTO `house_promotion_function` VALUES ('2', '【黑铁套餐】', '此页面购买成功后，推荐功能会作用到所有已发布的房源中。', '100', '10.00', '1', '0', '2023-04-16 16:32:55', '2023-04-16 19:21:39');
INSERT INTO `house_promotion_function` VALUES ('3', '【青铜套餐】', '此页面购买成功后，推荐功能会作用到所有已发布的房源中。', '500', '34.99', '1', '0', '2023-04-16 16:33:21', '2023-04-16 17:17:35');
INSERT INTO `house_promotion_function` VALUES ('4', '【白银套餐】', '此页面购买成功后，推荐功能会作用到所有已发布的房源中。', '800', '49.99', '1', '0', '2023-04-16 16:33:58', '2023-04-16 16:35:22');
INSERT INTO `house_promotion_function` VALUES ('5', '【黄金套餐】', '此页面购买成功后，推荐功能会作用到所有已发布的房源中。', '1200', '59.99', '1', '0', '2023-04-16 16:35:16', '2023-04-16 16:35:16');

-- ----------------------------
-- Table structure for house_promotion_order
-- ----------------------------
DROP TABLE IF EXISTS `house_promotion_order`;
CREATE TABLE `house_promotion_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT '0' COMMENT '用户id',
  `promotion_id` bigint DEFAULT '0' COMMENT '推广id',
  `base_info_id` bigint DEFAULT NULL COMMENT '房源基础信息id',
  `number` bigint DEFAULT NULL COMMENT '购买数量',
  `total_money` decimal(9,2) DEFAULT NULL COMMENT '总金额',
  `state` tinyint DEFAULT '0' COMMENT '订单状态 0-未支付 1-已支付 2-取消订单',
  `show_state` tinyint DEFAULT NULL,
  `is_deleted` tinyint unsigned DEFAULT '0' COMMENT '0-未删除 1-已删除',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='推广活动订单表';

-- ----------------------------
-- Records of house_promotion_order
-- ----------------------------
INSERT INTO `house_promotion_order` VALUES ('1', '1', '2', '-10', '3', '30.00', '2', '1', '0', '2023-04-22 09:41:17', '2023-04-22 09:42:17');
INSERT INTO `house_promotion_order` VALUES ('2', '1', '3', '-10', '3', '104.97', '2', '1', '0', '2023-04-22 09:42:45', '2023-04-22 09:43:51');
INSERT INTO `house_promotion_order` VALUES ('3', '1', '3', '-10', '3', '104.97', '2', '1', '0', '2023-04-22 09:43:16', '2023-04-22 09:44:16');
INSERT INTO `house_promotion_order` VALUES ('4', '1', '3', '-10', '3', '104.97', '2', '1', '0', '2023-04-22 09:45:07', '2023-04-22 09:46:08');
INSERT INTO `house_promotion_order` VALUES ('5', '1', '3', '-10', '3', '104.97', '2', '1', '0', '2023-04-22 09:59:04', '2023-04-22 10:00:04');
INSERT INTO `house_promotion_order` VALUES ('6', '1', '3', '-10', '3', '104.97', '2', '1', '0', '2023-04-22 09:59:29', '2023-04-22 10:00:29');
INSERT INTO `house_promotion_order` VALUES ('7', '1', '3', '-10', '3', '104.97', '2', '1', '0', '2023-04-22 10:03:20', '2023-04-22 10:04:20');
INSERT INTO `house_promotion_order` VALUES ('8', '1', '3', '-10', '3', '104.97', '2', '1', '0', '2023-04-22 10:03:45', '2023-04-22 10:04:45');
INSERT INTO `house_promotion_order` VALUES ('9', '1', '3', '-10', '3', '104.97', '2', '1', '0', '2023-04-22 10:37:40', '2023-04-22 10:38:40');
INSERT INTO `house_promotion_order` VALUES ('10', '1', '3', '-10', '3', '104.97', '2', '1', '0', '2023-04-22 10:40:53', '2023-04-22 10:41:53');
INSERT INTO `house_promotion_order` VALUES ('11', '1', '3', '-10', '3', '104.97', '2', '1', '0', '2023-04-22 10:44:14', '2023-04-22 10:45:14');
INSERT INTO `house_promotion_order` VALUES ('12', '1', '3', '-10', '3', '104.97', '2', '1', '0', '2023-04-22 10:46:25', '2023-04-22 10:47:25');
INSERT INTO `house_promotion_order` VALUES ('13', '1', '3', '-10', '3', '104.97', '2', '1', '0', '2023-04-22 10:50:52', '2023-04-22 10:51:52');
INSERT INTO `house_promotion_order` VALUES ('14', '1', '3', '-10', '3', '104.97', '2', '1', '0', '2023-04-22 10:53:06', '2023-04-22 10:54:06');
INSERT INTO `house_promotion_order` VALUES ('15', '1', '3', '-10', '3', '104.97', '1', '1', '0', '2023-04-22 10:55:04', '2023-04-22 10:55:20');
INSERT INTO `house_promotion_order` VALUES ('16', '1', '5', '-10', '1', '59.99', '2', '1', '0', '2023-04-23 14:12:49', '2023-04-23 14:42:49');
INSERT INTO `house_promotion_order` VALUES ('17', '1', '5', '-10', '1', '59.99', '2', '1', '0', '2023-04-23 14:13:44', '2023-04-23 14:43:44');
INSERT INTO `house_promotion_order` VALUES ('18', '1', '5', '-10', '1', '59.99', '1', '1', '0', '2023-04-23 14:17:38', '2023-04-23 14:17:54');
INSERT INTO `house_promotion_order` VALUES ('19', '1', '5', '-10', '1', '59.99', '2', '1', '0', '2023-04-23 14:21:51', '2023-04-23 14:51:51');
INSERT INTO `house_promotion_order` VALUES ('20', '1', '5', '-10', '1', '59.99', '1', '1', '0', '2023-04-23 14:34:44', '2023-04-23 14:35:07');
INSERT INTO `house_promotion_order` VALUES ('21', '1', '5', '-10', '1', '59.99', '1', '1', '0', '2023-04-23 14:37:00', '2023-04-23 14:37:30');
INSERT INTO `house_promotion_order` VALUES ('22', '1', '5', '6', '2', '119.98', '1', '1', '0', '2023-04-23 14:46:15', '2023-04-23 14:46:38');
INSERT INTO `house_promotion_order` VALUES ('23', '1', '5', '8', '2', '119.98', '1', '1', '0', '2023-04-23 20:06:38', '2023-04-23 20:07:05');
INSERT INTO `house_promotion_order` VALUES ('24', '1', '2', '-10', '1', '10.00', '2', '0', '0', '2023-04-29 22:05:47', '2023-05-07 11:11:35');
INSERT INTO `house_promotion_order` VALUES ('25', '1', '3', '-10', '1', '34.99', '1', '1', '0', '2023-04-29 22:06:59', '2023-04-29 22:07:17');
INSERT INTO `house_promotion_order` VALUES ('26', '1', '4', '-10', '1', '49.99', '2', '0', '0', '2023-04-29 22:08:21', '2023-05-07 11:11:33');
INSERT INTO `house_promotion_order` VALUES ('27', '1', '5', '-10', '1', '59.99', '2', '0', '0', '2023-04-29 22:08:29', '2023-05-07 11:11:29');
INSERT INTO `house_promotion_order` VALUES ('28', '1', '1', '-10', '1', '1.00', '2', '0', '0', '2023-04-29 22:08:37', '2023-05-07 11:11:27');
INSERT INTO `house_promotion_order` VALUES ('29', '1', '5', '6', '1', '59.99', '2', '0', '0', '2023-04-29 22:08:43', '2023-05-07 11:11:25');
INSERT INTO `house_promotion_order` VALUES ('30', '1', '3', '-10', '1', '34.99', '2', '0', '0', '2023-04-29 22:09:18', '2023-05-07 11:11:21');
INSERT INTO `house_promotion_order` VALUES ('31', '1', '5', '-10', '1', '59.99', '1', '1', '0', '2023-04-29 22:09:58', '2023-04-29 22:10:29');
INSERT INTO `house_promotion_order` VALUES ('32', '1', '5', '-10', '1', '59.99', '1', '1', '0', '2023-04-29 22:10:45', '2023-04-29 22:11:05');
INSERT INTO `house_promotion_order` VALUES ('33', '1', '2', '-10', '1', '10.00', '2', '0', '0', '2023-04-29 22:11:44', '2023-05-07 11:09:15');
INSERT INTO `house_promotion_order` VALUES ('34', '1', '5', '-10', '1', '59.99', '2', '0', '0', '2023-04-29 22:11:57', '2023-05-07 11:08:59');
INSERT INTO `house_promotion_order` VALUES ('35', '1', '4', '10', '4', '199.96', '1', '1', '0', '2023-04-29 22:20:58', '2023-04-29 22:21:28');
INSERT INTO `house_promotion_order` VALUES ('36', '1', '5', '7', '1', '59.99', '1', '1', '0', '2023-05-07 10:08:43', '2023-05-07 10:09:10');
INSERT INTO `house_promotion_order` VALUES ('37', '1', '1', '-10', '1', '1.00', '2', '0', '0', '2023-05-07 11:27:57', '2023-05-22 16:14:49');
INSERT INTO `house_promotion_order` VALUES ('38', '1', '1', '-10', '1', '1.00', '2', '0', '0', '2023-05-07 15:11:11', '2023-05-22 16:14:47');
INSERT INTO `house_promotion_order` VALUES ('39', '2', '5', '11', '9', '539.91', '1', '1', '0', '2023-05-09 09:49:22', '2023-05-09 09:50:03');
INSERT INTO `house_promotion_order` VALUES ('40', '1', '2', '9', '3', '30.00', '1', '1', '0', '2023-05-22 16:11:40', '2023-05-22 16:12:08');
INSERT INTO `house_promotion_order` VALUES ('41', '1', '2', '-10', '1', '10.00', '1', '1', '0', '2023-05-22 16:12:56', '2023-05-22 16:15:29');
INSERT INTO `house_promotion_order` VALUES ('42', '1', '2', '1', '2', '20.00', '1', '1', '0', '2023-05-22 16:13:10', '2023-05-22 16:30:11');
INSERT INTO `house_promotion_order` VALUES ('43', '1', '4', '3', '1', '49.99', '1', '1', '0', '2023-05-22 16:32:28', '2023-05-22 16:32:45');
INSERT INTO `house_promotion_order` VALUES ('44', '2', '2', '12', '1', '10.00', '1', '1', '0', '2023-05-22 16:58:25', '2023-05-22 16:58:44');
INSERT INTO `house_promotion_order` VALUES ('45', '2', '2', '14', '1', '10.00', '1', '1', '0', '2023-05-22 18:07:59', '2023-05-22 18:08:22');
INSERT INTO `house_promotion_order` VALUES ('46', '2', '1', '12', '3', '3.00', '2', '1', '0', '2023-05-22 18:08:44', '2023-05-22 18:38:44');
INSERT INTO `house_promotion_order` VALUES ('47', '2', '2', '-3', '1', '10.00', '1', '1', '0', '2023-05-22 20:19:52', '2023-05-22 20:20:13');
INSERT INTO `house_promotion_order` VALUES ('48', '2', '2', '-3', '1', '10.00', '1', '1', '0', '2023-05-22 20:20:31', '2023-05-22 20:20:50');
INSERT INTO `house_promotion_order` VALUES ('49', '2', '2', '14', '1', '10.00', '1', '1', '0', '2023-05-22 22:07:22', '2023-05-22 22:10:25');
INSERT INTO `house_promotion_order` VALUES ('50', '2', '1', '14', '2', '2.00', '1', '1', '0', '2023-05-22 23:10:17', '2023-05-22 23:10:37');
INSERT INTO `house_promotion_order` VALUES ('51', '2', '1', '14', '1', '1.00', '1', '1', '0', '2023-05-22 23:14:25', '2023-05-22 23:14:57');
INSERT INTO `house_promotion_order` VALUES ('52', '2', '2', '-3', '1', '10.00', '1', '1', '0', '2023-05-22 23:16:08', '2023-05-22 23:17:00');
INSERT INTO `house_promotion_order` VALUES ('53', '2', '1', '14', '1', '1.00', '1', '1', '0', '2023-05-22 23:17:16', '2023-05-22 23:17:35');
INSERT INTO `house_promotion_order` VALUES ('54', '2', '1', '14', '1', '1.00', '1', '1', '0', '2023-05-22 23:17:54', '2023-05-22 23:18:10');
INSERT INTO `house_promotion_order` VALUES ('55', '2', '1', '14', '1', '1.00', '1', '1', '0', '2023-05-23 07:49:14', '2023-05-23 07:49:57');
INSERT INTO `house_promotion_order` VALUES ('56', '2', '1', '14', '1', '1.00', '1', '1', '0', '2023-05-23 07:59:47', '2023-05-23 08:00:26');
INSERT INTO `house_promotion_order` VALUES ('57', '2', '1', '14', '1', '1.00', '1', '1', '0', '2023-05-23 08:00:41', '2023-05-23 08:02:01');
INSERT INTO `house_promotion_order` VALUES ('58', '2', '1', '14', '1', '1.00', '1', '1', '0', '2023-05-23 08:16:59', '2023-05-23 08:32:51');
INSERT INTO `house_promotion_order` VALUES ('59', '2', '2', '14', '1', '10.00', '1', '1', '0', '2023-05-23 09:21:20', '2023-05-23 09:21:43');
INSERT INTO `house_promotion_order` VALUES ('60', '1', '2', '4', '1', '10.00', '1', '1', '0', '2023-05-27 17:05:32', '2023-05-27 17:05:51');
