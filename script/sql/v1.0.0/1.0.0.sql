DROP DATABASE IF EXISTS `db_backstage`;

CREATE DATABASE  `qishuoshuo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

USE `db_backstage`;

CREATE TABLE `sys_menu` (
                            `menu_id` bigint NOT NULL,
                            `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '菜单名称',
                            `permission` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '菜单权限标识',
                            `path` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '前端URL',
                            `parent_id` bigint DEFAULT NULL COMMENT '父菜单ID',
                            `icon` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '图标',
                            `sort_order` int NOT NULL DEFAULT '0' COMMENT '排序值',
                            `keep_alive` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '0' COMMENT '0-开启，1- 关闭',
                            `type` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '菜单类型 （0菜单 1权限）',
                            `deleted` tinyint(1) DEFAULT '1' COMMENT '逻辑删除标记(1--正常 2--删除)',
                            `creator` bigint DEFAULT NULL COMMENT '创建人',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `modifier` bigint DEFAULT NULL COMMENT '修改人',
                            `modify_time` datetime DEFAULT NULL COMMENT '更新时间',
                            PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='菜单权限表';

CREATE TABLE `sys_role` (
                            `role_id` bigint NOT NULL COMMENT 'id',
                            `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色名',
                            `role_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色编码',
                            `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述',
                            `deleted` tinyint(1) DEFAULT '1' COMMENT '逻辑删除标记(1--正常 2--删除)',
                            `creator` bigint DEFAULT '0' COMMENT '创建人',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `modifier` bigint DEFAULT '0' COMMENT '修改人',
                            `modify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色表';

CREATE TABLE `sys_role_menu` (
                                 `role_id` bigint NOT NULL,
                                 `menu_id` bigint NOT NULL,
                                 `deleted` tinyint(1) DEFAULT '1' COMMENT '逻辑删除标记(1--正常 2--删除)',
                                 `creator` bigint DEFAULT '0' COMMENT '创建人',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `modifier` bigint DEFAULT '0' COMMENT '修改人',
                                 `modify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色菜单表';

CREATE TABLE `sys_user` (
                            `user_id` bigint NOT NULL COMMENT '用户id',
                            `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名称',
                            `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机号',
                            `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '电子邮箱',
                            `icon` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
                            `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
                            `status` tinyint(1) DEFAULT '1' COMMENT '用户状态：1：正常，2：停用',
                            `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
                            `province_code` int DEFAULT NULL COMMENT '省代码',
                            `province` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '省',
                            `city_code` int DEFAULT NULL COMMENT '市代码',
                            `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '市',
                            `district_code` int DEFAULT NULL COMMENT '区代码',
                            `district` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '区',
                            `deleted` tinyint(1) DEFAULT '1' COMMENT '逻辑删除标记(1--正常 2--删除)',
                            `creator` bigint DEFAULT '0' COMMENT '创建人',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `modifier` bigint DEFAULT '0' COMMENT '修改人',
                            `modify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户表';

CREATE TABLE `sys_user_role` (
                                 `user_id` bigint NOT NULL,
                                 `role_id` bigint NOT NULL,
                                 `deleted` tinyint(1) DEFAULT '1' COMMENT '逻辑删除标记(1--正常 2--删除)',
                                 `creator` bigint DEFAULT '0' COMMENT '创建人',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `modifier` bigint DEFAULT '0' COMMENT '修改人',
                                 `modify_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户角色表';

INSERT INTO `db_backstage`.`sys_role` (`role_id`, `role_name`, `role_code`, `role_desc`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1, '超级管理员', 'SUPER_ADMIN', NULL, 1, 0, '2022-08-01 11:12:43', 0, '2022-08-01 11:12:43');

INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1000, '权限管理', NULL, '/admin', -1, 'icon-quanxianguanli', 1, '0', '0', 1, 0, '2018-09-28 08:29:53', 0, '2020-03-11 23:58:18');
INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1100, '用户管理', NULL, '/admin/user/index', 1000, 'icon-yonghuguanli', 0, '0', '0', 1, 0, '2017-11-02 22:24:37', 0, '2020-03-12 00:12:57');
INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1101, '用户新增', 'sys_user_add', NULL, 1100, NULL, 0, '0', '1', 1, 0, '2017-11-08 09:52:09', 0, '2021-05-25 06:48:34');
INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1102, '用户修改', 'sys_user_edit', NULL, 1100, NULL, 0, '0', '1', 1, 0, '2017-11-08 09:52:48', 0, '2021-05-25 06:48:34');
INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1103, '用户删除', 'sys_user_del', NULL, 1100, NULL, 0, '0', '1', 1, 0, '2017-11-08 09:54:01', 0, '2021-05-25 06:48:34');
INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1104, '导入导出', 'sys_user_import_export', NULL, 1100, NULL, 0, '0', '1', 1, 0, '2017-11-08 09:54:01', 0, '2021-05-25 06:48:34');
INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1200, '菜单管理', NULL, '/admin/menu/index', 1000, 'icon-caidanguanli', 1, '0', '0', 1, 0, '2017-11-08 09:57:27', 0, '2020-03-12 00:13:52');
INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1201, '菜单新增', 'sys_menu_add', NULL, 1200, NULL, 0, '0', '1', 1, 0, '2017-11-08 10:15:53', 0, '2021-05-25 06:48:34');
INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1202, '菜单修改', 'sys_menu_edit', NULL, 1200, NULL, 0, '0', '1', 1, 0, '2017-11-08 10:16:23', 0, '2021-05-25 06:48:34');
INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1203, '菜单删除', 'sys_menu_del', NULL, 1200, NULL, 0, '0', '1', 1, 0, '2017-11-08 10:16:43', 0, '2021-05-25 06:48:34');
INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1300, '角色管理', NULL, '/admin/role/index', 1000, 'icon-jiaoseguanli', 2, '0', '0', 1, 0, '2017-11-08 10:13:37', 0, '2020-03-12 00:15:40');
INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1301, '角色新增', 'sys_role_add', NULL, 1300, NULL, 0, '0', '1', 1, 0, '2017-11-08 10:14:18', 0, '2021-05-25 06:48:34');
INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1302, '角色修改', 'sys_role_edit', NULL, 1300, NULL, 0, '0', '1', 1, 0, '2017-11-08 10:14:41', 0, '2021-05-25 06:48:34');
INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1303, '角色删除', 'sys_role_del', NULL, 1300, NULL, 0, '0', '1', 1, 0, '2017-11-08 10:14:59', 0, '2021-05-25 06:48:34');
INSERT INTO `db_backstage`.`sys_menu` (`menu_id`, `name`, `permission`, `path`, `parent_id`, `icon`, `sort_order`, `keep_alive`, `type`, `deleted`, `creator`, `create_time`, `modifier`, `modify_time`) VALUES (1304, '分配权限', 'sys_role_perm', NULL, 1300, NULL, 0, '0', '1', 1, 0, '2018-04-20 07:22:55', 0, '2021-05-25 06:48:34');

INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1000);
INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1100);
INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1101);
INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1102);
INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1103);
INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1104);
INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1200);
INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1201);
INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1202);
INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1203);
INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1300);
INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1301);
INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1302);
INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1303);
INSERT INTO `sys_role_menu`(role_id,menu_id) VALUES (1, 1304);