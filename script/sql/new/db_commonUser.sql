/*
 Navicat Premium Data Transfer

 Source Server         : db_localhost_mysql
 Source Server Type    : MySQL
 Source Server Version : 100902
 Source Host           : localhost:3306
 Source Schema         : db_commonUser

 Target Server Type    : MySQL
 Target Server Version : 100902
 File Encoding         : 65001

 Date: 19/09/2022 15:46:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名称',
  `phone` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '手机号',
  `icon` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `wechat` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '微信号',
  `sex` tinyint(1) DEFAULT 1 COMMENT '性别:1.男;2.女',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '用户状态：1：正常，2：停用',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `balance` decimal(10,2) DEFAULT 0.00 COMMENT '余额',
  `invite_code` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邀请码',
  `inviter` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邀请人',
  `viper` tinyint(1) DEFAULT 2 COMMENT '是否为vip:1.是;2.否',
  `deleted` int(11) DEFAULT 0 COMMENT '逻辑删除:是否已删除（1：未删除，2：已删除）',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `modify_time` datetime DEFAULT current_timestamp() COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `idx_phone` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;
