/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : db_cp_admin

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 19/07/2019 14:55:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮id',
  `parent_id` bigint(20) NOT NULL COMMENT '上级菜单id',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单/按钮名称',
  `url` varchar(100) DEFAULT NULL COMMENT '菜单url',
  `perms` text COMMENT '权限标识',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `type` char(2) NOT NULL COMMENT '类型 0菜单 1按钮',
  `status` char(1) NOT NULL COMMENT '状态 1-启用 0-禁用',
  `order_num` bigint(20) DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='菜单信息表';

-- ----------------------------
-- Records of t_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_menu` VALUES (1, 0, '首页', '', '', '', '0', '1', 1, '2019-07-19 14:55:16', '2019-07-19 14:55:16');
INSERT INTO `t_menu` VALUES (2, 0, '内容管理', '', '', '', '0', '1', 2, '2019-07-19 14:55:17', '2019-07-19 14:55:17');
INSERT INTO `t_menu` VALUES (3, 0, '订单管理', '', '', '', '0', '1', 3, '2019-07-19 14:55:18', '2019-07-19 14:55:18');
INSERT INTO `t_menu` VALUES (4, 0, '用户管理', '', '', '', '0', '1', 4, '2019-07-19 14:55:19', '2019-07-19 14:55:19');
INSERT INTO `t_menu` VALUES (5, 0, '系统管理', '', '', '', '0', '1', 5, '2019-07-19 14:55:20', '2019-07-19 14:55:20');
INSERT INTO `t_menu` VALUES (6, 2, '推荐管理', '', '', '', '0', '1', 1, '2019-07-19 14:55:20', '2019-07-19 14:55:20');
INSERT INTO `t_menu` VALUES (7, 2, '商品管理', '', '', '', '0', '1', 2, '2019-07-19 14:55:21', '2019-07-19 14:55:21');
INSERT INTO `t_menu` VALUES (8, 2, '分类管理', '', '', '', '0', '1', 3, '2019-07-19 14:55:22', '2019-07-19 14:55:22');
INSERT INTO `t_menu` VALUES (9, 2, '地区管理', '', '', '', '0', '1', 4, '2019-07-19 14:55:23', '2019-07-19 14:55:23');
INSERT INTO `t_menu` VALUES (10, 3, '订单列表', NULL, NULL, NULL, '0', '1', 1, '2019-07-19 14:55:24', '2019-07-19 14:55:24');
INSERT INTO `t_menu` VALUES (11, 4, '用户列表', NULL, NULL, NULL, '0', '1', 1, '2019-07-19 14:55:25', '2019-07-19 14:55:25');
INSERT INTO `t_menu` VALUES (12, 5, '版本管理', NULL, NULL, NULL, '0', '1', 1, '2019-07-19 14:55:26', '2019-07-19 14:55:26');
INSERT INTO `t_menu` VALUES (13, 5, '系统用户', NULL, NULL, NULL, '0', '1', 2, '2019-07-19 14:55:27', '2019-07-19 14:55:27');
COMMIT;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `role_code` varchar(20) NOT NULL COMMENT '角色编码',
  `remark` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `status` char(1) NOT NULL COMMENT '状态 1-启用 0-禁用',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='角色信息表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
BEGIN;
INSERT INTO `t_role` VALUES (1, '超级管理员', 'admin', '超级管理员', '1', '2019-07-19 14:55:08', '2019-07-19 14:55:08');
COMMIT;

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单/按钮id',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='角色菜单信息表';

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `t_role_menu` VALUES (1, 1, 1, '2018-01-01 12:00:00', '2018-01-01 12:00:00');
INSERT INTO `t_role_menu` VALUES (2, 1, 2, '2018-01-01 12:00:00', '2018-01-01 12:00:00');
INSERT INTO `t_role_menu` VALUES (3, 1, 3, '2018-01-01 12:00:00', '2018-01-01 12:00:00');
INSERT INTO `t_role_menu` VALUES (4, 1, 4, '2018-01-01 12:00:00', '2018-01-01 12:00:00');
INSERT INTO `t_role_menu` VALUES (5, 1, 5, '2018-01-01 12:00:00', '2018-01-01 12:00:00');
INSERT INTO `t_role_menu` VALUES (6, 1, 6, '2018-01-01 12:00:00', '2018-01-01 12:00:00');
INSERT INTO `t_role_menu` VALUES (7, 1, 7, '2018-01-01 12:00:00', '2018-01-01 12:00:00');
INSERT INTO `t_role_menu` VALUES (8, 1, 8, '2018-01-01 12:00:00', '2018-01-01 12:00:00');
INSERT INTO `t_role_menu` VALUES (9, 1, 9, '2019-07-04 23:43:58', '2019-07-04 23:43:58');
INSERT INTO `t_role_menu` VALUES (10, 1, 10, '2019-07-18 08:52:40', '2019-07-18 08:52:43');
INSERT INTO `t_role_menu` VALUES (11, 1, 11, '2019-07-18 08:52:53', '2019-07-18 08:52:55');
INSERT INTO `t_role_menu` VALUES (12, 1, 12, '2019-07-18 08:53:05', '2019-07-18 08:53:07');
INSERT INTO `t_role_menu` VALUES (13, 1, 13, '2019-07-18 08:53:18', '2019-07-18 08:53:20');
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `account` varchar(32) NOT NULL COMMENT '账号：唯一标识',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号码',
  `status` char(1) NOT NULL COMMENT '状态 0锁定 1有效',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '性别 0男 1女',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像',
  `last_login_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最近访问时间',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '111', 'mrbird@hotmail.com', '18996115970', '1', '0', 'default.jpg', '2019-07-05 22:25:55', '2019-07-05 22:25:55', '2019-07-05 22:25:55');
COMMIT;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
BEGIN;
INSERT INTO `t_user_role` VALUES (1, 1, 1, '2018-01-01 12:00:00', '2018-01-01 12:00:00');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
