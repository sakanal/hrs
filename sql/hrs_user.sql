/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80028
Source Host           : localhost:3306
Source Database       : hrs_user

Target Server Type    : MYSQL
Target Server Version : 80028
File Encoding         : 65001

Date: 2023-06-03 15:59:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_base_info
-- ----------------------------
DROP TABLE IF EXISTS `user_base_info`;
CREATE TABLE `user_base_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `head_portrait_url` varchar(255) DEFAULT NULL COMMENT '头像url',
  `phone` bigint DEFAULT NULL COMMENT '电话',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户基础数据表';

-- ----------------------------
-- Records of user_base_info
-- ----------------------------
INSERT INTO `user_base_info` VALUES ('1', 'sakanal', '黎海涛', 'https://hrs-sakanal.oss-cn-hangzhou.aliyuncs.com/avatar/2023/05/23/22333e84-0b0a-4a5c-8bea-ab27db1fca13_9.jfif', '19179148796', 'sakanal9527@gmail.com', '$2a$10$iHgX9pIjAIiPwSP.qGHvzuclsQhGcea4nN2ynpVN9pB/Q4CrbZwFu', '0', '2023-02-22 23:17:41', '2023-05-23 11:00:21');
INSERT INTO `user_base_info` VALUES ('2', 'little_sakanal', 'little_sakanal', 'https://hrs-sakanal.oss-cn-hangzhou.aliyuncs.com/avatar/2023/05/22/9a33540c-a7dd-48f4-8d99-5e80c486765e_avatar.jpg', '18870493681', '1104820805@qq.com', '$2a$10$3AfCv.1DugTY.awShO1Nqe2kw7Xlkj/OqOLhzt9wTO62KYZDGiIZa', '0', '2023-02-22 23:19:08', '2023-05-23 08:31:23');
INSERT INTO `user_base_info` VALUES ('3', 'xiaowang', 'ad6zhi60', 'https://guli-edu-sakanal.oss-cn-hangzhou.aliyuncs.com/avatar/string/2022/09/13/ef45e4e3e2764a77a3925aa4ce1f5e31multipartFile.png', '15546897895', '123456789@qq.com', '$2a$10$3AfCv.1DugTY.awShO1Nqe2kw7Xlkj/OqOLhzt9wTO62KYZDGiIZa', '0', '2023-02-23 10:04:22', '2023-02-23 10:04:22');
INSERT INTO `user_base_info` VALUES ('4', 'xaiowang', 'trjhamz4', 'https://guli-edu-sakanal.oss-cn-hangzhou.aliyuncs.com/avatar/string/2022/09/13/ef45e4e3e2764a77a3925aa4ce1f5e31multipartFile.png', '18954687920', '52168754@qq.com', '$2a$10$3AfCv.1DugTY.awShO1Nqe2kw7Xlkj/OqOLhzt9wTO62KYZDGiIZa', '0', '2023-02-23 11:24:50', '2023-03-25 17:13:00');
INSERT INTO `user_base_info` VALUES ('6', 'sakanal9521', '7ar6epww', 'https://guli-edu-sakanal.oss-cn-hangzhou.aliyuncs.com/avatar/string/2022/09/13/ef45e4e3e2764a77a3925aa4ce1f5e31multipartFile.png', '19179148793', '458975216@qq.com', '$2a$10$JbCBLyDd/bMgnW6m6nZMO.v2tPHl4L7VmHtj84wVkDOrxjtqnv52C', '0', '2023-04-01 17:09:26', '2023-04-01 17:09:26');
INSERT INTO `user_base_info` VALUES ('7', 'sakanal0', 'm997nf0t', 'https://guli-edu-sakanal.oss-cn-hangzhou.aliyuncs.com/avatar/string/2022/09/13/ef45e4e3e2764a77a3925aa4ce1f5e31multipartFile.png', '19315669400', null, '$2a$10$xN25rIow0vkDtraJcuWEy.3yotOL4olUhHFcUl2o87mVsedfb5flq', '0', '2023-05-22 16:25:27', '2023-05-22 16:25:27');
INSERT INTO `user_base_info` VALUES ('8', 'sakanal00', 'a14d12bc', 'https://guli-edu-sakanal.oss-cn-hangzhou.aliyuncs.com/avatar/string/2022/09/13/ef45e4e3e2764a77a3925aa4ce1f5e31multipartFile.png', '18870493680', null, '$2a$10$Mifp0Ipy8mqSyNGNLHp42O3XsRHUuRHmiU5WpPuf0q3cgrgWVf/wa', '0', '2023-05-22 16:27:36', '2023-05-22 16:27:36');
INSERT INTO `user_base_info` VALUES ('9', 'sakanal9527', '7mtxolxw', 'https://hrs-sakanal.oss-cn-hangzhou.aliyuncs.com/avatar/2023/05/26/23875e31-29ca-4b86-8dfa-3acbbb4bbb0a_avatar.jpg', '18870493682', null, '$2a$10$eyx715CZ6wPMk4SKWInt6u7PAUO6rTwbXjc47/uQwKaTZFkX9.DzO', '0', '2023-05-25 20:55:55', '2023-05-26 09:38:43');

-- ----------------------------
-- Table structure for user_browse
-- ----------------------------
DROP TABLE IF EXISTS `user_browse`;
CREATE TABLE `user_browse` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `base_info_id` bigint DEFAULT NULL COMMENT '房源基础信息id',
  `last_browse_time` datetime DEFAULT NULL COMMENT '最近浏览时间',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户浏览表';

-- ----------------------------
-- Records of user_browse
-- ----------------------------
INSERT INTO `user_browse` VALUES ('1', '1', '6', '2023-05-19 12:01:56', '0', '2023-03-26 11:02:49', '2023-05-19 12:01:56');
INSERT INTO `user_browse` VALUES ('2', '1', '7', '2023-05-19 12:01:23', '0', '2023-03-26 16:20:27', '2023-05-19 12:01:23');
INSERT INTO `user_browse` VALUES ('3', '1', '5', '2023-05-19 12:01:35', '0', '2023-03-26 16:34:28', '2023-05-19 12:01:35');
INSERT INTO `user_browse` VALUES ('4', '1', '4', '2023-05-19 12:00:49', '0', '2023-03-29 20:03:00', '2023-05-19 12:00:49');
INSERT INTO `user_browse` VALUES ('5', '1', '9', '2023-05-19 12:01:15', '0', '2023-04-02 10:43:49', '2023-05-19 12:01:15');
INSERT INTO `user_browse` VALUES ('6', '1', '8', '2023-05-19 12:01:32', '0', '2023-04-02 10:44:16', '2023-05-19 12:01:32');
INSERT INTO `user_browse` VALUES ('7', '1', '1', '2023-05-19 12:01:09', '0', '2023-04-02 10:50:22', '2023-05-19 12:01:09');
INSERT INTO `user_browse` VALUES ('8', '1', '10', '2023-05-19 12:01:18', '0', '2023-04-03 20:17:23', '2023-05-19 12:01:18');
INSERT INTO `user_browse` VALUES ('9', '1', '2', '2023-05-14 15:57:46', '0', '2023-04-15 19:01:56', '2023-05-14 15:57:46');
INSERT INTO `user_browse` VALUES ('10', '2', '11', '2023-05-22 20:08:00', '0', '2023-05-09 09:50:39', '2023-05-22 20:08:00');
INSERT INTO `user_browse` VALUES ('11', '2', '6', '2023-05-09 19:01:29', '0', '2023-05-09 19:01:25', '2023-05-09 19:01:29');
INSERT INTO `user_browse` VALUES ('12', '1', '12', '2023-05-19 12:01:26', '0', '2023-05-19 12:00:42', '2023-05-19 12:01:26');
INSERT INTO `user_browse` VALUES ('13', '1', '11', '2023-05-23 11:01:30', '0', '2023-05-19 12:01:07', '2023-05-23 11:01:30');
INSERT INTO `user_browse` VALUES ('14', '1', '3', '2023-05-26 10:42:51', '0', '2023-05-19 12:01:11', '2023-05-26 10:42:51');
INSERT INTO `user_browse` VALUES ('15', '2', '10', '2023-05-22 18:18:02', '0', '2023-05-22 18:18:02', '2023-05-22 18:18:02');

-- ----------------------------
-- Table structure for user_collection
-- ----------------------------
DROP TABLE IF EXISTS `user_collection`;
CREATE TABLE `user_collection` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `base_info_id` bigint DEFAULT NULL COMMENT '房源基础信息id',
  `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户收藏表';

