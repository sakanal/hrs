/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80028
Source Host           : localhost:3306
Source Database       : hrs_admin

Target Server Type    : MYSQL
Target Server Version : 80028
File Encoding         : 65001

Date: 2023-06-03 15:58:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', null, 'io.renren.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000018617EE9C587874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('RenrenScheduler', 'DESKTOP-DU23H511685503373684', '1685504582193', '15000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int DEFAULT NULL,
  `INT_PROP_2` int DEFAULT NULL,
  `LONG_PROP_1` bigint DEFAULT NULL,
  `LONG_PROP_2` bigint DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint DEFAULT NULL,
  `PREV_FIRE_TIME` bigint DEFAULT NULL,
  `PRIORITY` int DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', null, '1685505600000', '1685503800000', '5', 'WAITING', 'CRON', '1675439013000', '0', null, '2', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000018617EE9C587874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES ('1', 'testTask', 'renren', '0 0/30 * * * ?', '0', '参数测试', '2023-02-03 23:39:19');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1199 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
INSERT INTO `schedule_job_log` VALUES ('1', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 11:30:02');
INSERT INTO `schedule_job_log` VALUES ('2', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('3', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('4', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('5', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('6', '1', 'testTask', 'renren', '0', null, '1', '2023-02-04 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('7', '1', 'testTask', 'renren', '0', null, '1', '2023-02-04 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('8', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('9', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('10', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('11', '1', 'testTask', 'renren', '0', null, '1', '2023-02-04 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('12', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('13', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('14', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('15', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('16', '1', 'testTask', 'renren', '0', null, '1', '2023-02-04 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('17', '1', 'testTask', 'renren', '0', null, '1', '2023-02-04 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('18', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('19', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('20', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('21', '1', 'testTask', 'renren', '0', null, '1', '2023-02-04 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('22', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('23', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('24', '1', 'testTask', 'renren', '0', null, '1', '2023-02-04 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('25', '1', 'testTask', 'renren', '0', null, '0', '2023-02-04 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('26', '1', 'testTask', 'renren', '0', null, '0', '2023-02-05 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('27', '1', 'testTask', 'renren', '0', null, '1', '2023-02-05 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('28', '1', 'testTask', 'renren', '0', null, '1', '2023-02-05 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('29', '1', 'testTask', 'renren', '0', null, '1', '2023-02-05 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('30', '1', 'testTask', 'renren', '0', null, '1', '2023-02-05 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('31', '1', 'testTask', 'renren', '0', null, '0', '2023-02-05 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('32', '1', 'testTask', 'renren', '0', null, '0', '2023-02-05 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('33', '1', 'testTask', 'renren', '0', null, '0', '2023-02-05 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('34', '1', 'testTask', 'renren', '0', null, '1', '2023-02-05 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('35', '1', 'testTask', 'renren', '0', null, '0', '2023-02-05 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('36', '1', 'testTask', 'renren', '0', null, '1', '2023-02-05 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('37', '1', 'testTask', 'renren', '0', null, '0', '2023-02-05 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('38', '1', 'testTask', 'renren', '0', null, '0', '2023-02-05 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('39', '1', 'testTask', 'renren', '0', null, '0', '2023-02-05 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('40', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('41', '1', 'testTask', 'renren', '0', null, '1', '2023-02-06 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('42', '1', 'testTask', 'renren', '0', null, '1', '2023-02-06 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('43', '1', 'testTask', 'renren', '0', null, '1', '2023-02-06 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('44', '1', 'testTask', 'renren', '0', null, '1', '2023-02-06 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('45', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('46', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('47', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('48', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('49', '1', 'testTask', 'renren', '0', null, '1', '2023-02-06 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('50', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('51', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('52', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('53', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('54', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('55', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('56', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('57', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('58', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('59', '1', 'testTask', 'renren', '0', null, '0', '2023-02-06 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('60', '1', 'testTask', 'renren', '0', null, '1', '2023-02-06 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('61', '1', 'testTask', 'renren', '0', null, '6', '2023-02-07 00:00:00');
INSERT INTO `schedule_job_log` VALUES ('62', '1', 'testTask', 'renren', '0', null, '1', '2023-02-07 00:30:00');
INSERT INTO `schedule_job_log` VALUES ('63', '1', 'testTask', 'renren', '0', null, '0', '2023-02-07 01:00:00');
INSERT INTO `schedule_job_log` VALUES ('64', '1', 'testTask', 'renren', '0', null, '1', '2023-02-07 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('65', '1', 'testTask', 'renren', '0', null, '1', '2023-02-07 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('66', '1', 'testTask', 'renren', '0', null, '1', '2023-02-07 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('67', '1', 'testTask', 'renren', '0', null, '0', '2023-02-07 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('68', '1', 'testTask', 'renren', '0', null, '0', '2023-02-07 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('69', '1', 'testTask', 'renren', '0', null, '0', '2023-02-07 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('70', '1', 'testTask', 'renren', '0', null, '0', '2023-02-07 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('71', '1', 'testTask', 'renren', '0', null, '1', '2023-02-07 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('72', '1', 'testTask', 'renren', '0', null, '0', '2023-02-08 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('73', '1', 'testTask', 'renren', '0', null, '1', '2023-02-08 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('74', '1', 'testTask', 'renren', '0', null, '0', '2023-02-08 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('75', '1', 'testTask', 'renren', '0', null, '1', '2023-02-08 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('76', '1', 'testTask', 'renren', '0', null, '0', '2023-02-08 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('77', '1', 'testTask', 'renren', '0', null, '0', '2023-02-08 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('78', '1', 'testTask', 'renren', '0', null, '0', '2023-02-08 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('79', '1', 'testTask', 'renren', '0', null, '0', '2023-02-08 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('80', '1', 'testTask', 'renren', '0', null, '0', '2023-02-08 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('81', '1', 'testTask', 'renren', '0', null, '1', '2023-02-08 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('82', '1', 'testTask', 'renren', '0', null, '0', '2023-02-08 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('83', '1', 'testTask', 'renren', '0', null, '0', '2023-02-08 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('84', '1', 'testTask', 'renren', '0', null, '0', '2023-02-08 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('85', '1', 'testTask', 'renren', '0', null, '1', '2023-02-08 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('86', '1', 'testTask', 'renren', '0', null, '1', '2023-02-08 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('87', '1', 'testTask', 'renren', '0', null, '1', '2023-02-08 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('88', '1', 'testTask', 'renren', '0', null, '1', '2023-02-08 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('89', '1', 'testTask', 'renren', '0', null, '1', '2023-02-08 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('90', '1', 'testTask', 'renren', '0', null, '1', '2023-02-08 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('91', '1', 'testTask', 'renren', '0', null, '0', '2023-02-08 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('92', '1', 'testTask', 'renren', '0', null, '1', '2023-02-08 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('93', '1', 'testTask', 'renren', '0', null, '1', '2023-02-09 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('94', '1', 'testTask', 'renren', '0', null, '0', '2023-02-09 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('95', '1', 'testTask', 'renren', '0', null, '1', '2023-02-09 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('96', '1', 'testTask', 'renren', '0', null, '0', '2023-02-09 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('97', '1', 'testTask', 'renren', '0', null, '1', '2023-02-09 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('98', '1', 'testTask', 'renren', '0', null, '1', '2023-02-09 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('99', '1', 'testTask', 'renren', '0', null, '1', '2023-02-09 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('100', '1', 'testTask', 'renren', '0', null, '0', '2023-02-09 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('101', '1', 'testTask', 'renren', '0', null, '0', '2023-02-09 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('102', '1', 'testTask', 'renren', '0', null, '1', '2023-02-09 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('103', '1', 'testTask', 'renren', '0', null, '1', '2023-02-09 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('104', '1', 'testTask', 'renren', '0', null, '1', '2023-02-09 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('105', '1', 'testTask', 'renren', '0', null, '0', '2023-02-09 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('106', '1', 'testTask', 'renren', '0', null, '0', '2023-02-09 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('107', '1', 'testTask', 'renren', '0', null, '1', '2023-02-09 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('108', '1', 'testTask', 'renren', '0', null, '0', '2023-02-09 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('109', '1', 'testTask', 'renren', '0', null, '0', '2023-02-09 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('110', '1', 'testTask', 'renren', '0', null, '1', '2023-02-09 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('111', '1', 'testTask', 'renren', '0', null, '1', '2023-02-09 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('112', '1', 'testTask', 'renren', '0', null, '1', '2023-02-09 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('113', '1', 'testTask', 'renren', '0', null, '0', '2023-02-09 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('114', '1', 'testTask', 'renren', '0', null, '1', '2023-02-09 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('115', '1', 'testTask', 'renren', '0', null, '2', '2023-02-10 00:00:00');
INSERT INTO `schedule_job_log` VALUES ('116', '1', 'testTask', 'renren', '0', null, '1', '2023-02-10 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('117', '1', 'testTask', 'renren', '0', null, '1', '2023-02-10 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('118', '1', 'testTask', 'renren', '0', null, '0', '2023-02-10 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('119', '1', 'testTask', 'renren', '0', null, '0', '2023-02-10 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('120', '1', 'testTask', 'renren', '0', null, '0', '2023-02-10 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('121', '1', 'testTask', 'renren', '0', null, '0', '2023-02-10 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('122', '1', 'testTask', 'renren', '0', null, '1', '2023-02-10 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('123', '1', 'testTask', 'renren', '0', null, '0', '2023-02-10 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('124', '1', 'testTask', 'renren', '0', null, '0', '2023-02-10 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('125', '1', 'testTask', 'renren', '0', null, '0', '2023-02-10 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('126', '1', 'testTask', 'renren', '0', null, '0', '2023-02-10 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('127', '1', 'testTask', 'renren', '0', null, '1', '2023-02-10 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('128', '1', 'testTask', 'renren', '0', null, '1', '2023-02-10 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('129', '1', 'testTask', 'renren', '0', null, '1', '2023-02-10 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('130', '1', 'testTask', 'renren', '0', null, '1', '2023-02-10 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('131', '1', 'testTask', 'renren', '0', null, '0', '2023-02-10 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('132', '1', 'testTask', 'renren', '0', null, '1', '2023-02-10 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('133', '1', 'testTask', 'renren', '0', null, '0', '2023-02-22 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('134', '1', 'testTask', 'renren', '0', null, '1', '2023-02-22 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('135', '1', 'testTask', 'renren', '0', null, '1', '2023-02-22 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('136', '1', 'testTask', 'renren', '0', null, '1', '2023-02-22 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('137', '1', 'testTask', 'renren', '0', null, '1', '2023-02-22 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('138', '1', 'testTask', 'renren', '0', null, '0', '2023-02-22 16:00:02');
INSERT INTO `schedule_job_log` VALUES ('139', '1', 'testTask', 'renren', '0', null, '0', '2023-02-22 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('140', '1', 'testTask', 'renren', '0', null, '0', '2023-02-22 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('141', '1', 'testTask', 'renren', '0', null, '1', '2023-02-22 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('142', '1', 'testTask', 'renren', '0', null, '0', '2023-02-22 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('143', '1', 'testTask', 'renren', '0', null, '1', '2023-02-22 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('144', '1', 'testTask', 'renren', '0', null, '0', '2023-02-22 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('145', '1', 'testTask', 'renren', '0', null, '0', '2023-02-22 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('146', '1', 'testTask', 'renren', '0', null, '1', '2023-02-22 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('147', '1', 'testTask', 'renren', '0', null, '0', '2023-02-22 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('148', '1', 'testTask', 'renren', '0', null, '1', '2023-02-22 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('149', '1', 'testTask', 'renren', '0', null, '0', '2023-02-22 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('150', '1', 'testTask', 'renren', '0', null, '1', '2023-02-22 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('151', '1', 'testTask', 'renren', '0', null, '0', '2023-02-22 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('152', '1', 'testTask', 'renren', '0', null, '1', '2023-02-22 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('153', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('154', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('155', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('156', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('157', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('158', '1', 'testTask', 'renren', '0', null, '0', '2023-02-23 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('159', '1', 'testTask', 'renren', '0', null, '0', '2023-02-23 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('160', '1', 'testTask', 'renren', '0', null, '0', '2023-02-23 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('161', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('162', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('163', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('164', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('165', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('166', '1', 'testTask', 'renren', '0', null, '2', '2023-02-23 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('167', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('168', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('169', '1', 'testTask', 'renren', '0', null, '0', '2023-02-23 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('170', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('171', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('172', '1', 'testTask', 'renren', '0', null, '0', '2023-02-23 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('173', '1', 'testTask', 'renren', '0', null, '0', '2023-02-23 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('174', '1', 'testTask', 'renren', '0', null, '0', '2023-02-23 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('175', '1', 'testTask', 'renren', '0', null, '1', '2023-02-23 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('176', '1', 'testTask', 'renren', '0', null, '0', '2023-02-23 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('177', '1', 'testTask', 'renren', '0', null, '0', '2023-02-28 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('178', '1', 'testTask', 'renren', '0', null, '0', '2023-02-28 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('179', '1', 'testTask', 'renren', '0', null, '1', '2023-02-28 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('180', '1', 'testTask', 'renren', '0', null, '1', '2023-02-28 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('181', '1', 'testTask', 'renren', '0', null, '1', '2023-02-28 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('182', '1', 'testTask', 'renren', '0', null, '1', '2023-02-28 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('183', '1', 'testTask', 'renren', '0', null, '1', '2023-03-01 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('184', '1', 'testTask', 'renren', '0', null, '0', '2023-03-01 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('185', '1', 'testTask', 'renren', '0', null, '1', '2023-03-01 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('186', '1', 'testTask', 'renren', '0', null, '1', '2023-03-01 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('187', '1', 'testTask', 'renren', '0', null, '1', '2023-03-01 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('188', '1', 'testTask', 'renren', '0', null, '0', '2023-03-01 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('189', '1', 'testTask', 'renren', '0', null, '1', '2023-03-01 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('190', '1', 'testTask', 'renren', '0', null, '0', '2023-03-01 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('191', '1', 'testTask', 'renren', '0', null, '1', '2023-03-01 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('192', '1', 'testTask', 'renren', '0', null, '1', '2023-03-01 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('193', '1', 'testTask', 'renren', '0', null, '0', '2023-03-01 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('194', '1', 'testTask', 'renren', '0', null, '0', '2023-03-01 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('195', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 09:00:00');
INSERT INTO `schedule_job_log` VALUES ('196', '1', 'testTask', 'renren', '0', null, '0', '2023-03-02 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('197', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('198', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('199', '1', 'testTask', 'renren', '0', null, '0', '2023-03-02 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('200', '1', 'testTask', 'renren', '0', null, '0', '2023-03-02 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('201', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('202', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('203', '1', 'testTask', 'renren', '0', null, '5', '2023-03-02 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('204', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('205', '1', 'testTask', 'renren', '0', null, '0', '2023-03-02 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('206', '1', 'testTask', 'renren', '0', null, '0', '2023-03-02 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('207', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('208', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('209', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('210', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('211', '1', 'testTask', 'renren', '0', null, '0', '2023-03-02 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('212', '1', 'testTask', 'renren', '0', null, '0', '2023-03-02 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('213', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('214', '1', 'testTask', 'renren', '0', null, '0', '2023-03-02 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('215', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('216', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('217', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('218', '1', 'testTask', 'renren', '0', null, '0', '2023-03-02 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('219', '1', 'testTask', 'renren', '0', null, '0', '2023-03-02 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('220', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('221', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('222', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('223', '1', 'testTask', 'renren', '0', null, '1', '2023-03-02 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('224', '1', 'testTask', 'renren', '0', null, '0', '2023-03-03 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('225', '1', 'testTask', 'renren', '0', null, '0', '2023-03-03 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('226', '1', 'testTask', 'renren', '0', null, '0', '2023-03-03 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('227', '1', 'testTask', 'renren', '0', null, '0', '2023-03-03 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('228', '1', 'testTask', 'renren', '0', null, '1', '2023-03-03 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('229', '1', 'testTask', 'renren', '0', null, '0', '2023-03-03 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('230', '1', 'testTask', 'renren', '0', null, '1', '2023-03-03 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('231', '1', 'testTask', 'renren', '0', null, '1', '2023-03-03 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('232', '1', 'testTask', 'renren', '0', null, '1', '2023-03-03 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('233', '1', 'testTask', 'renren', '0', null, '1', '2023-03-03 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('234', '1', 'testTask', 'renren', '0', null, '1', '2023-03-03 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('235', '1', 'testTask', 'renren', '0', null, '1', '2023-03-03 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('236', '1', 'testTask', 'renren', '0', null, '1', '2023-03-03 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('237', '1', 'testTask', 'renren', '0', null, '0', '2023-03-03 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('238', '1', 'testTask', 'renren', '0', null, '0', '2023-03-03 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('239', '1', 'testTask', 'renren', '0', null, '1', '2023-03-03 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('240', '1', 'testTask', 'renren', '0', null, '0', '2023-03-03 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('241', '1', 'testTask', 'renren', '0', null, '1', '2023-03-03 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('242', '1', 'testTask', 'renren', '0', null, '0', '2023-03-03 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('243', '1', 'testTask', 'renren', '0', null, '0', '2023-03-03 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('244', '1', 'testTask', 'renren', '0', null, '1', '2023-03-03 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('245', '1', 'testTask', 'renren', '0', null, '1', '2023-03-03 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('246', '1', 'testTask', 'renren', '0', null, '1', '2023-03-03 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('247', '1', 'testTask', 'renren', '0', null, '0', '2023-03-04 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('248', '1', 'testTask', 'renren', '0', null, '0', '2023-03-04 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('249', '1', 'testTask', 'renren', '0', null, '3', '2023-03-04 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('250', '1', 'testTask', 'renren', '0', null, '1', '2023-03-04 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('251', '1', 'testTask', 'renren', '0', null, '0', '2023-03-04 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('252', '1', 'testTask', 'renren', '0', null, '0', '2023-03-04 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('253', '1', 'testTask', 'renren', '0', null, '1', '2023-03-04 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('254', '1', 'testTask', 'renren', '0', null, '0', '2023-03-04 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('255', '1', 'testTask', 'renren', '0', null, '0', '2023-03-04 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('256', '1', 'testTask', 'renren', '0', null, '1', '2023-03-04 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('257', '1', 'testTask', 'renren', '0', null, '0', '2023-03-04 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('258', '1', 'testTask', 'renren', '0', null, '1', '2023-03-04 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('259', '1', 'testTask', 'renren', '0', null, '0', '2023-03-07 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('260', '1', 'testTask', 'renren', '0', null, '0', '2023-03-07 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('261', '1', 'testTask', 'renren', '0', null, '5', '2023-03-07 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('262', '1', 'testTask', 'renren', '0', null, '1', '2023-03-07 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('263', '1', 'testTask', 'renren', '0', null, '0', '2023-03-07 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('264', '1', 'testTask', 'renren', '0', null, '1', '2023-03-07 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('265', '1', 'testTask', 'renren', '0', null, '0', '2023-03-07 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('266', '1', 'testTask', 'renren', '0', null, '0', '2023-03-07 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('267', '1', 'testTask', 'renren', '0', null, '1', '2023-03-07 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('268', '1', 'testTask', 'renren', '0', null, '1', '2023-03-07 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('269', '1', 'testTask', 'renren', '0', null, '0', '2023-03-07 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('270', '1', 'testTask', 'renren', '0', null, '1', '2023-03-07 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('271', '1', 'testTask', 'renren', '0', null, '1', '2023-03-07 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('272', '1', 'testTask', 'renren', '0', null, '0', '2023-03-07 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('273', '1', 'testTask', 'renren', '0', null, '0', '2023-03-07 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('274', '1', 'testTask', 'renren', '0', null, '1', '2023-03-07 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('275', '1', 'testTask', 'renren', '0', null, '1', '2023-03-07 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('276', '1', 'testTask', 'renren', '0', null, '1', '2023-03-10 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('277', '1', 'testTask', 'renren', '0', null, '0', '2023-03-10 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('278', '1', 'testTask', 'renren', '0', null, '1', '2023-03-10 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('279', '1', 'testTask', 'renren', '0', null, '0', '2023-03-10 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('280', '1', 'testTask', 'renren', '0', null, '0', '2023-03-10 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('281', '1', 'testTask', 'renren', '0', null, '1', '2023-03-10 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('282', '1', 'testTask', 'renren', '0', null, '0', '2023-03-10 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('283', '1', 'testTask', 'renren', '0', null, '0', '2023-03-10 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('284', '1', 'testTask', 'renren', '0', null, '1', '2023-03-10 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('285', '1', 'testTask', 'renren', '0', null, '4', '2023-03-10 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('286', '1', 'testTask', 'renren', '0', null, '0', '2023-03-10 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('287', '1', 'testTask', 'renren', '0', null, '1', '2023-03-10 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('288', '1', 'testTask', 'renren', '0', null, '0', '2023-03-10 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('289', '1', 'testTask', 'renren', '0', null, '0', '2023-03-10 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('290', '1', 'testTask', 'renren', '0', null, '1', '2023-03-10 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('291', '1', 'testTask', 'renren', '0', null, '0', '2023-03-10 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('292', '1', 'testTask', 'renren', '0', null, '0', '2023-03-10 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('293', '1', 'testTask', 'renren', '0', null, '0', '2023-03-10 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('294', '1', 'testTask', 'renren', '0', null, '0', '2023-03-10 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('295', '1', 'testTask', 'renren', '0', null, '1', '2023-03-10 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('296', '1', 'testTask', 'renren', '0', null, '0', '2023-03-11 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('297', '1', 'testTask', 'renren', '0', null, '0', '2023-03-11 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('298', '1', 'testTask', 'renren', '0', null, '1', '2023-03-11 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('299', '1', 'testTask', 'renren', '0', null, '1', '2023-03-11 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('300', '1', 'testTask', 'renren', '0', null, '0', '2023-03-11 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('301', '1', 'testTask', 'renren', '0', null, '1', '2023-03-11 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('302', '1', 'testTask', 'renren', '0', null, '1', '2023-03-11 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('303', '1', 'testTask', 'renren', '0', null, '1', '2023-03-11 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('304', '1', 'testTask', 'renren', '0', null, '0', '2023-03-11 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('305', '1', 'testTask', 'renren', '0', null, '1', '2023-03-11 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('306', '1', 'testTask', 'renren', '0', null, '1', '2023-03-11 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('307', '1', 'testTask', 'renren', '0', null, '1', '2023-03-11 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('308', '1', 'testTask', 'renren', '0', null, '0', '2023-03-11 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('309', '1', 'testTask', 'renren', '0', null, '1', '2023-03-11 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('310', '1', 'testTask', 'renren', '0', null, '1', '2023-03-12 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('311', '1', 'testTask', 'renren', '0', null, '0', '2023-03-12 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('312', '1', 'testTask', 'renren', '0', null, '1', '2023-03-12 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('313', '1', 'testTask', 'renren', '0', null, '0', '2023-03-12 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('314', '1', 'testTask', 'renren', '0', null, '0', '2023-03-12 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('315', '1', 'testTask', 'renren', '0', null, '0', '2023-03-12 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('316', '1', 'testTask', 'renren', '0', null, '0', '2023-03-12 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('317', '1', 'testTask', 'renren', '0', null, '0', '2023-03-12 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('318', '1', 'testTask', 'renren', '0', null, '0', '2023-03-12 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('319', '1', 'testTask', 'renren', '0', null, '1', '2023-03-12 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('320', '1', 'testTask', 'renren', '0', null, '0', '2023-03-12 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('321', '1', 'testTask', 'renren', '0', null, '0', '2023-03-12 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('322', '1', 'testTask', 'renren', '0', null, '1', '2023-03-12 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('323', '1', 'testTask', 'renren', '0', null, '1', '2023-03-12 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('324', '1', 'testTask', 'renren', '0', null, '1', '2023-03-12 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('325', '1', 'testTask', 'renren', '0', null, '1', '2023-03-12 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('326', '1', 'testTask', 'renren', '0', null, '0', '2023-03-12 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('327', '1', 'testTask', 'renren', '0', null, '0', '2023-03-13 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('328', '1', 'testTask', 'renren', '0', null, '0', '2023-03-13 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('329', '1', 'testTask', 'renren', '0', null, '1', '2023-03-13 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('330', '1', 'testTask', 'renren', '0', null, '1', '2023-03-13 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('331', '1', 'testTask', 'renren', '0', null, '1', '2023-03-13 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('332', '1', 'testTask', 'renren', '0', null, '0', '2023-03-13 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('333', '1', 'testTask', 'renren', '0', null, '0', '2023-03-13 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('334', '1', 'testTask', 'renren', '0', null, '1', '2023-03-13 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('335', '1', 'testTask', 'renren', '0', null, '1', '2023-03-13 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('336', '1', 'testTask', 'renren', '0', null, '1', '2023-03-13 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('337', '1', 'testTask', 'renren', '0', null, '0', '2023-03-13 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('338', '1', 'testTask', 'renren', '0', null, '0', '2023-03-13 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('339', '1', 'testTask', 'renren', '0', null, '1', '2023-03-13 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('340', '1', 'testTask', 'renren', '0', null, '1', '2023-03-13 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('341', '1', 'testTask', 'renren', '0', null, '0', '2023-03-13 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('342', '1', 'testTask', 'renren', '0', null, '0', '2023-03-13 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('343', '1', 'testTask', 'renren', '0', null, '1', '2023-03-13 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('344', '1', 'testTask', 'renren', '0', null, '0', '2023-03-13 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('345', '1', 'testTask', 'renren', '0', null, '1', '2023-03-13 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('346', '1', 'testTask', 'renren', '0', null, '0', '2023-03-13 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('347', '1', 'testTask', 'renren', '0', null, '1', '2023-03-14 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('348', '1', 'testTask', 'renren', '0', null, '0', '2023-03-14 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('349', '1', 'testTask', 'renren', '0', null, '1', '2023-03-14 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('350', '1', 'testTask', 'renren', '0', null, '0', '2023-03-14 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('351', '1', 'testTask', 'renren', '0', null, '1', '2023-03-14 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('352', '1', 'testTask', 'renren', '0', null, '0', '2023-03-14 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('353', '1', 'testTask', 'renren', '0', null, '1', '2023-03-14 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('354', '1', 'testTask', 'renren', '0', null, '0', '2023-03-14 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('355', '1', 'testTask', 'renren', '0', null, '0', '2023-03-14 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('356', '1', 'testTask', 'renren', '0', null, '0', '2023-03-19 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('357', '1', 'testTask', 'renren', '0', null, '0', '2023-03-19 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('358', '1', 'testTask', 'renren', '0', null, '1', '2023-03-19 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('359', '1', 'testTask', 'renren', '0', null, '1', '2023-03-19 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('360', '1', 'testTask', 'renren', '0', null, '0', '2023-03-19 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('361', '1', 'testTask', 'renren', '0', null, '0', '2023-03-19 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('362', '1', 'testTask', 'renren', '0', null, '1', '2023-03-19 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('363', '1', 'testTask', 'renren', '0', null, '0', '2023-03-19 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('364', '1', 'testTask', 'renren', '0', null, '0', '2023-03-19 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('365', '1', 'testTask', 'renren', '0', null, '1', '2023-03-19 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('366', '1', 'testTask', 'renren', '0', null, '0', '2023-03-19 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('367', '1', 'testTask', 'renren', '0', null, '0', '2023-03-19 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('368', '1', 'testTask', 'renren', '0', null, '1', '2023-03-19 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('369', '1', 'testTask', 'renren', '0', null, '0', '2023-03-19 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('370', '1', 'testTask', 'renren', '0', null, '1', '2023-03-19 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('371', '1', 'testTask', 'renren', '0', null, '1', '2023-03-19 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('372', '1', 'testTask', 'renren', '0', null, '1', '2023-03-19 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('373', '1', 'testTask', 'renren', '0', null, '0', '2023-03-19 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('374', '1', 'testTask', 'renren', '0', null, '0', '2023-03-19 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('375', '1', 'testTask', 'renren', '0', null, '1', '2023-03-20 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('376', '1', 'testTask', 'renren', '0', null, '0', '2023-03-20 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('377', '1', 'testTask', 'renren', '0', null, '1', '2023-03-20 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('378', '1', 'testTask', 'renren', '0', null, '0', '2023-03-20 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('379', '1', 'testTask', 'renren', '0', null, '1', '2023-03-20 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('380', '1', 'testTask', 'renren', '0', null, '1', '2023-03-20 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('381', '1', 'testTask', 'renren', '0', null, '0', '2023-03-20 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('382', '1', 'testTask', 'renren', '0', null, '0', '2023-03-20 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('383', '1', 'testTask', 'renren', '0', null, '1', '2023-03-20 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('384', '1', 'testTask', 'renren', '0', null, '1', '2023-03-20 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('385', '1', 'testTask', 'renren', '0', null, '1', '2023-03-20 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('386', '1', 'testTask', 'renren', '0', null, '1', '2023-03-20 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('387', '1', 'testTask', 'renren', '0', null, '1', '2023-03-20 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('388', '1', 'testTask', 'renren', '0', null, '14', '2023-03-20 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('389', '1', 'testTask', 'renren', '0', null, '0', '2023-03-20 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('390', '1', 'testTask', 'renren', '0', null, '1', '2023-03-20 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('391', '1', 'testTask', 'renren', '0', null, '1', '2023-03-20 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('392', '1', 'testTask', 'renren', '0', null, '1', '2023-03-20 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('393', '1', 'testTask', 'renren', '0', null, '0', '2023-03-20 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('394', '1', 'testTask', 'renren', '0', null, '0', '2023-03-20 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('395', '1', 'testTask', 'renren', '0', null, '2', '2023-03-20 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('396', '1', 'testTask', 'renren', '0', null, '1', '2023-03-20 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('397', '1', 'testTask', 'renren', '0', null, '0', '2023-03-20 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('398', '1', 'testTask', 'renren', '0', null, '0', '2023-03-20 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('399', '1', 'testTask', 'renren', '0', null, '1', '2023-03-21 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('400', '1', 'testTask', 'renren', '0', null, '0', '2023-03-21 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('401', '1', 'testTask', 'renren', '0', null, '1', '2023-03-21 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('402', '1', 'testTask', 'renren', '0', null, '1', '2023-03-21 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('403', '1', 'testTask', 'renren', '0', null, '0', '2023-03-21 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('404', '1', 'testTask', 'renren', '0', null, '1', '2023-03-21 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('405', '1', 'testTask', 'renren', '0', null, '1', '2023-03-21 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('406', '1', 'testTask', 'renren', '0', null, '0', '2023-03-21 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('407', '1', 'testTask', 'renren', '0', null, '1', '2023-03-21 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('408', '1', 'testTask', 'renren', '0', null, '0', '2023-03-21 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('409', '1', 'testTask', 'renren', '0', null, '1', '2023-03-21 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('410', '1', 'testTask', 'renren', '0', null, '0', '2023-03-21 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('411', '1', 'testTask', 'renren', '0', null, '0', '2023-03-21 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('412', '1', 'testTask', 'renren', '0', null, '1', '2023-03-21 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('413', '1', 'testTask', 'renren', '0', null, '0', '2023-03-21 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('414', '1', 'testTask', 'renren', '0', null, '1', '2023-03-21 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('415', '1', 'testTask', 'renren', '0', null, '1', '2023-03-21 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('416', '1', 'testTask', 'renren', '0', null, '0', '2023-03-22 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('417', '1', 'testTask', 'renren', '0', null, '0', '2023-03-22 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('418', '1', 'testTask', 'renren', '0', null, '0', '2023-03-22 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('419', '1', 'testTask', 'renren', '0', null, '0', '2023-03-22 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('420', '1', 'testTask', 'renren', '0', null, '1', '2023-03-22 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('421', '1', 'testTask', 'renren', '0', null, '1', '2023-03-22 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('422', '1', 'testTask', 'renren', '0', null, '1', '2023-03-22 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('423', '1', 'testTask', 'renren', '0', null, '1', '2023-03-22 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('424', '1', 'testTask', 'renren', '0', null, '0', '2023-03-22 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('425', '1', 'testTask', 'renren', '0', null, '1', '2023-03-22 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('426', '1', 'testTask', 'renren', '0', null, '1', '2023-03-22 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('427', '1', 'testTask', 'renren', '0', null, '0', '2023-03-22 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('428', '1', 'testTask', 'renren', '0', null, '0', '2023-03-22 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('429', '1', 'testTask', 'renren', '0', null, '0', '2023-03-22 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('430', '1', 'testTask', 'renren', '0', null, '1', '2023-03-22 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('431', '1', 'testTask', 'renren', '0', null, '2', '2023-03-22 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('432', '1', 'testTask', 'renren', '0', null, '1', '2023-03-22 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('433', '1', 'testTask', 'renren', '0', null, '0', '2023-03-22 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('434', '1', 'testTask', 'renren', '0', null, '1', '2023-03-23 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('435', '1', 'testTask', 'renren', '0', null, '0', '2023-03-23 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('436', '1', 'testTask', 'renren', '0', null, '1', '2023-03-23 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('437', '1', 'testTask', 'renren', '0', null, '1', '2023-03-23 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('438', '1', 'testTask', 'renren', '0', null, '0', '2023-03-23 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('439', '1', 'testTask', 'renren', '0', null, '1', '2023-03-23 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('440', '1', 'testTask', 'renren', '0', null, '0', '2023-03-23 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('441', '1', 'testTask', 'renren', '0', null, '0', '2023-03-23 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('442', '1', 'testTask', 'renren', '0', null, '1', '2023-03-23 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('443', '1', 'testTask', 'renren', '0', null, '1', '2023-03-23 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('444', '1', 'testTask', 'renren', '0', null, '0', '2023-03-23 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('445', '1', 'testTask', 'renren', '0', null, '0', '2023-03-23 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('446', '1', 'testTask', 'renren', '0', null, '0', '2023-03-23 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('447', '1', 'testTask', 'renren', '0', null, '0', '2023-03-23 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('448', '1', 'testTask', 'renren', '0', null, '1', '2023-03-24 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('449', '1', 'testTask', 'renren', '0', null, '1', '2023-03-24 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('450', '1', 'testTask', 'renren', '0', null, '0', '2023-03-24 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('451', '1', 'testTask', 'renren', '0', null, '1', '2023-03-24 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('452', '1', 'testTask', 'renren', '0', null, '0', '2023-03-24 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('453', '1', 'testTask', 'renren', '0', null, '0', '2023-03-24 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('454', '1', 'testTask', 'renren', '0', null, '1', '2023-03-24 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('455', '1', 'testTask', 'renren', '0', null, '0', '2023-03-24 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('456', '1', 'testTask', 'renren', '0', null, '1', '2023-03-24 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('457', '1', 'testTask', 'renren', '0', null, '1', '2023-03-24 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('458', '1', 'testTask', 'renren', '0', null, '0', '2023-03-24 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('459', '1', 'testTask', 'renren', '0', null, '1', '2023-03-24 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('460', '1', 'testTask', 'renren', '0', null, '1', '2023-03-24 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('461', '1', 'testTask', 'renren', '0', null, '0', '2023-03-24 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('462', '1', 'testTask', 'renren', '0', null, '0', '2023-03-24 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('463', '1', 'testTask', 'renren', '0', null, '1', '2023-03-24 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('464', '1', 'testTask', 'renren', '0', null, '1', '2023-03-24 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('465', '1', 'testTask', 'renren', '0', null, '1', '2023-03-24 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('466', '1', 'testTask', 'renren', '0', null, '1', '2023-03-24 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('467', '1', 'testTask', 'renren', '0', null, '0', '2023-03-25 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('468', '1', 'testTask', 'renren', '0', null, '0', '2023-03-25 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('469', '1', 'testTask', 'renren', '0', null, '1', '2023-03-25 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('470', '1', 'testTask', 'renren', '0', null, '0', '2023-03-25 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('471', '1', 'testTask', 'renren', '0', null, '1', '2023-03-25 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('472', '1', 'testTask', 'renren', '0', null, '0', '2023-03-25 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('473', '1', 'testTask', 'renren', '0', null, '1', '2023-03-25 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('474', '1', 'testTask', 'renren', '0', null, '1', '2023-03-25 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('475', '1', 'testTask', 'renren', '0', null, '0', '2023-03-25 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('476', '1', 'testTask', 'renren', '0', null, '1', '2023-03-25 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('477', '1', 'testTask', 'renren', '0', null, '1', '2023-03-25 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('478', '1', 'testTask', 'renren', '0', null, '1', '2023-03-25 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('479', '1', 'testTask', 'renren', '0', null, '1', '2023-03-25 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('480', '1', 'testTask', 'renren', '0', null, '1', '2023-03-25 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('481', '1', 'testTask', 'renren', '0', null, '1', '2023-03-25 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('482', '1', 'testTask', 'renren', '0', null, '1', '2023-03-25 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('483', '1', 'testTask', 'renren', '0', null, '1', '2023-03-26 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('484', '1', 'testTask', 'renren', '0', null, '0', '2023-03-26 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('485', '1', 'testTask', 'renren', '0', null, '0', '2023-03-26 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('486', '1', 'testTask', 'renren', '0', null, '0', '2023-03-26 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('487', '1', 'testTask', 'renren', '0', null, '1', '2023-03-26 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('488', '1', 'testTask', 'renren', '0', null, '0', '2023-03-26 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('489', '1', 'testTask', 'renren', '0', null, '1', '2023-03-26 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('490', '1', 'testTask', 'renren', '0', null, '1', '2023-03-26 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('491', '1', 'testTask', 'renren', '0', null, '0', '2023-03-26 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('492', '1', 'testTask', 'renren', '0', null, '1', '2023-03-26 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('493', '1', 'testTask', 'renren', '0', null, '0', '2023-03-26 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('494', '1', 'testTask', 'renren', '0', null, '1', '2023-03-26 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('495', '1', 'testTask', 'renren', '0', null, '1', '2023-03-26 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('496', '1', 'testTask', 'renren', '0', null, '0', '2023-03-26 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('497', '1', 'testTask', 'renren', '0', null, '1', '2023-03-26 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('498', '1', 'testTask', 'renren', '0', null, '0', '2023-03-26 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('499', '1', 'testTask', 'renren', '0', null, '1', '2023-03-26 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('500', '1', 'testTask', 'renren', '0', null, '0', '2023-03-26 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('501', '1', 'testTask', 'renren', '0', null, '1', '2023-03-26 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('502', '1', 'testTask', 'renren', '0', null, '0', '2023-03-26 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('503', '1', 'testTask', 'renren', '0', null, '0', '2023-03-26 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('504', '1', 'testTask', 'renren', '0', null, '0', '2023-03-29 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('505', '1', 'testTask', 'renren', '0', null, '1', '2023-03-29 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('506', '1', 'testTask', 'renren', '0', null, '0', '2023-03-29 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('507', '1', 'testTask', 'renren', '0', null, '0', '2023-03-29 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('508', '1', 'testTask', 'renren', '0', null, '0', '2023-03-29 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('509', '1', 'testTask', 'renren', '0', null, '0', '2023-04-01 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('510', '1', 'testTask', 'renren', '0', null, '1', '2023-04-01 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('511', '1', 'testTask', 'renren', '0', null, '1', '2023-04-01 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('512', '1', 'testTask', 'renren', '0', null, '0', '2023-04-01 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('513', '1', 'testTask', 'renren', '0', null, '1', '2023-04-01 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('514', '1', 'testTask', 'renren', '0', null, '1', '2023-04-01 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('515', '1', 'testTask', 'renren', '0', null, '0', '2023-04-01 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('516', '1', 'testTask', 'renren', '0', null, '0', '2023-04-01 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('517', '1', 'testTask', 'renren', '0', null, '2', '2023-04-01 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('518', '1', 'testTask', 'renren', '0', null, '0', '2023-04-01 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('519', '1', 'testTask', 'renren', '0', null, '1', '2023-04-01 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('520', '1', 'testTask', 'renren', '0', null, '1', '2023-04-01 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('521', '1', 'testTask', 'renren', '0', null, '0', '2023-04-01 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('522', '1', 'testTask', 'renren', '0', null, '1', '2023-04-01 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('523', '1', 'testTask', 'renren', '0', null, '11', '2023-04-01 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('524', '1', 'testTask', 'renren', '0', null, '0', '2023-04-01 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('525', '1', 'testTask', 'renren', '0', null, '1', '2023-04-01 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('526', '1', 'testTask', 'renren', '0', null, '1', '2023-04-01 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('527', '1', 'testTask', 'renren', '0', null, '1', '2023-04-01 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('528', '1', 'testTask', 'renren', '0', null, '0', '2023-04-01 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('529', '1', 'testTask', 'renren', '0', null, '0', '2023-04-01 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('530', '1', 'testTask', 'renren', '0', null, '1', '2023-04-02 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('531', '1', 'testTask', 'renren', '0', null, '1', '2023-04-02 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('532', '1', 'testTask', 'renren', '0', null, '1', '2023-04-02 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('533', '1', 'testTask', 'renren', '0', null, '1', '2023-04-02 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('534', '1', 'testTask', 'renren', '0', null, '0', '2023-04-02 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('535', '1', 'testTask', 'renren', '0', null, '0', '2023-04-02 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('536', '1', 'testTask', 'renren', '0', null, '0', '2023-04-02 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('537', '1', 'testTask', 'renren', '0', null, '0', '2023-04-02 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('538', '1', 'testTask', 'renren', '0', null, '0', '2023-04-02 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('539', '1', 'testTask', 'renren', '0', null, '1', '2023-04-02 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('540', '1', 'testTask', 'renren', '0', null, '0', '2023-04-02 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('541', '1', 'testTask', 'renren', '0', null, '4', '2023-04-03 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('542', '1', 'testTask', 'renren', '0', null, '0', '2023-04-03 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('543', '1', 'testTask', 'renren', '0', null, '0', '2023-04-03 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('544', '1', 'testTask', 'renren', '0', null, '1', '2023-04-03 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('545', '1', 'testTask', 'renren', '0', null, '1', '2023-04-03 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('546', '1', 'testTask', 'renren', '0', null, '1', '2023-04-03 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('547', '1', 'testTask', 'renren', '0', null, '0', '2023-04-15 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('548', '1', 'testTask', 'renren', '0', null, '0', '2023-04-15 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('549', '1', 'testTask', 'renren', '0', null, '0', '2023-04-15 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('550', '1', 'testTask', 'renren', '0', null, '0', '2023-04-15 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('551', '1', 'testTask', 'renren', '0', null, '0', '2023-04-15 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('552', '1', 'testTask', 'renren', '0', null, '0', '2023-04-15 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('553', '1', 'testTask', 'renren', '0', null, '1', '2023-04-15 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('554', '1', 'testTask', 'renren', '0', null, '1', '2023-04-16 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('555', '1', 'testTask', 'renren', '0', null, '1', '2023-04-16 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('556', '1', 'testTask', 'renren', '0', null, '1', '2023-04-16 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('557', '1', 'testTask', 'renren', '0', null, '0', '2023-04-16 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('558', '1', 'testTask', 'renren', '0', null, '1', '2023-04-16 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('559', '1', 'testTask', 'renren', '0', null, '1', '2023-04-16 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('560', '1', 'testTask', 'renren', '0', null, '0', '2023-04-16 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('561', '1', 'testTask', 'renren', '0', null, '0', '2023-04-16 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('562', '1', 'testTask', 'renren', '0', null, '0', '2023-04-16 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('563', '1', 'testTask', 'renren', '0', null, '1', '2023-04-16 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('564', '1', 'testTask', 'renren', '0', null, '0', '2023-04-16 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('565', '1', 'testTask', 'renren', '0', null, '0', '2023-04-16 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('566', '1', 'testTask', 'renren', '0', null, '1', '2023-04-16 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('567', '1', 'testTask', 'renren', '0', null, '0', '2023-04-16 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('568', '1', 'testTask', 'renren', '0', null, '0', '2023-04-16 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('569', '1', 'testTask', 'renren', '0', null, '1', '2023-04-16 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('570', '1', 'testTask', 'renren', '0', null, '0', '2023-04-16 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('571', '1', 'testTask', 'renren', '0', null, '0', '2023-04-16 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('572', '1', 'testTask', 'renren', '0', null, '0', '2023-04-16 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('573', '1', 'testTask', 'renren', '0', null, '1', '2023-04-16 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('574', '1', 'testTask', 'renren', '0', null, '12', '2023-04-16 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('575', '1', 'testTask', 'renren', '0', null, '1', '2023-04-16 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('576', '1', 'testTask', 'renren', '0', null, '0', '2023-04-16 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('577', '1', 'testTask', 'renren', '0', null, '0', '2023-04-16 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('578', '1', 'testTask', 'renren', '0', null, '0', '2023-04-17 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('579', '1', 'testTask', 'renren', '0', null, '1', '2023-04-17 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('580', '1', 'testTask', 'renren', '0', null, '0', '2023-04-17 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('581', '1', 'testTask', 'renren', '0', null, '2', '2023-04-17 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('582', '1', 'testTask', 'renren', '0', null, '0', '2023-04-17 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('583', '1', 'testTask', 'renren', '0', null, '1', '2023-04-17 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('584', '1', 'testTask', 'renren', '0', null, '0', '2023-04-21 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('585', '1', 'testTask', 'renren', '0', null, '1', '2023-04-21 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('586', '1', 'testTask', 'renren', '0', null, '0', '2023-04-21 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('587', '1', 'testTask', 'renren', '0', null, '1', '2023-04-21 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('588', '1', 'testTask', 'renren', '0', null, '1', '2023-04-21 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('589', '1', 'testTask', 'renren', '0', null, '0', '2023-04-21 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('590', '1', 'testTask', 'renren', '0', null, '0', '2023-04-21 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('591', '1', 'testTask', 'renren', '0', null, '1', '2023-04-21 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('592', '1', 'testTask', 'renren', '0', null, '0', '2023-04-21 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('593', '1', 'testTask', 'renren', '0', null, '0', '2023-04-21 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('594', '1', 'testTask', 'renren', '0', null, '1', '2023-04-21 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('595', '1', 'testTask', 'renren', '0', null, '0', '2023-04-21 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('596', '1', 'testTask', 'renren', '0', null, '1', '2023-04-21 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('597', '1', 'testTask', 'renren', '0', null, '1', '2023-04-21 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('598', '1', 'testTask', 'renren', '0', null, '1', '2023-04-21 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('599', '1', 'testTask', 'renren', '0', null, '2', '2023-04-21 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('600', '1', 'testTask', 'renren', '0', null, '0', '2023-04-22 09:00:00');
INSERT INTO `schedule_job_log` VALUES ('601', '1', 'testTask', 'renren', '0', null, '0', '2023-04-22 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('602', '1', 'testTask', 'renren', '0', null, '0', '2023-04-22 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('603', '1', 'testTask', 'renren', '0', null, '0', '2023-04-22 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('604', '1', 'testTask', 'renren', '0', null, '1', '2023-04-22 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('605', '1', 'testTask', 'renren', '0', null, '1', '2023-04-22 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('606', '1', 'testTask', 'renren', '0', null, '0', '2023-04-22 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('607', '1', 'testTask', 'renren', '0', null, '0', '2023-04-22 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('608', '1', 'testTask', 'renren', '0', null, '0', '2023-04-22 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('609', '1', 'testTask', 'renren', '0', null, '0', '2023-04-22 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('610', '1', 'testTask', 'renren', '0', null, '0', '2023-04-22 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('611', '1', 'testTask', 'renren', '0', null, '1', '2023-04-22 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('612', '1', 'testTask', 'renren', '0', null, '1', '2023-04-22 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('613', '1', 'testTask', 'renren', '0', null, '0', '2023-04-22 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('614', '1', 'testTask', 'renren', '0', null, '1', '2023-04-22 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('615', '1', 'testTask', 'renren', '0', null, '0', '2023-04-23 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('616', '1', 'testTask', 'renren', '0', null, '1', '2023-04-23 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('617', '1', 'testTask', 'renren', '0', null, '1', '2023-04-23 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('618', '1', 'testTask', 'renren', '0', null, '1', '2023-04-23 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('619', '1', 'testTask', 'renren', '0', null, '1', '2023-04-23 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('620', '1', 'testTask', 'renren', '0', null, '0', '2023-04-23 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('621', '1', 'testTask', 'renren', '0', null, '1', '2023-04-23 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('622', '1', 'testTask', 'renren', '0', null, '0', '2023-04-23 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('623', '1', 'testTask', 'renren', '0', null, '1', '2023-04-23 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('624', '1', 'testTask', 'renren', '0', null, '1', '2023-04-23 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('625', '1', 'testTask', 'renren', '0', null, '1', '2023-04-23 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('626', '1', 'testTask', 'renren', '0', null, '1', '2023-04-23 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('627', '1', 'testTask', 'renren', '0', null, '1', '2023-04-23 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('628', '1', 'testTask', 'renren', '0', null, '0', '2023-04-23 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('629', '1', 'testTask', 'renren', '0', null, '1', '2023-04-23 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('630', '1', 'testTask', 'renren', '0', null, '2', '2023-04-23 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('631', '1', 'testTask', 'renren', '0', null, '0', '2023-04-23 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('632', '1', 'testTask', 'renren', '0', null, '1', '2023-04-23 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('633', '1', 'testTask', 'renren', '0', null, '0', '2023-04-23 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('634', '1', 'testTask', 'renren', '0', null, '1', '2023-04-24 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('635', '1', 'testTask', 'renren', '0', null, '0', '2023-04-24 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('636', '1', 'testTask', 'renren', '0', null, '1', '2023-04-24 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('637', '1', 'testTask', 'renren', '0', null, '0', '2023-04-24 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('638', '1', 'testTask', 'renren', '0', null, '0', '2023-04-24 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('639', '1', 'testTask', 'renren', '0', null, '0', '2023-04-24 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('640', '1', 'testTask', 'renren', '0', null, '1', '2023-04-24 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('641', '1', 'testTask', 'renren', '0', null, '0', '2023-04-24 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('642', '1', 'testTask', 'renren', '0', null, '1', '2023-04-24 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('643', '1', 'testTask', 'renren', '0', null, '1', '2023-04-24 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('644', '1', 'testTask', 'renren', '0', null, '0', '2023-04-24 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('645', '1', 'testTask', 'renren', '0', null, '0', '2023-04-24 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('646', '1', 'testTask', 'renren', '0', null, '1', '2023-04-24 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('647', '1', 'testTask', 'renren', '0', null, '1', '2023-04-24 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('648', '1', 'testTask', 'renren', '0', null, '0', '2023-04-24 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('649', '1', 'testTask', 'renren', '0', null, '1', '2023-04-24 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('650', '1', 'testTask', 'renren', '0', null, '0', '2023-04-24 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('651', '1', 'testTask', 'renren', '0', null, '0', '2023-04-28 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('652', '1', 'testTask', 'renren', '0', null, '0', '2023-04-28 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('653', '1', 'testTask', 'renren', '0', null, '1', '2023-04-28 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('654', '1', 'testTask', 'renren', '0', null, '0', '2023-04-28 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('655', '1', 'testTask', 'renren', '0', null, '0', '2023-04-28 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('656', '1', 'testTask', 'renren', '0', null, '0', '2023-04-28 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('657', '1', 'testTask', 'renren', '0', null, '1', '2023-04-28 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('658', '1', 'testTask', 'renren', '0', null, '0', '2023-04-28 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('659', '1', 'testTask', 'renren', '0', null, '0', '2023-04-28 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('660', '1', 'testTask', 'renren', '0', null, '1', '2023-04-28 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('661', '1', 'testTask', 'renren', '0', null, '1', '2023-04-28 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('662', '1', 'testTask', 'renren', '0', null, '0', '2023-04-28 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('663', '1', 'testTask', 'renren', '0', null, '0', '2023-04-28 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('664', '1', 'testTask', 'renren', '0', null, '1', '2023-04-28 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('665', '1', 'testTask', 'renren', '0', null, '1', '2023-04-28 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('666', '1', 'testTask', 'renren', '0', null, '1', '2023-04-28 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('667', '1', 'testTask', 'renren', '0', null, '0', '2023-04-28 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('668', '1', 'testTask', 'renren', '0', null, '4', '2023-04-29 00:00:00');
INSERT INTO `schedule_job_log` VALUES ('669', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 00:30:00');
INSERT INTO `schedule_job_log` VALUES ('670', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 01:00:00');
INSERT INTO `schedule_job_log` VALUES ('671', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 01:30:00');
INSERT INTO `schedule_job_log` VALUES ('672', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 02:00:00');
INSERT INTO `schedule_job_log` VALUES ('673', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 02:30:00');
INSERT INTO `schedule_job_log` VALUES ('674', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 03:00:00');
INSERT INTO `schedule_job_log` VALUES ('675', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 03:30:00');
INSERT INTO `schedule_job_log` VALUES ('676', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 04:00:00');
INSERT INTO `schedule_job_log` VALUES ('677', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 04:30:00');
INSERT INTO `schedule_job_log` VALUES ('678', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 05:00:00');
INSERT INTO `schedule_job_log` VALUES ('679', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 05:30:00');
INSERT INTO `schedule_job_log` VALUES ('680', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 06:00:00');
INSERT INTO `schedule_job_log` VALUES ('681', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 06:30:00');
INSERT INTO `schedule_job_log` VALUES ('682', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 07:00:00');
INSERT INTO `schedule_job_log` VALUES ('683', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 07:30:00');
INSERT INTO `schedule_job_log` VALUES ('684', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 08:00:00');
INSERT INTO `schedule_job_log` VALUES ('685', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 08:30:00');
INSERT INTO `schedule_job_log` VALUES ('686', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 09:00:00');
INSERT INTO `schedule_job_log` VALUES ('687', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('688', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('689', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('690', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('691', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('692', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('693', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('694', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('695', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('696', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('697', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('698', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('699', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('700', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('701', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('702', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('703', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('704', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('705', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('706', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('707', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('708', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('709', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('710', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('711', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('712', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('713', '1', 'testTask', 'renren', '0', null, '1', '2023-04-29 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('714', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('715', '1', 'testTask', 'renren', '0', null, '0', '2023-04-29 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('716', '1', 'testTask', 'renren', '0', null, '3', '2023-04-30 00:00:00');
INSERT INTO `schedule_job_log` VALUES ('717', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 00:30:00');
INSERT INTO `schedule_job_log` VALUES ('718', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 01:00:00');
INSERT INTO `schedule_job_log` VALUES ('719', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 01:30:00');
INSERT INTO `schedule_job_log` VALUES ('720', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 02:00:00');
INSERT INTO `schedule_job_log` VALUES ('721', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 02:30:00');
INSERT INTO `schedule_job_log` VALUES ('722', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 03:00:00');
INSERT INTO `schedule_job_log` VALUES ('723', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 03:30:00');
INSERT INTO `schedule_job_log` VALUES ('724', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 04:00:00');
INSERT INTO `schedule_job_log` VALUES ('725', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 04:30:00');
INSERT INTO `schedule_job_log` VALUES ('726', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 05:00:00');
INSERT INTO `schedule_job_log` VALUES ('727', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 05:30:00');
INSERT INTO `schedule_job_log` VALUES ('728', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 06:00:00');
INSERT INTO `schedule_job_log` VALUES ('729', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 06:30:00');
INSERT INTO `schedule_job_log` VALUES ('730', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 07:00:00');
INSERT INTO `schedule_job_log` VALUES ('731', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 07:30:00');
INSERT INTO `schedule_job_log` VALUES ('732', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 08:00:00');
INSERT INTO `schedule_job_log` VALUES ('733', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 08:30:00');
INSERT INTO `schedule_job_log` VALUES ('734', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 09:00:00');
INSERT INTO `schedule_job_log` VALUES ('735', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('736', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('737', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('738', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('739', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('740', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('741', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('742', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('743', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('744', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('745', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('746', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('747', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('748', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('749', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('750', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('751', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('752', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('753', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('754', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('755', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('756', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('757', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('758', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('759', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('760', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('761', '1', 'testTask', 'renren', '0', null, '1', '2023-04-30 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('762', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('763', '1', 'testTask', 'renren', '0', null, '0', '2023-04-30 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('764', '1', 'testTask', 'renren', '0', null, '6', '2023-05-01 00:00:00');
INSERT INTO `schedule_job_log` VALUES ('765', '1', 'testTask', 'renren', '0', null, '3', '2023-05-01 00:30:00');
INSERT INTO `schedule_job_log` VALUES ('766', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 01:00:00');
INSERT INTO `schedule_job_log` VALUES ('767', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 01:30:00');
INSERT INTO `schedule_job_log` VALUES ('768', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 02:00:00');
INSERT INTO `schedule_job_log` VALUES ('769', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 02:30:00');
INSERT INTO `schedule_job_log` VALUES ('770', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 03:00:00');
INSERT INTO `schedule_job_log` VALUES ('771', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 03:30:00');
INSERT INTO `schedule_job_log` VALUES ('772', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 08:30:00');
INSERT INTO `schedule_job_log` VALUES ('773', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 09:00:00');
INSERT INTO `schedule_job_log` VALUES ('774', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('775', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('776', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('777', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('778', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('779', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('780', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('781', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('782', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('783', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('784', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('785', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('786', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('787', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('788', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('789', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('790', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('791', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('792', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('793', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('794', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('795', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('796', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('797', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('798', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('799', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('800', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('801', '1', 'testTask', 'renren', '0', null, '0', '2023-05-01 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('802', '1', 'testTask', 'renren', '0', null, '1', '2023-05-01 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('803', '1', 'testTask', 'renren', '0', null, '5', '2023-05-02 00:00:00');
INSERT INTO `schedule_job_log` VALUES ('804', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 00:30:00');
INSERT INTO `schedule_job_log` VALUES ('805', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 01:00:00');
INSERT INTO `schedule_job_log` VALUES ('806', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 01:30:00');
INSERT INTO `schedule_job_log` VALUES ('807', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 02:00:00');
INSERT INTO `schedule_job_log` VALUES ('808', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 02:30:00');
INSERT INTO `schedule_job_log` VALUES ('809', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 03:00:00');
INSERT INTO `schedule_job_log` VALUES ('810', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 03:30:00');
INSERT INTO `schedule_job_log` VALUES ('811', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 04:00:00');
INSERT INTO `schedule_job_log` VALUES ('812', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 04:30:00');
INSERT INTO `schedule_job_log` VALUES ('813', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 05:00:00');
INSERT INTO `schedule_job_log` VALUES ('814', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 05:30:00');
INSERT INTO `schedule_job_log` VALUES ('815', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 06:00:00');
INSERT INTO `schedule_job_log` VALUES ('816', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 06:30:00');
INSERT INTO `schedule_job_log` VALUES ('817', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 07:00:00');
INSERT INTO `schedule_job_log` VALUES ('818', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 07:30:00');
INSERT INTO `schedule_job_log` VALUES ('819', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 08:00:00');
INSERT INTO `schedule_job_log` VALUES ('820', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 08:30:00');
INSERT INTO `schedule_job_log` VALUES ('821', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 09:00:00');
INSERT INTO `schedule_job_log` VALUES ('822', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('823', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('824', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('825', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('826', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('827', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('828', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('829', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('830', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('831', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('832', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('833', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('834', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('835', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('836', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('837', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('838', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('839', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('840', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('841', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('842', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('843', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('844', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('845', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('846', '1', 'testTask', 'renren', '0', null, '0', '2023-05-02 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('847', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('848', '1', 'testTask', 'renren', '0', null, '1', '2023-05-02 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('849', '1', 'testTask', 'renren', '0', null, '10', '2023-05-03 00:00:00');
INSERT INTO `schedule_job_log` VALUES ('850', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 00:30:00');
INSERT INTO `schedule_job_log` VALUES ('851', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 01:00:00');
INSERT INTO `schedule_job_log` VALUES ('852', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 01:30:00');
INSERT INTO `schedule_job_log` VALUES ('853', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 02:00:00');
INSERT INTO `schedule_job_log` VALUES ('854', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 02:30:00');
INSERT INTO `schedule_job_log` VALUES ('855', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 03:00:00');
INSERT INTO `schedule_job_log` VALUES ('856', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 03:30:00');
INSERT INTO `schedule_job_log` VALUES ('857', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 04:00:00');
INSERT INTO `schedule_job_log` VALUES ('858', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 04:30:00');
INSERT INTO `schedule_job_log` VALUES ('859', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 05:00:00');
INSERT INTO `schedule_job_log` VALUES ('860', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 05:30:00');
INSERT INTO `schedule_job_log` VALUES ('861', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 06:00:00');
INSERT INTO `schedule_job_log` VALUES ('862', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 06:30:00');
INSERT INTO `schedule_job_log` VALUES ('863', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 07:00:00');
INSERT INTO `schedule_job_log` VALUES ('864', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 07:30:00');
INSERT INTO `schedule_job_log` VALUES ('865', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 08:00:00');
INSERT INTO `schedule_job_log` VALUES ('866', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 08:30:00');
INSERT INTO `schedule_job_log` VALUES ('867', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 09:00:00');
INSERT INTO `schedule_job_log` VALUES ('868', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('869', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('870', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('871', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('872', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('873', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('874', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('875', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('876', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('877', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('878', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('879', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('880', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('881', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('882', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('883', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('884', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('885', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('886', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('887', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('888', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('889', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('890', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('891', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('892', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('893', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('894', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('895', '1', 'testTask', 'renren', '0', null, '1', '2023-05-03 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('896', '1', 'testTask', 'renren', '0', null, '0', '2023-05-03 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('897', '1', 'testTask', 'renren', '0', null, '8', '2023-05-04 00:00:00');
INSERT INTO `schedule_job_log` VALUES ('898', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 00:30:00');
INSERT INTO `schedule_job_log` VALUES ('899', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 01:00:00');
INSERT INTO `schedule_job_log` VALUES ('900', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 01:30:00');
INSERT INTO `schedule_job_log` VALUES ('901', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 02:00:00');
INSERT INTO `schedule_job_log` VALUES ('902', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 02:30:00');
INSERT INTO `schedule_job_log` VALUES ('903', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 03:00:00');
INSERT INTO `schedule_job_log` VALUES ('904', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 03:30:00');
INSERT INTO `schedule_job_log` VALUES ('905', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 04:00:00');
INSERT INTO `schedule_job_log` VALUES ('906', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 04:30:00');
INSERT INTO `schedule_job_log` VALUES ('907', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 05:00:00');
INSERT INTO `schedule_job_log` VALUES ('908', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 05:30:00');
INSERT INTO `schedule_job_log` VALUES ('909', '1', 'testTask', 'renren', '0', null, '6', '2023-05-04 06:00:00');
INSERT INTO `schedule_job_log` VALUES ('910', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 06:30:00');
INSERT INTO `schedule_job_log` VALUES ('911', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 07:00:00');
INSERT INTO `schedule_job_log` VALUES ('912', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 07:30:00');
INSERT INTO `schedule_job_log` VALUES ('913', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 08:00:00');
INSERT INTO `schedule_job_log` VALUES ('914', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 08:30:00');
INSERT INTO `schedule_job_log` VALUES ('915', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 09:00:00');
INSERT INTO `schedule_job_log` VALUES ('916', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('917', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('918', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('919', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('920', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('921', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('922', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('923', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('924', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('925', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('926', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('927', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('928', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('929', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('930', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('931', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('932', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('933', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('934', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('935', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('936', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('937', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('938', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('939', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('940', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('941', '1', 'testTask', 'renren', '0', null, '0', '2023-05-04 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('942', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('943', '1', 'testTask', 'renren', '0', null, '1', '2023-05-04 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('944', '1', 'testTask', 'renren', '0', null, '0', '2023-05-07 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('945', '1', 'testTask', 'renren', '0', null, '0', '2023-05-07 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('946', '1', 'testTask', 'renren', '0', null, '1', '2023-05-07 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('947', '1', 'testTask', 'renren', '0', null, '1', '2023-05-07 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('948', '1', 'testTask', 'renren', '0', null, '0', '2023-05-07 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('949', '1', 'testTask', 'renren', '0', null, '1', '2023-05-07 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('950', '1', 'testTask', 'renren', '0', null, '0', '2023-05-07 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('951', '1', 'testTask', 'renren', '0', null, '0', '2023-05-07 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('952', '1', 'testTask', 'renren', '0', null, '1', '2023-05-07 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('953', '1', 'testTask', 'renren', '0', null, '0', '2023-05-07 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('954', '1', 'testTask', 'renren', '0', null, '1', '2023-05-07 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('955', '1', 'testTask', 'renren', '0', null, '0', '2023-05-07 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('956', '1', 'testTask', 'renren', '0', null, '1', '2023-05-07 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('957', '1', 'testTask', 'renren', '0', null, '0', '2023-05-07 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('958', '1', 'testTask', 'renren', '0', null, '1', '2023-05-07 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('959', '1', 'testTask', 'renren', '0', null, '1', '2023-05-07 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('960', '1', 'testTask', 'renren', '0', null, '0', '2023-05-07 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('961', '1', 'testTask', 'renren', '0', null, '0', '2023-05-07 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('962', '1', 'testTask', 'renren', '0', null, '1', '2023-05-07 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('963', '1', 'testTask', 'renren', '0', null, '1', '2023-05-07 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('964', '1', 'testTask', 'renren', '0', null, '1', '2023-05-07 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('965', '1', 'testTask', 'renren', '0', null, '0', '2023-05-07 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('966', '1', 'testTask', 'renren', '0', null, '0', '2023-05-07 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('967', '1', 'testTask', 'renren', '0', null, '0', '2023-05-09 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('968', '1', 'testTask', 'renren', '0', null, '0', '2023-05-09 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('969', '1', 'testTask', 'renren', '0', null, '1', '2023-05-09 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('970', '1', 'testTask', 'renren', '0', null, '0', '2023-05-09 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('971', '1', 'testTask', 'renren', '0', null, '1', '2023-05-09 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('972', '1', 'testTask', 'renren', '0', null, '1', '2023-05-09 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('973', '1', 'testTask', 'renren', '0', null, '1', '2023-05-09 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('974', '1', 'testTask', 'renren', '0', null, '0', '2023-05-09 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('975', '1', 'testTask', 'renren', '0', null, '1', '2023-05-09 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('976', '1', 'testTask', 'renren', '0', null, '0', '2023-05-09 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('977', '1', 'testTask', 'renren', '0', null, '1', '2023-05-09 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('978', '1', 'testTask', 'renren', '0', null, '1', '2023-05-09 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('979', '1', 'testTask', 'renren', '0', null, '2', '2023-05-09 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('980', '1', 'testTask', 'renren', '0', null, '0', '2023-05-09 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('981', '1', 'testTask', 'renren', '0', null, '0', '2023-05-09 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('982', '1', 'testTask', 'renren', '0', null, '0', '2023-05-09 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('983', '1', 'testTask', 'renren', '0', null, '0', '2023-05-09 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('984', '1', 'testTask', 'renren', '0', null, '0', '2023-05-09 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('985', '1', 'testTask', 'renren', '0', null, '1', '2023-05-09 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('986', '1', 'testTask', 'renren', '0', null, '1', '2023-05-09 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('987', '1', 'testTask', 'renren', '0', null, '0', '2023-05-11 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('988', '1', 'testTask', 'renren', '0', null, '1', '2023-05-11 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('989', '1', 'testTask', 'renren', '0', null, '0', '2023-05-11 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('990', '1', 'testTask', 'renren', '0', null, '1', '2023-05-11 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('991', '1', 'testTask', 'renren', '0', null, '1', '2023-05-11 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('992', '1', 'testTask', 'renren', '0', null, '0', '2023-05-11 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('993', '1', 'testTask', 'renren', '0', null, '1', '2023-05-11 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('994', '1', 'testTask', 'renren', '0', null, '1', '2023-05-11 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('995', '1', 'testTask', 'renren', '0', null, '1', '2023-05-11 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('996', '1', 'testTask', 'renren', '0', null, '1', '2023-05-11 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('997', '1', 'testTask', 'renren', '0', null, '0', '2023-05-11 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('998', '1', 'testTask', 'renren', '0', null, '5', '2023-05-11 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('999', '1', 'testTask', 'renren', '0', null, '0', '2023-05-11 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('1000', '1', 'testTask', 'renren', '0', null, '1', '2023-05-11 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('1001', '1', 'testTask', 'renren', '0', null, '0', '2023-05-11 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('1002', '1', 'testTask', 'renren', '0', null, '1', '2023-05-11 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('1003', '1', 'testTask', 'renren', '0', null, '1', '2023-05-11 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('1004', '1', 'testTask', 'renren', '0', null, '0', '2023-05-11 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('1005', '1', 'testTask', 'renren', '0', null, '0', '2023-05-11 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('1006', '1', 'testTask', 'renren', '0', null, '0', '2023-05-11 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('1007', '1', 'testTask', 'renren', '0', null, '0', '2023-05-11 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('1008', '1', 'testTask', 'renren', '0', null, '0', '2023-05-11 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('1009', '1', 'testTask', 'renren', '0', null, '2', '2023-05-11 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('1010', '1', 'testTask', 'renren', '0', null, '1', '2023-05-11 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('1011', '1', 'testTask', 'renren', '0', null, '1', '2023-05-11 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('1012', '1', 'testTask', 'renren', '0', null, '0', '2023-05-11 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('1013', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 01:30:00');
INSERT INTO `schedule_job_log` VALUES ('1014', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('1015', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('1016', '1', 'testTask', 'renren', '0', null, '1', '2023-05-12 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('1017', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('1018', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('1019', '1', 'testTask', 'renren', '0', null, '1', '2023-05-12 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('1020', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('1021', '1', 'testTask', 'renren', '0', null, '1', '2023-05-12 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('1022', '1', 'testTask', 'renren', '0', null, '1', '2023-05-12 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('1023', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('1024', '1', 'testTask', 'renren', '0', null, '1', '2023-05-12 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('1025', '1', 'testTask', 'renren', '0', null, '1', '2023-05-12 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('1026', '1', 'testTask', 'renren', '0', null, '1', '2023-05-12 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('1027', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('1028', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('1029', '1', 'testTask', 'renren', '0', null, '3', '2023-05-12 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('1030', '1', 'testTask', 'renren', '0', null, '1', '2023-05-12 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('1031', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('1032', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('1033', '1', 'testTask', 'renren', '0', null, '1', '2023-05-12 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('1034', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('1035', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('1036', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('1037', '1', 'testTask', 'renren', '0', null, '1', '2023-05-12 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('1038', '1', 'testTask', 'renren', '0', null, '1', '2023-05-12 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('1039', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('1040', '1', 'testTask', 'renren', '0', null, '0', '2023-05-12 23:30:00');
INSERT INTO `schedule_job_log` VALUES ('1041', '1', 'testTask', 'renren', '0', null, '14', '2023-05-13 00:00:00');
INSERT INTO `schedule_job_log` VALUES ('1042', '1', 'testTask', 'renren', '0', null, '1', '2023-05-13 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('1043', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('1044', '1', 'testTask', 'renren', '0', null, '1', '2023-05-13 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('1045', '1', 'testTask', 'renren', '0', null, '1', '2023-05-13 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('1046', '1', 'testTask', 'renren', '0', null, '1', '2023-05-13 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('1047', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('1048', '1', 'testTask', 'renren', '0', null, '1', '2023-05-13 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('1049', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('1050', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('1051', '1', 'testTask', 'renren', '0', null, '1', '2023-05-13 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('1052', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('1053', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('1054', '1', 'testTask', 'renren', '0', null, '1', '2023-05-13 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('1055', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('1056', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('1057', '1', 'testTask', 'renren', '0', null, '1', '2023-05-13 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('1058', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('1059', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('1060', '1', 'testTask', 'renren', '0', null, '1', '2023-05-13 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('1061', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('1062', '1', 'testTask', 'renren', '0', null, '1', '2023-05-13 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('1063', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('1064', '1', 'testTask', 'renren', '0', null, '1', '2023-05-13 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('1065', '1', 'testTask', 'renren', '0', null, '1', '2023-05-13 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('1066', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('1067', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('1068', '1', 'testTask', 'renren', '0', null, '0', '2023-05-13 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('1069', '1', 'testTask', 'renren', '0', null, '3', '2023-05-14 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('1070', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('1071', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('1072', '1', 'testTask', 'renren', '0', null, '1', '2023-05-14 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('1073', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('1074', '1', 'testTask', 'renren', '0', null, '1', '2023-05-14 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('1075', '1', 'testTask', 'renren', '0', null, '1', '2023-05-14 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('1076', '1', 'testTask', 'renren', '0', null, '1', '2023-05-14 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('1077', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('1078', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('1079', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('1080', '1', 'testTask', 'renren', '0', null, '1', '2023-05-14 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('1081', '1', 'testTask', 'renren', '0', null, '1', '2023-05-14 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('1082', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('1083', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('1084', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('1085', '1', 'testTask', 'renren', '0', null, '1', '2023-05-14 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('1086', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('1087', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('1088', '1', 'testTask', 'renren', '0', null, '1', '2023-05-14 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('1089', '1', 'testTask', 'renren', '0', null, '1', '2023-05-14 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('1090', '1', 'testTask', 'renren', '0', null, '1', '2023-05-14 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('1091', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('1092', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('1093', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('1094', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('1095', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('1096', '1', 'testTask', 'renren', '0', null, '0', '2023-05-14 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('1097', '1', 'testTask', 'renren', '0', null, '3', '2023-05-15 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('1098', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('1099', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('1100', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('1101', '1', 'testTask', 'renren', '0', null, '1', '2023-05-15 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('1102', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('1103', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('1104', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('1105', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('1106', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('1107', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('1108', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('1109', '1', 'testTask', 'renren', '0', null, '1', '2023-05-15 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('1110', '1', 'testTask', 'renren', '0', null, '1', '2023-05-15 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('1111', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('1112', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('1113', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('1114', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('1115', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('1116', '1', 'testTask', 'renren', '0', null, '1', '2023-05-15 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('1117', '1', 'testTask', 'renren', '0', null, '1', '2023-05-15 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('1118', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('1119', '1', 'testTask', 'renren', '0', null, '0', '2023-05-15 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('1120', '1', 'testTask', 'renren', '0', null, '1', '2023-05-19 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('1121', '1', 'testTask', 'renren', '0', null, '0', '2023-05-19 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('1122', '1', 'testTask', 'renren', '0', null, '0', '2023-05-19 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('1123', '1', 'testTask', 'renren', '0', null, '1', '2023-05-19 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('1124', '1', 'testTask', 'renren', '0', null, '1', '2023-05-19 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('1125', '1', 'testTask', 'renren', '0', null, '0', '2023-05-19 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('1126', '1', 'testTask', 'renren', '0', null, '1', '2023-05-19 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('1127', '1', 'testTask', 'renren', '0', null, '1', '2023-05-19 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('1128', '1', 'testTask', 'renren', '0', null, '0', '2023-05-19 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('1129', '1', 'testTask', 'renren', '0', null, '1', '2023-05-19 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('1130', '1', 'testTask', 'renren', '0', null, '0', '2023-05-22 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('1131', '1', 'testTask', 'renren', '0', null, '1', '2023-05-22 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('1132', '1', 'testTask', 'renren', '0', null, '1', '2023-05-22 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('1133', '1', 'testTask', 'renren', '0', null, '2', '2023-05-22 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('1134', '1', 'testTask', 'renren', '0', null, '0', '2023-05-22 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('1135', '1', 'testTask', 'renren', '0', null, '1', '2023-05-22 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('1136', '1', 'testTask', 'renren', '0', null, '1', '2023-05-22 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('1137', '1', 'testTask', 'renren', '0', null, '0', '2023-05-22 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('1138', '1', 'testTask', 'renren', '0', null, '0', '2023-05-22 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('1139', '1', 'testTask', 'renren', '0', null, '1', '2023-05-22 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('1140', '1', 'testTask', 'renren', '0', null, '0', '2023-05-22 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('1141', '1', 'testTask', 'renren', '0', null, '0', '2023-05-22 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('1142', '1', 'testTask', 'renren', '0', null, '1', '2023-05-22 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('1143', '1', 'testTask', 'renren', '0', null, '1', '2023-05-22 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('1144', '1', 'testTask', 'renren', '0', null, '4', '2023-05-23 08:00:00');
INSERT INTO `schedule_job_log` VALUES ('1145', '1', 'testTask', 'renren', '0', null, '4', '2023-05-23 08:30:00');
INSERT INTO `schedule_job_log` VALUES ('1146', '1', 'testTask', 'renren', '0', null, '0', '2023-05-23 09:00:00');
INSERT INTO `schedule_job_log` VALUES ('1147', '1', 'testTask', 'renren', '0', null, '0', '2023-05-23 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('1148', '1', 'testTask', 'renren', '0', null, '0', '2023-05-23 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('1149', '1', 'testTask', 'renren', '0', null, '1', '2023-05-23 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('1150', '1', 'testTask', 'renren', '0', null, '1', '2023-05-23 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('1151', '1', 'testTask', 'renren', '0', null, '0', '2023-05-23 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('1152', '1', 'testTask', 'renren', '0', null, '0', '2023-05-23 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('1153', '1', 'testTask', 'renren', '0', null, '1', '2023-05-23 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('1154', '1', 'testTask', 'renren', '0', null, '1', '2023-05-23 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('1155', '1', 'testTask', 'renren', '0', null, '1', '2023-05-23 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('1156', '1', 'testTask', 'renren', '0', null, '1', '2023-05-23 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('1157', '1', 'testTask', 'renren', '0', null, '0', '2023-05-23 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('1158', '1', 'testTask', 'renren', '0', null, '0', '2023-05-23 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('1159', '1', 'testTask', 'renren', '0', null, '1', '2023-05-23 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('1160', '1', 'testTask', 'renren', '0', null, '4', '2023-05-23 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('1161', '1', 'testTask', 'renren', '0', null, '2', '2023-05-23 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('1162', '1', 'testTask', 'renren', '0', null, '0', '2023-05-25 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('1163', '1', 'testTask', 'renren', '0', null, '0', '2023-05-25 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('1164', '1', 'testTask', 'renren', '0', null, '2', '2023-05-26 09:30:00');
INSERT INTO `schedule_job_log` VALUES ('1165', '1', 'testTask', 'renren', '0', null, '2', '2023-05-26 10:00:00');
INSERT INTO `schedule_job_log` VALUES ('1166', '1', 'testTask', 'renren', '0', null, '0', '2023-05-26 10:30:00');
INSERT INTO `schedule_job_log` VALUES ('1167', '1', 'testTask', 'renren', '0', null, '0', '2023-05-26 11:00:00');
INSERT INTO `schedule_job_log` VALUES ('1168', '1', 'testTask', 'renren', '0', null, '0', '2023-05-26 11:30:00');
INSERT INTO `schedule_job_log` VALUES ('1169', '1', 'testTask', 'renren', '0', null, '1', '2023-05-26 12:00:00');
INSERT INTO `schedule_job_log` VALUES ('1170', '1', 'testTask', 'renren', '0', null, '0', '2023-05-26 12:30:00');
INSERT INTO `schedule_job_log` VALUES ('1171', '1', 'testTask', 'renren', '0', null, '0', '2023-05-26 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('1172', '1', 'testTask', 'renren', '0', null, '0', '2023-05-26 13:30:00');
INSERT INTO `schedule_job_log` VALUES ('1173', '1', 'testTask', 'renren', '0', null, '0', '2023-05-26 14:00:00');
INSERT INTO `schedule_job_log` VALUES ('1174', '1', 'testTask', 'renren', '0', null, '1', '2023-05-26 14:30:00');
INSERT INTO `schedule_job_log` VALUES ('1175', '1', 'testTask', 'renren', '0', null, '0', '2023-05-26 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('1176', '1', 'testTask', 'renren', '0', null, '0', '2023-05-26 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('1177', '1', 'testTask', 'renren', '0', null, '0', '2023-05-26 16:00:00');
INSERT INTO `schedule_job_log` VALUES ('1178', '1', 'testTask', 'renren', '0', null, '1', '2023-05-26 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('1179', '1', 'testTask', 'renren', '0', null, '0', '2023-05-26 17:00:00');
INSERT INTO `schedule_job_log` VALUES ('1180', '1', 'testTask', 'renren', '0', null, '1', '2023-05-26 17:30:00');
INSERT INTO `schedule_job_log` VALUES ('1181', '1', 'testTask', 'renren', '0', null, '2', '2023-05-26 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('1182', '1', 'testTask', 'renren', '0', null, '0', '2023-05-26 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('1183', '1', 'testTask', 'renren', '0', null, '0', '2023-05-26 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('1184', '1', 'testTask', 'renren', '0', null, '0', '2023-05-26 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('1185', '1', 'testTask', 'renren', '0', null, '1', '2023-05-26 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('1186', '1', 'testTask', 'renren', '0', null, '1', '2023-05-27 17:30:02');
INSERT INTO `schedule_job_log` VALUES ('1187', '1', 'testTask', 'renren', '0', null, '1', '2023-05-27 18:00:00');
INSERT INTO `schedule_job_log` VALUES ('1188', '1', 'testTask', 'renren', '0', null, '0', '2023-05-27 18:30:00');
INSERT INTO `schedule_job_log` VALUES ('1189', '1', 'testTask', 'renren', '0', null, '1', '2023-05-27 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('1190', '1', 'testTask', 'renren', '0', null, '1', '2023-05-27 19:30:00');
INSERT INTO `schedule_job_log` VALUES ('1191', '1', 'testTask', 'renren', '0', null, '1', '2023-05-27 20:00:00');
INSERT INTO `schedule_job_log` VALUES ('1192', '1', 'testTask', 'renren', '0', null, '1', '2023-05-27 20:30:00');
INSERT INTO `schedule_job_log` VALUES ('1193', '1', 'testTask', 'renren', '0', null, '1', '2023-05-27 21:00:00');
INSERT INTO `schedule_job_log` VALUES ('1194', '1', 'testTask', 'renren', '0', null, '0', '2023-05-27 21:30:00');
INSERT INTO `schedule_job_log` VALUES ('1195', '1', 'testTask', 'renren', '0', null, '5', '2023-05-27 22:00:00');
INSERT INTO `schedule_job_log` VALUES ('1196', '1', 'testTask', 'renren', '0', null, '1', '2023-05-27 22:30:00');
INSERT INTO `schedule_job_log` VALUES ('1197', '1', 'testTask', 'renren', '0', null, '0', '2023-05-27 23:00:00');
INSERT INTO `schedule_job_log` VALUES ('1198', '1', 'testTask', 'renren', '0', null, '2', '2023-05-31 11:30:00');

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha` (
  `uuid` char(36) NOT NULL COMMENT 'uuid',
  `code` varchar(6) NOT NULL COMMENT '验证码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统验证码';

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------
INSERT INTO `sys_captcha` VALUES ('0140642b-6cda-4971-8a96-4627e2823756', '6e7bp', '2023-05-26 11:21:37');
INSERT INTO `sys_captcha` VALUES ('01b51aed-5345-40a2-85a7-5f75c8a981c8', 'e34fn', '2023-05-14 16:09:59');
INSERT INTO `sys_captcha` VALUES ('0d9a655e-b38a-4052-8659-6009efab9eab', '62bb5', '2023-05-13 20:20:35');
INSERT INTO `sys_captcha` VALUES ('1c383b9d-f1c3-48d7-8319-41e79ef5a45f', 'n6ewy', '2023-05-09 09:44:22');
INSERT INTO `sys_captcha` VALUES ('24cb3a7d-ced4-4792-8370-aab54f178fac', '6pmpc', '2023-05-14 15:31:14');
INSERT INTO `sys_captcha` VALUES ('3c734d03-0097-4caa-8226-27e80ada2fe7', 'y6npe', '2023-05-14 15:32:38');
INSERT INTO `sys_captcha` VALUES ('557f50bf-0b2b-4b2e-8663-d60e58dc3bbd', 'pbbny', '2023-02-04 11:27:55');
INSERT INTO `sys_captcha` VALUES ('76a15c66-8e7c-48fb-8c7f-e2060b29d0e3', 'wm77e', '2023-04-01 09:44:05');
INSERT INTO `sys_captcha` VALUES ('a95a8ba1-20f2-4369-8a7d-2403cdfb30ee', 'ncwnn', '2023-05-26 11:21:37');
INSERT INTO `sys_captcha` VALUES ('b02158b8-25e9-4b08-846c-0338a1cc4dd9', 'cnx4f', '2023-02-09 13:09:28');
INSERT INTO `sys_captcha` VALUES ('b07cb892-9aff-4d03-8eb1-94ac46db74fb', '4yf6p', '2023-05-13 20:20:43');
INSERT INTO `sys_captcha` VALUES ('b3d180c6-33f2-46d3-8991-e16f330b154e', 'gaeax', '2023-02-06 16:35:33');
INSERT INTO `sys_captcha` VALUES ('b57a9916-eb0c-41a8-84da-746c8d8d409d', 'ep4dn', '2023-05-22 16:24:22');
INSERT INTO `sys_captcha` VALUES ('e3ed5d3f-2fd3-4efa-831c-376b33af686e', '6b6fp', '2023-02-06 16:46:03');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', '0', '云存储配置信息');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":101,\"parentId\":0,\"name\":\"用户管理\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"admin\",\"orderNum\":0,\"list\":[]}]', '14', '0:0:0:0:0:0:0:1', '2023-02-03 23:44:56');
INSERT INTO `sys_log` VALUES ('2', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":102,\"parentId\":0,\"name\":\"内容管理\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"shouye\",\"orderNum\":0,\"list\":[]}]', '32', '0:0:0:0:0:0:0:1', '2023-02-03 23:45:08');
INSERT INTO `sys_log` VALUES ('3', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":103,\"parentId\":0,\"name\":\"城市与区域\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"dangdifill\",\"orderNum\":0,\"list\":[]}]', '7', '0:0:0:0:0:0:0:1', '2023-02-03 23:45:26');
INSERT INTO `sys_log` VALUES ('4', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":104,\"parentId\":0,\"name\":\"房源特色\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"shoucangfill\",\"orderNum\":0,\"list\":[]}]', '8', '0:0:0:0:0:0:0:1', '2023-02-03 23:45:37');
INSERT INTO `sys_log` VALUES ('5', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":105,\"parentId\":0,\"name\":\"房源推广\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"daohang\",\"orderNum\":0,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-02-03 23:45:59');
INSERT INTO `sys_log` VALUES ('6', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":71,\"parentId\":103,\"name\":\"城市管理\",\"url\":\"house/housecity\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":6,\"list\":[]}]', '17', '0:0:0:0:0:0:0:1', '2023-02-03 23:46:31');
INSERT INTO `sys_log` VALUES ('7', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":31,\"parentId\":103,\"name\":\"区域管理\",\"url\":\"house/housearea\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":6,\"list\":[]}]', '10', '0:0:0:0:0:0:0:1', '2023-02-03 23:46:51');
INSERT INTO `sys_log` VALUES ('8', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":36,\"parentId\":104,\"name\":\"房屋设施表\",\"url\":\"house/basefacilities\",\"type\":1,\"icon\":\"config\",\"orderNum\":6,\"list\":[]}]', '9', '0:0:0:0:0:0:0:1', '2023-02-03 23:47:23');
INSERT INTO `sys_log` VALUES ('9', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":41,\"parentId\":104,\"name\":\"房屋亮点表\",\"url\":\"house/basehighlight\",\"type\":1,\"icon\":\"config\",\"orderNum\":6,\"list\":[]}]', '11', '0:0:0:0:0:0:0:1', '2023-02-03 23:47:33');
INSERT INTO `sys_log` VALUES ('10', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":46,\"parentId\":104,\"name\":\"房屋朝向表\",\"url\":\"house/baseorientation\",\"type\":1,\"icon\":\"config\",\"orderNum\":6,\"list\":[]}]', '9', '0:0:0:0:0:0:0:1', '2023-02-03 23:47:46');
INSERT INTO `sys_log` VALUES ('11', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":51,\"parentId\":104,\"name\":\"房屋出租要求表\",\"url\":\"house/baserentalrequirements\",\"type\":1,\"icon\":\"config\",\"orderNum\":6,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-02-03 23:47:58');
INSERT INTO `sys_log` VALUES ('12', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":56,\"parentId\":104,\"name\":\"房屋租金内容表\",\"url\":\"house/baserentcontent\",\"type\":1,\"icon\":\"config\",\"orderNum\":6,\"list\":[]}]', '9', '0:0:0:0:0:0:0:1', '2023-02-03 23:48:13');
INSERT INTO `sys_log` VALUES ('13', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":46,\"parentId\":104,\"name\":\"朝向种类管理\",\"url\":\"house/baseorientation\",\"type\":1,\"icon\":\"config\",\"orderNum\":6,\"list\":[]}]', '10', '0:0:0:0:0:0:0:1', '2023-02-03 23:48:41');
INSERT INTO `sys_log` VALUES ('14', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":46,\"parentId\":104,\"name\":\"朝向种类管理\",\"url\":\"house/baseorientation\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":6,\"list\":[]}]', '8', '0:0:0:0:0:0:0:1', '2023-02-03 23:48:52');
INSERT INTO `sys_log` VALUES ('15', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":56,\"parentId\":104,\"name\":\"租金内容管理\",\"url\":\"house/baserentcontent\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":6,\"list\":[]}]', '10', '0:0:0:0:0:0:0:1', '2023-02-03 23:49:03');
INSERT INTO `sys_log` VALUES ('16', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":36,\"parentId\":104,\"name\":\"设施种类管理\",\"url\":\"house/basefacilities\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":6,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-02-03 23:49:13');
INSERT INTO `sys_log` VALUES ('17', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":41,\"parentId\":104,\"name\":\"亮点种类管理\",\"url\":\"house/basehighlight\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":6,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-02-03 23:49:26');
INSERT INTO `sys_log` VALUES ('18', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":51,\"parentId\":104,\"name\":\"出租要求管理\",\"url\":\"house/baserentalrequirements\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":6,\"list\":[]}]', '8', '0:0:0:0:0:0:0:1', '2023-02-03 23:49:36');
INSERT INTO `sys_log` VALUES ('19', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":71,\"parentId\":103,\"name\":\"城市管理\",\"url\":\"house/housecity\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":7,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-02-04 11:23:30');
INSERT INTO `sys_log` VALUES ('20', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":71,\"parentId\":103,\"name\":\"城市管理\",\"url\":\"house/housecity\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":5,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-02-04 11:23:39');
INSERT INTO `sys_log` VALUES ('21', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '[{\"roleId\":1,\"roleName\":\"超级管理员\",\"remark\":\"全局管理\",\"createUserId\":1,\"menuIdList\":[1,2,15,16,17,18,3,19,20,21,22,4,23,24,25,26,5,6,7,8,9,10,11,12,13,14,27,30,61,62,63,64,65,66,67,68,69,70,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,29,101,102,103,71,72,73,74,75,31,32,33,34,35,104,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,105,-666666],\"createTime\":\"Feb 6, 2023 4:36:37 PM\"}]', '267', '0:0:0:0:0:0:0:1', '2023-02-06 16:36:37');
INSERT INTO `sys_log` VALUES ('22', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '[{\"roleId\":2,\"roleName\":\"系统管理员\",\"remark\":\"只能管理系统相关内容\",\"createUserId\":1,\"menuIdList\":[1,2,15,16,17,18,3,19,20,21,22,4,23,24,25,26,5,6,7,8,9,10,11,12,13,14,27,30,61,62,63,64,65,66,67,68,69,70,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,29,-666666],\"createTime\":\"Feb 6, 2023 4:37:10 PM\"}]', '83', '0:0:0:0:0:0:0:1', '2023-02-06 16:37:11');
INSERT INTO `sys_log` VALUES ('23', 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '[{\"userId\":2,\"username\":\"root\",\"password\":\"1f6430825c4513e514e5b89c8f1ba1f8203d86adee7de4616e2926fd4862c1ed\",\"salt\":\"vlTJHFjMQiJxn9qU3uh7\",\"email\":\"1104820805@qq.com\",\"mobile\":\"18870493682\",\"status\":1,\"roleIdList\":[1],\"createUserId\":1,\"createTime\":\"Feb 6, 2023 4:37:50 PM\"}]', '1552', '0:0:0:0:0:0:0:1', '2023-02-06 16:37:52');
INSERT INTO `sys_log` VALUES ('24', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '[{\"roleId\":3,\"roleName\":\"用户管理员\",\"remark\":\"管理用户以及商家相关信息\",\"createUserId\":1,\"menuIdList\":[101,-666666],\"createTime\":\"Feb 6, 2023 4:38:29 PM\"}]', '14', '0:0:0:0:0:0:0:1', '2023-02-06 16:38:30');
INSERT INTO `sys_log` VALUES ('25', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '[{\"roleId\":4,\"roleName\":\"房源内容管理员\",\"remark\":\"管理房源内容的相关消息\",\"createUserId\":1,\"menuIdList\":[102,-666666],\"createTime\":\"Feb 6, 2023 4:38:59 PM\"}]', '15', '0:0:0:0:0:0:0:1', '2023-02-06 16:38:59');
INSERT INTO `sys_log` VALUES ('26', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '[{\"roleId\":5,\"roleName\":\"城市管理员\",\"remark\":\"管理城市与区域的相关消息\",\"createUserId\":1,\"menuIdList\":[103,71,72,73,74,75,31,32,33,34,35,-666666],\"createTime\":\"Feb 6, 2023 4:39:25 PM\"}]', '25', '0:0:0:0:0:0:0:1', '2023-02-06 16:39:25');
INSERT INTO `sys_log` VALUES ('27', 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '[{\"userId\":3,\"username\":\"user\",\"password\":\"7c6a398f451a2ef0df22a660588289c0813c8932c1c3c77f5a091e23ac86165e\",\"salt\":\"UKngiUkO9wCTLTT6v0p5\",\"email\":\"user@gmail.com\",\"mobile\":\"18874523651\",\"status\":1,\"roleIdList\":[3],\"createUserId\":1,\"createTime\":\"Feb 6, 2023 4:39:58 PM\"}]', '14', '0:0:0:0:0:0:0:1', '2023-02-06 16:39:58');
INSERT INTO `sys_log` VALUES ('28', 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '[{\"userId\":4,\"username\":\"house\",\"password\":\"d935dbaeb85cefd8c91456454567398a05b0100e6ce9d313b7867c2f10c590fa\",\"salt\":\"w9gOnqOAZ6zbjY2a3ftp\",\"email\":\"house@gmail.com\",\"mobile\":\"18811254632\",\"status\":1,\"roleIdList\":[4],\"createUserId\":1,\"createTime\":\"Feb 6, 2023 4:42:27 PM\"}]', '12', '0:0:0:0:0:0:0:1', '2023-02-06 16:42:28');
INSERT INTO `sys_log` VALUES ('29', 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '[{\"userId\":5,\"username\":\"city\",\"password\":\"aaa75c0617e8ded6f7069cb83ef8e8d01f232fab9050f903484df25dd8b425ba\",\"salt\":\"MQuiRzXkHGESKO1sIvjc\",\"email\":\"city@gmail.com\",\"mobile\":\"18665214520\",\"status\":1,\"roleIdList\":[5],\"createUserId\":1,\"createTime\":\"Feb 6, 2023 4:42:56 PM\"}]', '11', '0:0:0:0:0:0:0:1', '2023-02-06 16:42:56');
INSERT INTO `sys_log` VALUES ('30', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":61,\"parentId\":102,\"name\":\"房屋基础信息表\",\"url\":\"house/housebaseinfo\",\"type\":1,\"icon\":\"config\",\"orderNum\":6,\"list\":[]}]', '20', '0:0:0:0:0:0:0:1', '2023-02-09 13:05:47');
INSERT INTO `sys_log` VALUES ('31', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":81,\"parentId\":102,\"name\":\"房源详细介绍表\",\"url\":\"house/housedetailedinfo\",\"type\":1,\"icon\":\"config\",\"orderNum\":6,\"list\":[]}]', '11', '0:0:0:0:0:0:0:1', '2023-02-09 13:06:03');
INSERT INTO `sys_log` VALUES ('32', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":76,\"parentId\":102,\"name\":\"房源联系电话表\",\"url\":\"house/housecontactinfo\",\"type\":1,\"icon\":\"config\",\"orderNum\":6,\"list\":[]}]', '9', '0:0:0:0:0:0:0:1', '2023-02-09 13:06:11');
INSERT INTO `sys_log` VALUES ('33', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":96,\"parentId\":102,\"name\":\"房源状态表\",\"url\":\"house/housestate\",\"type\":1,\"icon\":\"config\",\"orderNum\":6,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-02-09 13:06:19');
INSERT INTO `sys_log` VALUES ('34', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":86,\"parentId\":102,\"name\":\"房源图片表\",\"url\":\"house/houseimage\",\"type\":1,\"icon\":\"config\",\"orderNum\":6,\"list\":[]}]', '9', '0:0:0:0:0:0:0:1', '2023-02-09 13:06:28');
INSERT INTO `sys_log` VALUES ('35', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":91,\"parentId\":102,\"name\":\"房源租金信息表\",\"url\":\"house/houserentinfo\",\"type\":1,\"icon\":\"config\",\"orderNum\":6,\"list\":[]}]', '6', '0:0:0:0:0:0:0:1', '2023-02-09 13:06:38');
INSERT INTO `sys_log` VALUES ('36', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":66,\"parentId\":102,\"name\":\"房屋类别表\",\"url\":\"house/housecategory\",\"type\":1,\"icon\":\"config\",\"orderNum\":6,\"list\":[]}]', '7', '0:0:0:0:0:0:0:1', '2023-02-09 13:06:47');
INSERT INTO `sys_log` VALUES ('37', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":66,\"parentId\":102,\"name\":\"房屋类别表\",\"url\":\"house/housecategory\",\"type\":1,\"icon\":\"config\",\"orderNum\":7,\"list\":[]}]', '10', '0:0:0:0:0:0:0:1', '2023-02-09 13:07:04');
INSERT INTO `sys_log` VALUES ('38', 'root', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '[{\"roleId\":6,\"roleName\":\"审核人员\",\"remark\":\"\",\"createUserId\":2,\"menuIdList\":[102,61,62,63,64,65,81,82,83,84,85,91,92,93,94,95,76,77,78,79,80,86,87,88,89,90,96,97,98,99,100,66,67,68,69,70,-666666],\"createTime\":\"Mar 13, 2023 10:38:33 AM\"}]', '182', '0:0:0:0:0:0:0:1', '2023-03-13 10:38:33');
INSERT INTO `sys_log` VALUES ('39', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":36,\"parentId\":104,\"name\":\"设施种类管理\",\"url\":\"base/basefacilities\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":6,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-03-13 11:03:56');
INSERT INTO `sys_log` VALUES ('40', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":41,\"parentId\":104,\"name\":\"亮点种类管理\",\"url\":\"base/basehighlight\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":6,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-03-13 11:04:02');
INSERT INTO `sys_log` VALUES ('41', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":46,\"parentId\":104,\"name\":\"朝向种类管理\",\"url\":\"base/baseorientation\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":6,\"list\":[]}]', '10', '0:0:0:0:0:0:0:1', '2023-03-13 11:04:07');
INSERT INTO `sys_log` VALUES ('42', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":51,\"parentId\":104,\"name\":\"出租要求管理\",\"url\":\"base/baserentalrequirements\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":6,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-03-13 11:04:13');
INSERT INTO `sys_log` VALUES ('43', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":56,\"parentId\":104,\"name\":\"租金内容管理\",\"url\":\"base/baserentcontent\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":6,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-03-13 11:04:19');
INSERT INTO `sys_log` VALUES ('44', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":71,\"parentId\":103,\"name\":\"城市管理\",\"url\":\"city/housecity\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":5,\"list\":[]}]', '6', '0:0:0:0:0:0:0:1', '2023-03-13 11:06:06');
INSERT INTO `sys_log` VALUES ('45', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":31,\"parentId\":103,\"name\":\"区域管理\",\"url\":\"city/housearea\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":6,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-03-13 11:06:10');
INSERT INTO `sys_log` VALUES ('46', 'root', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":111,\"parentId\":102,\"name\":\"待审核\",\"url\":\"house/examineList\",\"perms\":\"\",\"type\":1,\"icon\":\"sousuo\",\"orderNum\":0,\"list\":[]}]', '7', '0:0:0:0:0:0:0:1', '2023-03-13 11:08:32');
INSERT INTO `sys_log` VALUES ('47', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":111,\"parentId\":102,\"name\":\"待审核\",\"url\":\"house/examineList\",\"perms\":\"\",\"type\":1,\"icon\":\"sousuo\",\"orderNum\":1,\"list\":[]}]', '11', '0:0:0:0:0:0:0:1', '2023-03-13 11:10:50');
INSERT INTO `sys_log` VALUES ('48', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":61,\"parentId\":105,\"name\":\"房屋基础信息表\",\"url\":\"house/housebaseinfo\",\"type\":1,\"icon\":\"config\",\"orderNum\":1,\"list\":[]}]', '7', '0:0:0:0:0:0:0:1', '2023-03-13 11:12:32');
INSERT INTO `sys_log` VALUES ('49', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":111,\"parentId\":102,\"name\":\"待审核房源\",\"url\":\"house/examineList\",\"perms\":\"\",\"type\":1,\"icon\":\"sousuo\",\"orderNum\":1,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-03-13 11:13:34');
INSERT INTO `sys_log` VALUES ('50', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":111,\"parentId\":0,\"name\":\"待审核房源\",\"url\":\"house/examineList\",\"perms\":\"\",\"type\":1,\"icon\":\"sousuo\",\"orderNum\":1,\"list\":[]}]', '11', '0:0:0:0:0:0:0:1', '2023-03-13 11:15:03');
INSERT INTO `sys_log` VALUES ('51', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":111,\"parentId\":102,\"name\":\"待审核房源\",\"url\":\"house/examineList\",\"perms\":\"\",\"type\":1,\"icon\":\"sousuo\",\"orderNum\":1,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-03-13 11:15:21');
INSERT INTO `sys_log` VALUES ('52', 'root', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":112,\"parentId\":111,\"name\":\"添加\",\"perms\":\"house:examineList:list,house:examineList:info\",\"type\":2,\"orderNum\":6,\"list\":[]}]', '20', '0:0:0:0:0:0:0:1', '2023-03-13 11:16:02');
INSERT INTO `sys_log` VALUES ('53', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":81,\"parentId\":102,\"name\":\"房源详细介绍表\",\"url\":\"house/examineList\",\"type\":1,\"icon\":\"config\",\"orderNum\":2,\"list\":[]}]', '10', '0:0:0:0:0:0:0:1', '2023-03-13 11:19:18');
INSERT INTO `sys_log` VALUES ('54', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":81,\"parentId\":102,\"name\":\"待审核房源\",\"url\":\"house/examineList\",\"type\":1,\"icon\":\"config\",\"orderNum\":1,\"list\":[]}]', '10', '0:0:0:0:0:0:0:1', '2023-03-13 11:19:57');
INSERT INTO `sys_log` VALUES ('55', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[111]', '1', '0:0:0:0:0:0:0:1', '2023-03-13 11:20:04');
INSERT INTO `sys_log` VALUES ('56', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[112]', '12', '0:0:0:0:0:0:0:1', '2023-03-13 11:20:06');
INSERT INTO `sys_log` VALUES ('57', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[111]', '10', '0:0:0:0:0:0:0:1', '2023-03-13 11:20:11');
INSERT INTO `sys_log` VALUES ('58', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":82,\"parentId\":81,\"name\":\"查看\",\"perms\":\"house:examineList:list,house:examineList:info\",\"type\":2,\"orderNum\":6,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-03-13 11:20:23');
INSERT INTO `sys_log` VALUES ('59', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":83,\"parentId\":81,\"name\":\"新增\",\"perms\":\"house:examineList:save\",\"type\":2,\"orderNum\":6,\"list\":[]}]', '6', '0:0:0:0:0:0:0:1', '2023-03-13 11:20:30');
INSERT INTO `sys_log` VALUES ('60', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":84,\"parentId\":81,\"name\":\"修改\",\"perms\":\"house:examineList:update\",\"type\":2,\"orderNum\":6,\"list\":[]}]', '9', '0:0:0:0:0:0:0:1', '2023-03-13 11:20:36');
INSERT INTO `sys_log` VALUES ('61', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":85,\"parentId\":81,\"name\":\"删除\",\"perms\":\"house:examineList:delete\",\"type\":2,\"orderNum\":6,\"list\":[]}]', '9', '0:0:0:0:0:0:0:1', '2023-03-13 11:20:41');
INSERT INTO `sys_log` VALUES ('62', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":81,\"parentId\":102,\"name\":\"待审核房源\",\"url\":\"house/examineList\",\"type\":1,\"icon\":\"sousuo\",\"orderNum\":1,\"list\":[]}]', '7', '0:0:0:0:0:0:0:1', '2023-03-13 18:11:20');
INSERT INTO `sys_log` VALUES ('63', 'root', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":113,\"parentId\":102,\"name\":\"审核\",\"url\":\"/house/examine/:baseInfoId\",\"perms\":\"\",\"type\":1,\"orderNum\":6,\"list\":[]}]', '13', '0:0:0:0:0:0:0:1', '2023-03-19 10:24:49');
INSERT INTO `sys_log` VALUES ('64', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[113]', '17', '0:0:0:0:0:0:0:1', '2023-03-19 10:29:39');
INSERT INTO `sys_log` VALUES ('65', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[95]', '18', '0:0:0:0:0:0:0:1', '2023-03-19 16:47:30');
INSERT INTO `sys_log` VALUES ('66', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[94]', '18', '0:0:0:0:0:0:0:1', '2023-03-19 16:47:37');
INSERT INTO `sys_log` VALUES ('67', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[93]', '15', '0:0:0:0:0:0:0:1', '2023-03-19 16:47:42');
INSERT INTO `sys_log` VALUES ('68', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[92]', '13', '0:0:0:0:0:0:0:1', '2023-03-19 16:47:47');
INSERT INTO `sys_log` VALUES ('69', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[91]', '16', '0:0:0:0:0:0:0:1', '2023-03-19 16:47:50');
INSERT INTO `sys_log` VALUES ('70', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[77]', '16', '0:0:0:0:0:0:0:1', '2023-03-19 16:47:55');
INSERT INTO `sys_log` VALUES ('71', 'root', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":3,\"roleName\":\"用户管理员\",\"remark\":\"管理用户以及商家相关信息\",\"createUserId\":2,\"menuIdList\":[-666666]}]', '135', '0:0:0:0:0:0:0:1', '2023-03-19 16:48:14');
INSERT INTO `sys_log` VALUES ('72', 'root', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":4,\"roleName\":\"房源内容管理员\",\"remark\":\"管理房源内容的相关消息\",\"createUserId\":2,\"menuIdList\":[-666666]}]', '18', '0:0:0:0:0:0:0:1', '2023-03-19 16:48:25');
INSERT INTO `sys_log` VALUES ('73', 'root', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":6,\"roleName\":\"审核人员\",\"remark\":\"审核房源信息\",\"createUserId\":2,\"menuIdList\":[-666666]}]', '19', '0:0:0:0:0:0:0:1', '2023-03-19 16:48:35');
INSERT INTO `sys_log` VALUES ('74', 'root', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":5,\"roleName\":\"城市管理员\",\"remark\":\"管理城市与区域的相关消息\",\"createUserId\":2,\"menuIdList\":[-666666]}]', '16', '0:0:0:0:0:0:0:1', '2023-03-19 16:48:49');
INSERT INTO `sys_log` VALUES ('75', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[78]', '16', '0:0:0:0:0:0:0:1', '2023-03-19 16:49:07');
INSERT INTO `sys_log` VALUES ('76', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[79]', '15', '0:0:0:0:0:0:0:1', '2023-03-19 16:49:12');
INSERT INTO `sys_log` VALUES ('77', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[80]', '21', '0:0:0:0:0:0:0:1', '2023-03-19 16:49:16');
INSERT INTO `sys_log` VALUES ('78', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[76]', '16', '0:0:0:0:0:0:0:1', '2023-03-19 16:49:20');
INSERT INTO `sys_log` VALUES ('79', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[87]', '15', '0:0:0:0:0:0:0:1', '2023-03-19 16:49:23');
INSERT INTO `sys_log` VALUES ('80', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[88]', '18', '0:0:0:0:0:0:0:1', '2023-03-19 16:49:27');
INSERT INTO `sys_log` VALUES ('81', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[89]', '15', '0:0:0:0:0:0:0:1', '2023-03-19 16:49:33');
INSERT INTO `sys_log` VALUES ('82', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[90]', '15', '0:0:0:0:0:0:0:1', '2023-03-19 16:49:38');
INSERT INTO `sys_log` VALUES ('83', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[86]', '16', '0:0:0:0:0:0:0:1', '2023-03-19 16:49:42');
INSERT INTO `sys_log` VALUES ('84', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[97]', '13', '0:0:0:0:0:0:0:1', '2023-03-19 16:49:46');
INSERT INTO `sys_log` VALUES ('85', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[98]', '17', '0:0:0:0:0:0:0:1', '2023-03-19 16:49:52');
INSERT INTO `sys_log` VALUES ('86', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[99]', '17', '0:0:0:0:0:0:0:1', '2023-03-19 16:49:56');
INSERT INTO `sys_log` VALUES ('87', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[100]', '13', '0:0:0:0:0:0:0:1', '2023-03-19 16:50:00');
INSERT INTO `sys_log` VALUES ('88', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[96]', '15', '0:0:0:0:0:0:0:1', '2023-03-19 16:50:04');
INSERT INTO `sys_log` VALUES ('89', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[67]', '14', '0:0:0:0:0:0:0:1', '2023-03-19 16:50:08');
INSERT INTO `sys_log` VALUES ('90', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[68]', '16', '0:0:0:0:0:0:0:1', '2023-03-19 16:50:12');
INSERT INTO `sys_log` VALUES ('91', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[69]', '15', '0:0:0:0:0:0:0:1', '2023-03-19 16:50:16');
INSERT INTO `sys_log` VALUES ('92', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[70]', '17', '0:0:0:0:0:0:0:1', '2023-03-19 16:50:20');
INSERT INTO `sys_log` VALUES ('93', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[66]', '17', '0:0:0:0:0:0:0:1', '2023-03-19 16:50:23');
INSERT INTO `sys_log` VALUES ('94', 'root', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":114,\"parentId\":102,\"name\":\"房源信息\",\"url\":\"house\\u0027houseInfoList\",\"perms\":\"\",\"type\":1,\"icon\":\"shouye\",\"orderNum\":0,\"list\":[]}]', '9', '0:0:0:0:0:0:0:1', '2023-03-19 17:00:28');
INSERT INTO `sys_log` VALUES ('95', 'root', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":114,\"parentId\":102,\"name\":\"房源信息\",\"url\":\"house/houseInfoList\",\"perms\":\"\",\"type\":1,\"icon\":\"shouye\",\"orderNum\":0,\"list\":[]}]', '11', '0:0:0:0:0:0:0:1', '2023-03-19 17:00:44');
INSERT INTO `sys_log` VALUES ('96', 'root', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":115,\"parentId\":103,\"name\":\"1\",\"url\":\"/a\",\"perms\":\"\",\"type\":1,\"icon\":\"\",\"orderNum\":0,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-03-19 17:00:59');
INSERT INTO `sys_log` VALUES ('97', 'root', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[115]', '9', '0:0:0:0:0:0:0:1', '2023-03-19 17:01:22');
INSERT INTO `sys_log` VALUES ('98', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":2,\"roleName\":\"系统管理员\",\"remark\":\"只能管理系统相关内容\",\"createUserId\":1,\"menuIdList\":[1,2,15,16,17,18,3,19,20,21,22,4,23,24,25,26,5,6,7,8,9,10,11,12,13,14,27,30,29,102,114,81,82,83,84,85,105,61,62,63,64,65,-666666]}]', '58', '0:0:0:0:0:0:0:1', '2023-03-19 17:02:32');
INSERT INTO `sys_log` VALUES ('99', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":114,\"parentId\":102,\"name\":\"已经审核房源\",\"url\":\"house/houseInfoList\",\"perms\":\"\",\"type\":1,\"icon\":\"shouye\",\"orderNum\":0,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-03-19 17:09:47');
INSERT INTO `sys_log` VALUES ('100', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":114,\"parentId\":102,\"name\":\"已审核房源\",\"url\":\"house/houseInfoList\",\"perms\":\"\",\"type\":1,\"icon\":\"shouye\",\"orderNum\":0,\"list\":[]}]', '10', '0:0:0:0:0:0:0:1', '2023-03-19 17:10:00');
INSERT INTO `sys_log` VALUES ('101', 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":116,\"parentId\":102,\"name\":\"所有房源\",\"url\":\"/house/allList\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0,\"list\":[]}]', '8', '0:0:0:0:0:0:0:1', '2023-03-19 19:22:29');
INSERT INTO `sys_log` VALUES ('102', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":116,\"parentId\":102,\"name\":\"所有房源\",\"url\":\"/house/allList\",\"perms\":\"\",\"type\":1,\"icon\":\"menu\",\"orderNum\":0,\"list\":[]}]', '3', '0:0:0:0:0:0:0:1', '2023-03-19 19:22:45');
INSERT INTO `sys_log` VALUES ('103', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":114,\"parentId\":102,\"name\":\"已审核房源\",\"url\":\"house/houseInfoList\",\"perms\":\"\",\"type\":1,\"icon\":\"shouye\",\"orderNum\":1,\"list\":[]}]', '13', '0:0:0:0:0:0:0:1', '2023-03-19 19:22:50');
INSERT INTO `sys_log` VALUES ('104', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":81,\"parentId\":102,\"name\":\"待审核房源\",\"url\":\"house/examineList\",\"type\":1,\"icon\":\"sousuo\",\"orderNum\":2,\"list\":[]}]', '11', '0:0:0:0:0:0:0:1', '2023-03-19 19:22:55');
INSERT INTO `sys_log` VALUES ('105', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":2,\"parentId\":101,\"name\":\"管理员列表\",\"url\":\"sys/user\",\"type\":1,\"icon\":\"admin\",\"orderNum\":1,\"list\":[]}]', '16', '0:0:0:0:0:0:0:1', '2023-04-01 10:33:37');
INSERT INTO `sys_log` VALUES ('106', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":3,\"parentId\":101,\"name\":\"角色管理\",\"url\":\"sys/role\",\"type\":1,\"icon\":\"role\",\"orderNum\":2,\"list\":[]}]', '10', '0:0:0:0:0:0:0:1', '2023-04-01 10:33:43');
INSERT INTO `sys_log` VALUES ('107', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":5,\"roleName\":\"城市管理员\",\"remark\":\"管理城市与区域的相关消息\",\"createUserId\":1,\"menuIdList\":[103,71,72,73,74,75,31,32,33,34,35,-666666]}]', '147', '0:0:0:0:0:0:0:1', '2023-04-01 10:34:39');
INSERT INTO `sys_log` VALUES ('108', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":3,\"roleName\":\"用户管理员\",\"remark\":\"管理用户以及商家相关信息\",\"createUserId\":1,\"menuIdList\":[106,107,108,109,110,-666666,101]}]', '23', '0:0:0:0:0:0:0:1', '2023-04-01 10:34:53');
INSERT INTO `sys_log` VALUES ('109', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":4,\"roleName\":\"房源内容管理员\",\"remark\":\"管理房源内容的相关消息\",\"createUserId\":1,\"menuIdList\":[102,116,114,81,82,83,84,85,104,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,-666666]}]', '50', '0:0:0:0:0:0:0:1', '2023-04-01 10:35:22');
INSERT INTO `sys_log` VALUES ('110', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":6,\"roleName\":\"审核人员\",\"remark\":\"审核房源信息\",\"createUserId\":1,\"menuIdList\":[81,82,83,84,85,-666666,102]}]', '21', '0:0:0:0:0:0:0:1', '2023-04-01 10:35:33');
INSERT INTO `sys_log` VALUES ('111', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":2,\"roleName\":\"系统管理员\",\"remark\":\"只能管理系统相关内容\",\"createUserId\":1,\"menuIdList\":[106,107,108,109,110,102,116,114,81,82,83,84,85,103,71,72,73,74,75,31,32,33,34,35,104,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,105,61,62,63,64,65,-666666,101]}]', '70', '0:0:0:0:0:0:0:1', '2023-04-01 10:36:02');
INSERT INTO `sys_log` VALUES ('112', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '[{\"userId\":1,\"username\":\"admin\",\"salt\":\"YzcmCZNvbXocrsz9dm8e\",\"email\":\"root@renren.io\",\"mobile\":\"13612345678\",\"status\":1,\"roleIdList\":[1],\"createUserId\":1}]', '31', '0:0:0:0:0:0:0:1', '2023-04-01 10:36:47');
INSERT INTO `sys_log` VALUES ('113', 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '[{\"userId\":2,\"username\":\"root\",\"salt\":\"vlTJHFjMQiJxn9qU3uh7\",\"email\":\"1104820805@qq.com\",\"mobile\":\"18870493682\",\"status\":1,\"roleIdList\":[2],\"createUserId\":1}]', '10', '0:0:0:0:0:0:0:1', '2023-04-01 10:37:01');
INSERT INTO `sys_log` VALUES ('114', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":2,\"parentId\":101,\"name\":\"管理员列表\",\"url\":\"sys/user\",\"type\":1,\"icon\":\"admin\",\"orderNum\":2,\"list\":[]}]', '5', '0:0:0:0:0:0:0:1', '2023-04-01 10:37:40');
INSERT INTO `sys_log` VALUES ('115', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":3,\"parentId\":101,\"name\":\"角色管理\",\"url\":\"sys/role\",\"type\":1,\"icon\":\"role\",\"orderNum\":1,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-04-01 10:37:45');
INSERT INTO `sys_log` VALUES ('116', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":106,\"parentId\":101,\"name\":\"用户信息\",\"url\":\"user/userbaseinfo\",\"type\":1,\"icon\":\"geren\",\"orderNum\":6,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-04-01 10:37:57');
INSERT INTO `sys_log` VALUES ('117', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[65]', '30', '0:0:0:0:0:0:0:1', '2023-04-16 10:56:36');
INSERT INTO `sys_log` VALUES ('118', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[64]', '21', '0:0:0:0:0:0:0:1', '2023-04-16 10:56:42');
INSERT INTO `sys_log` VALUES ('119', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[63]', '19', '0:0:0:0:0:0:0:1', '2023-04-16 10:56:48');
INSERT INTO `sys_log` VALUES ('120', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[62]', '20', '0:0:0:0:0:0:0:1', '2023-04-16 10:56:51');
INSERT INTO `sys_log` VALUES ('121', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[61]', '17', '0:0:0:0:0:0:0:1', '2023-04-16 10:56:55');
INSERT INTO `sys_log` VALUES ('122', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":117,\"parentId\":105,\"name\":\"推广活动表\",\"url\":\"promotion/housepromotionfunction\",\"type\":1,\"icon\":\"daohang\",\"orderNum\":6,\"list\":[]}]', '13', '0:0:0:0:0:0:0:1', '2023-04-16 10:57:15');
INSERT INTO `sys_log` VALUES ('123', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":117,\"parentId\":105,\"name\":\"推广活动表\",\"url\":\"promotion/housepromotionfunction\",\"type\":1,\"icon\":\"tixing\",\"orderNum\":6,\"list\":[]}]', '10', '0:0:0:0:0:0:0:1', '2023-04-16 10:57:51');
INSERT INTO `sys_log` VALUES ('124', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":117,\"parentId\":105,\"name\":\"推广活动\",\"url\":\"promotion/housepromotionfunction\",\"type\":1,\"icon\":\"tixing\",\"orderNum\":6,\"list\":[]}]', '12', '0:0:0:0:0:0:0:1', '2023-04-16 16:30:45');
INSERT INTO `sys_log` VALUES ('125', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":2,\"roleName\":\"系统管理员\",\"remark\":\"只能管理系统相关内容\",\"createUserId\":1,\"menuIdList\":[101,3,19,20,21,22,2,15,16,17,18,106,107,108,109,110,102,116,114,81,82,83,84,85,103,71,72,73,74,75,31,32,33,34,35,104,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,105,117,118,119,120,121,122,123,124,125,126,-666666]}]', '227', '0:0:0:0:0:0:0:1', '2023-04-28 15:58:22');
INSERT INTO `sys_log` VALUES ('126', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":3,\"roleName\":\"用户管理员\",\"remark\":\"管理用户以及商家相关信息\",\"createUserId\":1,\"menuIdList\":[101,3,19,20,21,22,2,15,16,17,18,106,107,108,109,110,-666666]}]', '31', '0:0:0:0:0:0:0:1', '2023-04-28 15:58:32');
INSERT INTO `sys_log` VALUES ('127', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":4,\"roleName\":\"房源内容管理员\",\"remark\":\"管理房源内容的相关消息\",\"createUserId\":1,\"menuIdList\":[102,116,114,81,82,83,84,85,104,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,-666666]}]', '49', '0:0:0:0:0:0:0:1', '2023-04-28 15:58:46');
INSERT INTO `sys_log` VALUES ('128', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '[{\"roleId\":7,\"roleName\":\"推广套餐管理员\",\"remark\":\"管理房源推广套餐的设置\",\"createUserId\":1,\"menuIdList\":[105,117,118,119,120,121,122,123,124,125,126,-666666],\"createTime\":\"Apr 28, 2023 3:59:35 PM\"}]', '24', '0:0:0:0:0:0:0:1', '2023-04-28 15:59:35');
INSERT INTO `sys_log` VALUES ('129', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":1,\"roleName\":\"超级管理员\",\"remark\":\"全局管理\",\"createUserId\":1,\"menuIdList\":[4,23,24,25,26,101,3,19,20,21,22,2,15,16,17,18,106,107,108,109,110,102,116,114,81,82,83,84,85,103,71,72,73,74,75,31,32,33,34,35,104,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,105,117,118,119,120,121,122,123,124,125,126,-666666,1]}]', '114', '0:0:0:0:0:0:0:1', '2023-04-28 16:07:18');
INSERT INTO `sys_log` VALUES ('130', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":1,\"roleName\":\"超级管理员\",\"remark\":\"全局管理\",\"createUserId\":1,\"menuIdList\":[1,4,23,24,25,26,5,6,7,8,9,10,11,12,13,14,27,30,29,101,3,19,20,21,22,2,15,16,17,18,106,107,108,109,110,102,116,114,81,82,83,84,85,103,71,72,73,74,75,31,32,33,34,35,104,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,105,117,118,119,120,121,122,123,124,125,126,-666666]}]', '104', '0:0:0:0:0:0:0:1', '2023-04-28 16:07:50');
INSERT INTO `sys_log` VALUES ('131', 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":2,\"roleName\":\"系统管理员\",\"remark\":\"只能管理系统相关内容\",\"createUserId\":1,\"menuIdList\":[4,23,24,25,26,101,3,19,20,21,22,2,15,16,17,18,106,107,108,109,110,102,116,114,81,82,83,84,85,103,71,72,73,74,75,31,32,33,34,35,104,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,105,117,118,119,120,121,122,123,124,125,126,-666666,1]}]', '88', '0:0:0:0:0:0:0:1', '2023-04-28 16:08:00');
INSERT INTO `sys_log` VALUES ('132', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[5]', '0', '0:0:0:0:0:0:0:1', '2023-05-22 16:18:19');
INSERT INTO `sys_log` VALUES ('133', 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[7]', '0', '0:0:0:0:0:0:0:1', '2023-05-22 16:18:28');
INSERT INTO `sys_log` VALUES ('134', 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":105,\"parentId\":0,\"name\":\"房源推荐\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"daohang\",\"orderNum\":0,\"list\":[]}]', '53', '0:0:0:0:0:0:0:1', '2023-05-31 11:32:34');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'system', '0');
INSERT INTO `sys_menu` VALUES ('2', '101', '管理员列表', 'sys/user', null, '1', 'admin', '2');
INSERT INTO `sys_menu` VALUES ('3', '101', '角色管理', 'sys/role', null, '1', 'role', '1');
INSERT INTO `sys_menu` VALUES ('4', '1', '菜单管理', 'sys/menu', null, '1', 'menu', '3');
INSERT INTO `sys_menu` VALUES ('15', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('16', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('17', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('18', '2', '删除', null, 'sys:user:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('19', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('20', '3', '新增', null, 'sys:role:save,sys:menu:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('21', '3', '修改', null, 'sys:role:update,sys:menu:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('22', '3', '删除', null, 'sys:role:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('23', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('24', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('25', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('26', '4', '删除', null, 'sys:menu:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('31', '103', '区域管理', 'city/housearea', null, '1', 'zonghe', '6');
INSERT INTO `sys_menu` VALUES ('32', '31', '查看', null, 'house:housearea:list,house:housearea:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('33', '31', '新增', null, 'house:housearea:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('34', '31', '修改', null, 'house:housearea:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('35', '31', '删除', null, 'house:housearea:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('36', '104', '设施种类', 'base/basefacilities', null, '1', 'zonghe', '6');
INSERT INTO `sys_menu` VALUES ('37', '36', '查看', null, 'house:basefacilities:list,house:basefacilities:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('38', '36', '新增', null, 'house:basefacilities:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('39', '36', '修改', null, 'house:basefacilities:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('40', '36', '删除', null, 'house:basefacilities:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('41', '104', '亮点种类', 'base/basehighlight', null, '1', 'zonghe', '6');
INSERT INTO `sys_menu` VALUES ('42', '41', '查看', null, 'house:basehighlight:list,house:basehighlight:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('43', '41', '新增', null, 'house:basehighlight:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('44', '41', '修改', null, 'house:basehighlight:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('45', '41', '删除', null, 'house:basehighlight:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('46', '104', '朝向种类', 'base/baseorientation', null, '1', 'zonghe', '6');
INSERT INTO `sys_menu` VALUES ('47', '46', '查看', null, 'house:baseorientation:list,house:baseorientation:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('48', '46', '新增', null, 'house:baseorientation:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('49', '46', '修改', null, 'house:baseorientation:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('50', '46', '删除', null, 'house:baseorientation:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('51', '104', '出租要求', 'base/baserentalrequirements', null, '1', 'zonghe', '6');
INSERT INTO `sys_menu` VALUES ('52', '51', '查看', null, 'house:baserentalrequirements:list,house:baserentalrequirements:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('53', '51', '新增', null, 'house:baserentalrequirements:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('54', '51', '修改', null, 'house:baserentalrequirements:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('55', '51', '删除', null, 'house:baserentalrequirements:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('56', '104', '租金内容', 'base/baserentcontent', null, '1', 'zonghe', '6');
INSERT INTO `sys_menu` VALUES ('57', '56', '查看', null, 'house:baserentcontent:list,house:baserentcontent:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('58', '56', '新增', null, 'house:baserentcontent:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('59', '56', '修改', null, 'house:baserentcontent:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('60', '56', '删除', null, 'house:baserentcontent:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('71', '103', '城市管理', 'city/housecity', null, '1', 'zonghe', '5');
INSERT INTO `sys_menu` VALUES ('72', '71', '查看', null, 'house:housecity:list,house:housecity:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('73', '71', '新增', null, 'house:housecity:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('74', '71', '修改', null, 'house:housecity:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('75', '71', '删除', null, 'house:housecity:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('81', '102', '待审核房源', 'house/examineList', null, '1', 'sousuo', '2');
INSERT INTO `sys_menu` VALUES ('82', '81', '查看', null, 'house:examineList:list,house:examineList:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('83', '81', '新增', null, 'house:examineList:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('84', '81', '修改', null, 'house:examineList:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('85', '81', '删除', null, 'house:examineList:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('101', '0', '用户管理', '', '', '0', 'admin', '0');
INSERT INTO `sys_menu` VALUES ('102', '0', '内容管理', '', '', '0', 'shouye', '0');
INSERT INTO `sys_menu` VALUES ('103', '0', '城市与区域', '', '', '0', 'dangdifill', '0');
INSERT INTO `sys_menu` VALUES ('104', '0', '房源特色', '', '', '0', 'shoucangfill', '0');
INSERT INTO `sys_menu` VALUES ('105', '0', '房源推荐', '', '', '0', 'daohang', '0');
INSERT INTO `sys_menu` VALUES ('106', '101', '用户信息', 'user/userbaseinfo', null, '1', 'geren', '6');
INSERT INTO `sys_menu` VALUES ('107', '106', '查看', null, 'user:userbaseinfo:list,user:userbaseinfo:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('108', '106', '新增', null, 'user:userbaseinfo:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('109', '106', '修改', null, 'user:userbaseinfo:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('110', '106', '删除', null, 'user:userbaseinfo:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('114', '102', '已审核房源', 'house/houseInfoList', '', '1', 'shouye', '1');
INSERT INTO `sys_menu` VALUES ('116', '102', '所有房源', '/house/allList', '', '1', 'menu', '0');
INSERT INTO `sys_menu` VALUES ('117', '105', '推荐活动', 'promotion/housepromotionfunction', null, '1', 'tixing', '6');
INSERT INTO `sys_menu` VALUES ('118', '117', '查看', null, 'promotion:housepromotionfunction:list,promotion:housepromotionfunction:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('119', '117', '新增', null, 'promotion:housepromotionfunction:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('120', '117', '修改', null, 'promotion:housepromotionfunction:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('121', '117', '删除', null, 'promotion:housepromotionfunction:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('122', '105', '推荐订单', 'promotion/housepromotionorder', null, '1', 'config', '6');
INSERT INTO `sys_menu` VALUES ('123', '122', '查看', null, 'promotion:housepromotionorder:list,promotion:housepromotionorder:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('124', '122', '新增', null, 'promotion:housepromotionorder:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('125', '122', '修改', null, 'promotion:housepromotionorder:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('126', '122', '删除', null, 'promotion:housepromotionorder:delete', '2', null, '6');

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文件上传';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '全局管理', '1', '2023-02-06 16:36:37');
INSERT INTO `sys_role` VALUES ('2', '系统管理员', '只能管理系统相关内容', '1', '2023-02-06 16:37:11');
INSERT INTO `sys_role` VALUES ('3', '用户管理员', '管理用户以及商家相关信息', '1', '2023-02-06 16:38:30');
INSERT INTO `sys_role` VALUES ('4', '房源内容管理员', '管理房源内容的相关消息', '1', '2023-02-06 16:38:59');
INSERT INTO `sys_role` VALUES ('5', '城市管理员', '管理城市与区域的相关消息', '1', '2023-02-06 16:39:25');
INSERT INTO `sys_role` VALUES ('6', '审核人员', '审核房源信息', '1', '2023-03-13 10:38:33');
INSERT INTO `sys_role` VALUES ('7', '推广套餐管理员', '管理房源推广套餐的设置', '1', '2023-04-28 15:59:35');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=777 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('271', '5', '103');
INSERT INTO `sys_role_menu` VALUES ('272', '5', '71');
INSERT INTO `sys_role_menu` VALUES ('273', '5', '72');
INSERT INTO `sys_role_menu` VALUES ('274', '5', '73');
INSERT INTO `sys_role_menu` VALUES ('275', '5', '74');
INSERT INTO `sys_role_menu` VALUES ('276', '5', '75');
INSERT INTO `sys_role_menu` VALUES ('277', '5', '31');
INSERT INTO `sys_role_menu` VALUES ('278', '5', '32');
INSERT INTO `sys_role_menu` VALUES ('279', '5', '33');
INSERT INTO `sys_role_menu` VALUES ('280', '5', '34');
INSERT INTO `sys_role_menu` VALUES ('281', '5', '35');
INSERT INTO `sys_role_menu` VALUES ('282', '5', '-666666');
INSERT INTO `sys_role_menu` VALUES ('325', '6', '81');
INSERT INTO `sys_role_menu` VALUES ('326', '6', '82');
INSERT INTO `sys_role_menu` VALUES ('327', '6', '83');
INSERT INTO `sys_role_menu` VALUES ('328', '6', '84');
INSERT INTO `sys_role_menu` VALUES ('329', '6', '85');
INSERT INTO `sys_role_menu` VALUES ('330', '6', '-666666');
INSERT INTO `sys_role_menu` VALUES ('331', '6', '102');
INSERT INTO `sys_role_menu` VALUES ('463', '3', '101');
INSERT INTO `sys_role_menu` VALUES ('464', '3', '3');
INSERT INTO `sys_role_menu` VALUES ('465', '3', '19');
INSERT INTO `sys_role_menu` VALUES ('466', '3', '20');
INSERT INTO `sys_role_menu` VALUES ('467', '3', '21');
INSERT INTO `sys_role_menu` VALUES ('468', '3', '22');
INSERT INTO `sys_role_menu` VALUES ('469', '3', '2');
INSERT INTO `sys_role_menu` VALUES ('470', '3', '15');
INSERT INTO `sys_role_menu` VALUES ('471', '3', '16');
INSERT INTO `sys_role_menu` VALUES ('472', '3', '17');
INSERT INTO `sys_role_menu` VALUES ('473', '3', '18');
INSERT INTO `sys_role_menu` VALUES ('474', '3', '106');
INSERT INTO `sys_role_menu` VALUES ('475', '3', '107');
INSERT INTO `sys_role_menu` VALUES ('476', '3', '108');
INSERT INTO `sys_role_menu` VALUES ('477', '3', '109');
INSERT INTO `sys_role_menu` VALUES ('478', '3', '110');
INSERT INTO `sys_role_menu` VALUES ('479', '3', '-666666');
INSERT INTO `sys_role_menu` VALUES ('480', '4', '102');
INSERT INTO `sys_role_menu` VALUES ('481', '4', '116');
INSERT INTO `sys_role_menu` VALUES ('482', '4', '114');
INSERT INTO `sys_role_menu` VALUES ('483', '4', '81');
INSERT INTO `sys_role_menu` VALUES ('484', '4', '82');
INSERT INTO `sys_role_menu` VALUES ('485', '4', '83');
INSERT INTO `sys_role_menu` VALUES ('486', '4', '84');
INSERT INTO `sys_role_menu` VALUES ('487', '4', '85');
INSERT INTO `sys_role_menu` VALUES ('488', '4', '104');
INSERT INTO `sys_role_menu` VALUES ('489', '4', '36');
INSERT INTO `sys_role_menu` VALUES ('490', '4', '37');
INSERT INTO `sys_role_menu` VALUES ('491', '4', '38');
INSERT INTO `sys_role_menu` VALUES ('492', '4', '39');
INSERT INTO `sys_role_menu` VALUES ('493', '4', '40');
INSERT INTO `sys_role_menu` VALUES ('494', '4', '41');
INSERT INTO `sys_role_menu` VALUES ('495', '4', '42');
INSERT INTO `sys_role_menu` VALUES ('496', '4', '43');
INSERT INTO `sys_role_menu` VALUES ('497', '4', '44');
INSERT INTO `sys_role_menu` VALUES ('498', '4', '45');
INSERT INTO `sys_role_menu` VALUES ('499', '4', '46');
INSERT INTO `sys_role_menu` VALUES ('500', '4', '47');
INSERT INTO `sys_role_menu` VALUES ('501', '4', '48');
INSERT INTO `sys_role_menu` VALUES ('502', '4', '49');
INSERT INTO `sys_role_menu` VALUES ('503', '4', '50');
INSERT INTO `sys_role_menu` VALUES ('504', '4', '51');
INSERT INTO `sys_role_menu` VALUES ('505', '4', '52');
INSERT INTO `sys_role_menu` VALUES ('506', '4', '53');
INSERT INTO `sys_role_menu` VALUES ('507', '4', '54');
INSERT INTO `sys_role_menu` VALUES ('508', '4', '55');
INSERT INTO `sys_role_menu` VALUES ('509', '4', '56');
INSERT INTO `sys_role_menu` VALUES ('510', '4', '57');
INSERT INTO `sys_role_menu` VALUES ('511', '4', '58');
INSERT INTO `sys_role_menu` VALUES ('512', '4', '59');
INSERT INTO `sys_role_menu` VALUES ('513', '4', '60');
INSERT INTO `sys_role_menu` VALUES ('514', '4', '-666666');
INSERT INTO `sys_role_menu` VALUES ('515', '7', '105');
INSERT INTO `sys_role_menu` VALUES ('516', '7', '117');
INSERT INTO `sys_role_menu` VALUES ('517', '7', '118');
INSERT INTO `sys_role_menu` VALUES ('518', '7', '119');
INSERT INTO `sys_role_menu` VALUES ('519', '7', '120');
INSERT INTO `sys_role_menu` VALUES ('520', '7', '121');
INSERT INTO `sys_role_menu` VALUES ('521', '7', '122');
INSERT INTO `sys_role_menu` VALUES ('522', '7', '123');
INSERT INTO `sys_role_menu` VALUES ('523', '7', '124');
INSERT INTO `sys_role_menu` VALUES ('524', '7', '125');
INSERT INTO `sys_role_menu` VALUES ('525', '7', '126');
INSERT INTO `sys_role_menu` VALUES ('526', '7', '-666666');
INSERT INTO `sys_role_menu` VALUES ('606', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('607', '1', '4');
INSERT INTO `sys_role_menu` VALUES ('608', '1', '23');
INSERT INTO `sys_role_menu` VALUES ('609', '1', '24');
INSERT INTO `sys_role_menu` VALUES ('610', '1', '25');
INSERT INTO `sys_role_menu` VALUES ('611', '1', '26');
INSERT INTO `sys_role_menu` VALUES ('612', '1', '5');
INSERT INTO `sys_role_menu` VALUES ('613', '1', '6');
INSERT INTO `sys_role_menu` VALUES ('614', '1', '7');
INSERT INTO `sys_role_menu` VALUES ('615', '1', '8');
INSERT INTO `sys_role_menu` VALUES ('616', '1', '9');
INSERT INTO `sys_role_menu` VALUES ('617', '1', '10');
INSERT INTO `sys_role_menu` VALUES ('618', '1', '11');
INSERT INTO `sys_role_menu` VALUES ('619', '1', '12');
INSERT INTO `sys_role_menu` VALUES ('620', '1', '13');
INSERT INTO `sys_role_menu` VALUES ('621', '1', '14');
INSERT INTO `sys_role_menu` VALUES ('622', '1', '27');
INSERT INTO `sys_role_menu` VALUES ('623', '1', '30');
INSERT INTO `sys_role_menu` VALUES ('624', '1', '29');
INSERT INTO `sys_role_menu` VALUES ('625', '1', '101');
INSERT INTO `sys_role_menu` VALUES ('626', '1', '3');
INSERT INTO `sys_role_menu` VALUES ('627', '1', '19');
INSERT INTO `sys_role_menu` VALUES ('628', '1', '20');
INSERT INTO `sys_role_menu` VALUES ('629', '1', '21');
INSERT INTO `sys_role_menu` VALUES ('630', '1', '22');
INSERT INTO `sys_role_menu` VALUES ('631', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('632', '1', '15');
INSERT INTO `sys_role_menu` VALUES ('633', '1', '16');
INSERT INTO `sys_role_menu` VALUES ('634', '1', '17');
INSERT INTO `sys_role_menu` VALUES ('635', '1', '18');
INSERT INTO `sys_role_menu` VALUES ('636', '1', '106');
INSERT INTO `sys_role_menu` VALUES ('637', '1', '107');
INSERT INTO `sys_role_menu` VALUES ('638', '1', '108');
INSERT INTO `sys_role_menu` VALUES ('639', '1', '109');
INSERT INTO `sys_role_menu` VALUES ('640', '1', '110');
INSERT INTO `sys_role_menu` VALUES ('641', '1', '102');
INSERT INTO `sys_role_menu` VALUES ('642', '1', '116');
INSERT INTO `sys_role_menu` VALUES ('643', '1', '114');
INSERT INTO `sys_role_menu` VALUES ('644', '1', '81');
INSERT INTO `sys_role_menu` VALUES ('645', '1', '82');
INSERT INTO `sys_role_menu` VALUES ('646', '1', '83');
INSERT INTO `sys_role_menu` VALUES ('647', '1', '84');
INSERT INTO `sys_role_menu` VALUES ('648', '1', '85');
INSERT INTO `sys_role_menu` VALUES ('649', '1', '103');
INSERT INTO `sys_role_menu` VALUES ('650', '1', '71');
INSERT INTO `sys_role_menu` VALUES ('651', '1', '72');
INSERT INTO `sys_role_menu` VALUES ('652', '1', '73');
INSERT INTO `sys_role_menu` VALUES ('653', '1', '74');
INSERT INTO `sys_role_menu` VALUES ('654', '1', '75');
INSERT INTO `sys_role_menu` VALUES ('655', '1', '31');
INSERT INTO `sys_role_menu` VALUES ('656', '1', '32');
INSERT INTO `sys_role_menu` VALUES ('657', '1', '33');
INSERT INTO `sys_role_menu` VALUES ('658', '1', '34');
INSERT INTO `sys_role_menu` VALUES ('659', '1', '35');
INSERT INTO `sys_role_menu` VALUES ('660', '1', '104');
INSERT INTO `sys_role_menu` VALUES ('661', '1', '36');
INSERT INTO `sys_role_menu` VALUES ('662', '1', '37');
INSERT INTO `sys_role_menu` VALUES ('663', '1', '38');
INSERT INTO `sys_role_menu` VALUES ('664', '1', '39');
INSERT INTO `sys_role_menu` VALUES ('665', '1', '40');
INSERT INTO `sys_role_menu` VALUES ('666', '1', '41');
INSERT INTO `sys_role_menu` VALUES ('667', '1', '42');
INSERT INTO `sys_role_menu` VALUES ('668', '1', '43');
INSERT INTO `sys_role_menu` VALUES ('669', '1', '44');
INSERT INTO `sys_role_menu` VALUES ('670', '1', '45');
INSERT INTO `sys_role_menu` VALUES ('671', '1', '46');
INSERT INTO `sys_role_menu` VALUES ('672', '1', '47');
INSERT INTO `sys_role_menu` VALUES ('673', '1', '48');
INSERT INTO `sys_role_menu` VALUES ('674', '1', '49');
INSERT INTO `sys_role_menu` VALUES ('675', '1', '50');
INSERT INTO `sys_role_menu` VALUES ('676', '1', '51');
INSERT INTO `sys_role_menu` VALUES ('677', '1', '52');
INSERT INTO `sys_role_menu` VALUES ('678', '1', '53');
INSERT INTO `sys_role_menu` VALUES ('679', '1', '54');
INSERT INTO `sys_role_menu` VALUES ('680', '1', '55');
INSERT INTO `sys_role_menu` VALUES ('681', '1', '56');
INSERT INTO `sys_role_menu` VALUES ('682', '1', '57');
INSERT INTO `sys_role_menu` VALUES ('683', '1', '58');
INSERT INTO `sys_role_menu` VALUES ('684', '1', '59');
INSERT INTO `sys_role_menu` VALUES ('685', '1', '60');
INSERT INTO `sys_role_menu` VALUES ('686', '1', '105');
INSERT INTO `sys_role_menu` VALUES ('687', '1', '117');
INSERT INTO `sys_role_menu` VALUES ('688', '1', '118');
INSERT INTO `sys_role_menu` VALUES ('689', '1', '119');
INSERT INTO `sys_role_menu` VALUES ('690', '1', '120');
INSERT INTO `sys_role_menu` VALUES ('691', '1', '121');
INSERT INTO `sys_role_menu` VALUES ('692', '1', '122');
INSERT INTO `sys_role_menu` VALUES ('693', '1', '123');
INSERT INTO `sys_role_menu` VALUES ('694', '1', '124');
INSERT INTO `sys_role_menu` VALUES ('695', '1', '125');
INSERT INTO `sys_role_menu` VALUES ('696', '1', '126');
INSERT INTO `sys_role_menu` VALUES ('697', '1', '-666666');
INSERT INTO `sys_role_menu` VALUES ('698', '2', '4');
INSERT INTO `sys_role_menu` VALUES ('699', '2', '23');
INSERT INTO `sys_role_menu` VALUES ('700', '2', '24');
INSERT INTO `sys_role_menu` VALUES ('701', '2', '25');
INSERT INTO `sys_role_menu` VALUES ('702', '2', '26');
INSERT INTO `sys_role_menu` VALUES ('703', '2', '101');
INSERT INTO `sys_role_menu` VALUES ('704', '2', '3');
INSERT INTO `sys_role_menu` VALUES ('705', '2', '19');
INSERT INTO `sys_role_menu` VALUES ('706', '2', '20');
INSERT INTO `sys_role_menu` VALUES ('707', '2', '21');
INSERT INTO `sys_role_menu` VALUES ('708', '2', '22');
INSERT INTO `sys_role_menu` VALUES ('709', '2', '2');
INSERT INTO `sys_role_menu` VALUES ('710', '2', '15');
INSERT INTO `sys_role_menu` VALUES ('711', '2', '16');
INSERT INTO `sys_role_menu` VALUES ('712', '2', '17');
INSERT INTO `sys_role_menu` VALUES ('713', '2', '18');
INSERT INTO `sys_role_menu` VALUES ('714', '2', '106');
INSERT INTO `sys_role_menu` VALUES ('715', '2', '107');
INSERT INTO `sys_role_menu` VALUES ('716', '2', '108');
INSERT INTO `sys_role_menu` VALUES ('717', '2', '109');
INSERT INTO `sys_role_menu` VALUES ('718', '2', '110');
INSERT INTO `sys_role_menu` VALUES ('719', '2', '102');
INSERT INTO `sys_role_menu` VALUES ('720', '2', '116');
INSERT INTO `sys_role_menu` VALUES ('721', '2', '114');
INSERT INTO `sys_role_menu` VALUES ('722', '2', '81');
INSERT INTO `sys_role_menu` VALUES ('723', '2', '82');
INSERT INTO `sys_role_menu` VALUES ('724', '2', '83');
INSERT INTO `sys_role_menu` VALUES ('725', '2', '84');
INSERT INTO `sys_role_menu` VALUES ('726', '2', '85');
INSERT INTO `sys_role_menu` VALUES ('727', '2', '103');
INSERT INTO `sys_role_menu` VALUES ('728', '2', '71');
INSERT INTO `sys_role_menu` VALUES ('729', '2', '72');
INSERT INTO `sys_role_menu` VALUES ('730', '2', '73');
INSERT INTO `sys_role_menu` VALUES ('731', '2', '74');
INSERT INTO `sys_role_menu` VALUES ('732', '2', '75');
INSERT INTO `sys_role_menu` VALUES ('733', '2', '31');
INSERT INTO `sys_role_menu` VALUES ('734', '2', '32');
INSERT INTO `sys_role_menu` VALUES ('735', '2', '33');
INSERT INTO `sys_role_menu` VALUES ('736', '2', '34');
INSERT INTO `sys_role_menu` VALUES ('737', '2', '35');
INSERT INTO `sys_role_menu` VALUES ('738', '2', '104');
INSERT INTO `sys_role_menu` VALUES ('739', '2', '36');
INSERT INTO `sys_role_menu` VALUES ('740', '2', '37');
INSERT INTO `sys_role_menu` VALUES ('741', '2', '38');
INSERT INTO `sys_role_menu` VALUES ('742', '2', '39');
INSERT INTO `sys_role_menu` VALUES ('743', '2', '40');
INSERT INTO `sys_role_menu` VALUES ('744', '2', '41');
INSERT INTO `sys_role_menu` VALUES ('745', '2', '42');
INSERT INTO `sys_role_menu` VALUES ('746', '2', '43');
INSERT INTO `sys_role_menu` VALUES ('747', '2', '44');
INSERT INTO `sys_role_menu` VALUES ('748', '2', '45');
INSERT INTO `sys_role_menu` VALUES ('749', '2', '46');
INSERT INTO `sys_role_menu` VALUES ('750', '2', '47');
INSERT INTO `sys_role_menu` VALUES ('751', '2', '48');
INSERT INTO `sys_role_menu` VALUES ('752', '2', '49');
INSERT INTO `sys_role_menu` VALUES ('753', '2', '50');
INSERT INTO `sys_role_menu` VALUES ('754', '2', '51');
INSERT INTO `sys_role_menu` VALUES ('755', '2', '52');
INSERT INTO `sys_role_menu` VALUES ('756', '2', '53');
INSERT INTO `sys_role_menu` VALUES ('757', '2', '54');
INSERT INTO `sys_role_menu` VALUES ('758', '2', '55');
INSERT INTO `sys_role_menu` VALUES ('759', '2', '56');
INSERT INTO `sys_role_menu` VALUES ('760', '2', '57');
INSERT INTO `sys_role_menu` VALUES ('761', '2', '58');
INSERT INTO `sys_role_menu` VALUES ('762', '2', '59');
INSERT INTO `sys_role_menu` VALUES ('763', '2', '60');
INSERT INTO `sys_role_menu` VALUES ('764', '2', '105');
INSERT INTO `sys_role_menu` VALUES ('765', '2', '117');
INSERT INTO `sys_role_menu` VALUES ('766', '2', '118');
INSERT INTO `sys_role_menu` VALUES ('767', '2', '119');
INSERT INTO `sys_role_menu` VALUES ('768', '2', '120');
INSERT INTO `sys_role_menu` VALUES ('769', '2', '121');
INSERT INTO `sys_role_menu` VALUES ('770', '2', '122');
INSERT INTO `sys_role_menu` VALUES ('771', '2', '123');
INSERT INTO `sys_role_menu` VALUES ('772', '2', '124');
INSERT INTO `sys_role_menu` VALUES ('773', '2', '125');
INSERT INTO `sys_role_menu` VALUES ('774', '2', '126');
INSERT INTO `sys_role_menu` VALUES ('775', '2', '-666666');
INSERT INTO `sys_role_menu` VALUES ('776', '2', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', '1', '1', '2016-11-11 11:11:11');
INSERT INTO `sys_user` VALUES ('2', 'root', '1f6430825c4513e514e5b89c8f1ba1f8203d86adee7de4616e2926fd4862c1ed', 'vlTJHFjMQiJxn9qU3uh7', '1104820805@qq.com', '18870493682', '1', '1', '2023-02-06 16:37:51');
INSERT INTO `sys_user` VALUES ('3', 'user', '7c6a398f451a2ef0df22a660588289c0813c8932c1c3c77f5a091e23ac86165e', 'UKngiUkO9wCTLTT6v0p5', 'user@gmail.com', '18874523651', '1', '2', '2023-02-06 16:39:58');
INSERT INTO `sys_user` VALUES ('4', 'house', 'd935dbaeb85cefd8c91456454567398a05b0100e6ce9d313b7867c2f10c590fa', 'w9gOnqOAZ6zbjY2a3ftp', 'house@gmail.com', '18811254632', '1', '2', '2023-02-06 16:42:28');
INSERT INTO `sys_user` VALUES ('5', 'city', 'aaa75c0617e8ded6f7069cb83ef8e8d01f232fab9050f903484df25dd8b425ba', 'MQuiRzXkHGESKO1sIvjc', 'city@gmail.com', '18665214520', '1', '2', '2023-02-06 16:42:56');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('2', '3', '3');
INSERT INTO `sys_user_role` VALUES ('3', '4', '4');
INSERT INTO `sys_user_role` VALUES ('4', '5', '5');
INSERT INTO `sys_user_role` VALUES ('5', '1', '1');
INSERT INTO `sys_user_role` VALUES ('6', '2', '2');

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户Token';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES ('1', 'add7d3d44b74b188c8da262929571b8e', '2023-05-31 23:31:36', '2023-05-31 11:31:36');
INSERT INTO `sys_user_token` VALUES ('2', '01f177ef54d964334bb5750d1e120a06', '2023-04-29 04:08:12', '2023-04-28 16:08:12');
INSERT INTO `sys_user_token` VALUES ('3', '9e48d2290eb361a4808da90e7ae1624c', '2023-02-07 04:40:10', '2023-02-06 16:40:10');
INSERT INTO `sys_user_token` VALUES ('4', 'd034c8b51975c0792996dbc2fcda6593', '2023-05-23 04:19:34', '2023-05-22 16:19:34');
INSERT INTO `sys_user_token` VALUES ('5', '37654df9768a8d3b45d635cb951249f2', '2023-02-07 04:43:16', '2023-02-06 16:43:16');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');