-- ----------------------------
-- Records of user_collection
-- ----------------------------
INSERT INTO `user_collection` VALUES ('1', '1', '4', '1', '2023-03-29 20:44:05', '2023-03-29 20:44:05');
INSERT INTO `user_collection` VALUES ('2', '1', '5', '1', '2023-03-29 20:47:58', '2023-03-29 20:47:58');
INSERT INTO `user_collection` VALUES ('3', '1', '4', '0', '2023-03-29 20:52:05', '2023-03-29 20:52:05');
INSERT INTO `user_collection` VALUES ('4', '1', '6', '1', '2023-04-03 18:31:23', '2023-04-03 18:31:23');
INSERT INTO `user_collection` VALUES ('5', '1', '6', '1', '2023-05-13 18:13:56', '2023-05-13 18:13:56');
INSERT INTO `user_collection` VALUES ('6', '1', '2', '0', '2023-05-14 15:11:08', '2023-05-14 15:11:08');
INSERT INTO `user_collection` VALUES ('7', '1', '6', '1', '2023-05-14 16:14:42', '2023-05-14 16:14:42');
INSERT INTO `user_collection` VALUES ('8', '1', '6', '1', '2023-05-19 12:01:57', '2023-05-19 12:01:57');
INSERT INTO `user_collection` VALUES ('9', '1', '11', '1', '2023-05-22 16:10:16', '2023-05-22 16:10:16');
INSERT INTO `user_collection` VALUES ('10', '1', '11', '0', '2023-05-22 16:10:19', '2023-05-22 16:10:19');
INSERT INTO `user_collection` VALUES ('11', '2', '11', '0', '2023-05-22 18:17:53', '2023-05-22 18:17:53');
INSERT INTO `user_collection` VALUES ('12', '2', '10', '0', '2023-05-22 18:18:09', '2023-05-22 18:18:09');
