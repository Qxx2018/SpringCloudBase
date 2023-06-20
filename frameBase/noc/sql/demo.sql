/*
 Navicat Premium Data Transfer

 Source Server         : 虚拟机129
 Source Server Type    : MySQL
 Source Server Version : 50741
 Source Host           : 192.168.237.129:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50741
 File Encoding         : 65001

 Date: 20/06/2023 13:49:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `token_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '该字段的值是将access_token的值通过MD5加密后存储的.',
  `token` blob NOT NULL COMMENT '存储将OAuth2AccessToken.java对象序列化后的二进制数据, 是真实的AccessToken的数据值.',
  `authentication_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '该字段具有唯一性 根据当前的user_name(如果有)、client_id、scope通过MD5加密生成',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录时的用户名, 若客户端没有用户名(如grant_type=\"client_credentials\"),则该值等于client_id',
  `authentication` blob NOT NULL COMMENT '存储将OAuth2Authentication.java对象序列化后的二进制数据.',
  `refresh_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '该字段的值是将refresh_token的值通过MD5加密后存储的.',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间 (扩展)',
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '生成access_token' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals`  (
  `userId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权的用户id',
  `clientId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端id',
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端申请的权限范围',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `expiresAt` datetime(0) NOT NULL COMMENT '到期时间点',
  `lastModifiedAt` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次调整时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户授权表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_approvals
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用于唯一标识每一个客户端；同appKey',
  `resource_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端所能访问的资源id集合，多个资源时用逗号分割',
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端的访问密钥 同appSecret',
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '指定客户端申请的权限范围，自定义',
  `authorized_grant_types` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权模式：\r\nauthorization_code 授权码\r\npassword 密码\r\nrefresh_token 刷新token\r\nimplicit 隐藏式\r\nclient_credentials 客户端模式\r\nsms_code 手机短信验证码模式',
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端的重定向URI',
  `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端拥有的权限值，自定义',
  `access_token_validity` int(11) NOT NULL COMMENT '客户端access_token有效时间',
  `refresh_token_validity` int(11) NOT NULL COMMENT '客户端refresh_token有效时间',
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段',
  `trusted` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否授信任 1授信0不授信',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间 (扩展)',
  `autoapprove` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'false' COMMENT '设置用户是否自动Approval操作\r\n默认值为 \'false\'',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '客户端详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('D423713BF1A7A', 'all', '$2y$16$/6Xge2Cb21wGzqXzCmkoMuhRo8tRrJE305wLJToS1OyaEbA4sBq4u', 'all', 'authorization_code,password,refresh_token,client_credentials,sms_code', 'www.baidu.com', 'all', 72000, 259200, NULL, 1, '2023-05-24 01:59:05', 'false');

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token`  (
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用于唯一标识每一个客户端；同appKey',
  `token_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '从服务器端获得到的access_token的值',
  `token` blob NOT NULL COMMENT '二进制字段， 存储的数据是OAuth2AccessToken.java对象序列化后的二进制数据.',
  `authentication_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '该字段具有唯一性 根据当前的user_name(如果有)、client_id、scope通过MD5加密生成',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登入时的用户名',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间 扩展',
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '在客户端系统中存储从服务端获取的token数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`  (
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '存储服务端系统生成的code的值(未加密).',
  `authentication` blob NOT NULL,
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间 (扩展)',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '授权码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_code
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`  (
  `token_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '该字段的值是将refresh_token的值通过MD5加密后存储的.',
  `token` blob NOT NULL COMMENT '存储将OAuth2RefreshToken.java对象序列化后的二进制数据.',
  `authentication` blob NOT NULL,
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间(扩展)'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '更新access_token' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_refresh_token
-- ----------------------------

-- ----------------------------
-- Table structure for sys_web_acc_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_web_acc_role_rel`;
CREATE TABLE `sys_web_acc_role_rel`  (
  `id` bigint(20) NOT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `account_id` bigint(20) NOT NULL COMMENT '账号id',
  `role_id` bigint(20) NOT NULL COMMENT '账号对应的角色id-允许一对多',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `version` bigint(20) NOT NULL DEFAULT 0 COMMENT '版本',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 1删除 0正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-管理平台-账号-角色-关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_web_acc_role_rel
-- ----------------------------
INSERT INTO `sys_web_acc_role_rel` VALUES (1651838986647298048, NULL, 1651838488800251904, 1651838649978904576, '2023-04-28 14:33:41', '2023-04-28 22:44:28', NULL, NULL, 0, 0);

-- ----------------------------
-- Table structure for sys_web_account
-- ----------------------------
DROP TABLE IF EXISTS `sys_web_account`;
CREATE TABLE `sys_web_account`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登入账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登入密码',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0 COMMENT '版本',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 1删除 0正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-管理平台-账户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_web_account
-- ----------------------------
INSERT INTO `sys_web_account` VALUES (1651838488800251904, NULL, '1044582360', '$2y$16$Zx29yKon4QLcKEFrVkAMUuu8FVLPXRXCYAGTXVALCKb7jORK4ghGa', '2023-04-28 14:06:37', '2023-04-28 22:39:31', NULL, NULL, 0, 0);

-- ----------------------------
-- Table structure for sys_web_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_web_menu`;
CREATE TABLE `sys_web_menu`  (
  `id` bigint(20) NOT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `parent_id` bigint(20) NOT NULL COMMENT '父级菜单id',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名',
  `menu_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单编码',
  `menu_level` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '菜单等级',
  `menu_sort` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '同级下菜单排序',
  `menu_icon_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单图标id',
  `menu_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单url',
  `menu_tree_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单层级树 a/b/c',
  `hidden` tinyint(4) NOT NULL DEFAULT 1 COMMENT '显示1 隐藏0',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` tinyint(4) NOT NULL DEFAULT 0 COMMENT '版本',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 1删除 0正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-管理平台-菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_web_menu
-- ----------------------------
INSERT INTO `sys_web_menu` VALUES (1651840548874543104, NULL, 0, '用户中心', 'menu_user_center', 1, 1, NULL, NULL, '1651840548874543104', 1, NULL, NULL, NULL, NULL, 0, 0);

-- ----------------------------
-- Table structure for sys_web_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_web_resource`;
CREATE TABLE `sys_web_resource`  (
  `id` bigint(20) NOT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  `resource_category` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源分类:ADD DELETE UPDATE QUERY',
  `resource_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源名称-业务操作名称',
  `resource_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源访问url-请求访问的api地址 a/b/c',
  `resource_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源编码-全表唯一',
  `resource_level` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '资源等级',
  `resource_sort` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '同级别下排序',
  `resource_icon_id` bigint(20) NULL DEFAULT NULL COMMENT '资源图标id',
  `hidden` tinyint(4) NOT NULL DEFAULT 1 COMMENT '显示1 隐藏0',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0 COMMENT '版本',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 1删除 0正常',
  PRIMARY KEY (`id`, `resource_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-管理平台-菜单资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_web_resource
-- ----------------------------
INSERT INTO `sys_web_resource` VALUES (1651840314358394880, NULL, 1651840548874543104, 'QUERY', '用户中心', 'user/center', 'source_user_center', 1, 1, NULL, 1, '2023-04-28 15:18:09', '2023-04-28 23:34:46', NULL, NULL, 0, 0);

-- ----------------------------
-- Table structure for sys_web_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_web_role`;
CREATE TABLE `sys_web_role`  (
  `id` bigint(20) NOT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户',
  `role_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `role_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色类型',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `created_time` datetime(0) NULL DEFAULT NULL,
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0 COMMENT '版本',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 1删除 0正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-管理平台-角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_web_role
-- ----------------------------
INSERT INTO `sys_web_role` VALUES (1651838649978904576, NULL, 'admin_manager', NULL, '系统管理员', '2023-04-28 14:31:05', '2023-04-28 22:40:12', NULL, NULL, 0, 0);

-- ----------------------------
-- Table structure for sys_web_role_menu_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_web_role_menu_rel`;
CREATE TABLE `sys_web_role_menu_rel`  (
  `id` bigint(20) NOT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0 COMMENT '版本',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 1删除 0正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-管理平台-角色-菜单-关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_web_role_menu_rel
-- ----------------------------
INSERT INTO `sys_web_role_menu_rel` VALUES (1651851663033552896, NULL, 1651838649978904576, 1651840548874543104, '2023-04-28 15:32:10', '2023-04-28 15:32:12', NULL, NULL, 0, 0);

-- ----------------------------
-- Table structure for sys_web_role_res_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_web_role_res_rel`;
CREATE TABLE `sys_web_role_res_rel`  (
  `id` bigint(20) NOT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `resource_id` bigint(20) NOT NULL COMMENT '资源权限id-一个角色多个资源权限',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0 COMMENT '版本',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 1删除 0正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-管理平台-角色-资源权限-关联-表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_web_role_res_rel
-- ----------------------------
INSERT INTO `sys_web_role_res_rel` VALUES (1651851280877957120, NULL, 1651838649978904576, 1651840314358394880, '2023-04-28 15:30:29', '2023-04-28 15:30:32', NULL, NULL, 0, 0);

-- ----------------------------
-- Table structure for sys_web_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_web_user`;
CREATE TABLE `sys_web_user`  (
  `id` bigint(20) NOT NULL,
  `account_id` bigint(20) NULL DEFAULT NULL COMMENT '对应的账号id',
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `real_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `nick_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别M男F女',
  `face_pic` bigint(20) NULL DEFAULT NULL COMMENT '头像',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `created_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` bigint(20) NOT NULL DEFAULT 0 COMMENT '版本',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 1删除 0正常',
  PRIMARY KEY (`id`, `phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-管理平台-用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_web_user
-- ----------------------------
INSERT INTO `sys_web_user` VALUES (1660951991335120896, 1651838488800251904, '18268832697', '钱鑫星', '', 'M', NULL, NULL, '2023-05-24 02:13:14', NULL, NULL, 0, 0);

-- ----------------------------
-- Table structure for wx_app_account
-- ----------------------------
DROP TABLE IF EXISTS `wx_app_account`;
CREATE TABLE `wx_app_account`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `created_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT 0 COMMENT '版本',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除 1删除 0正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统-移动端-账户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wx_app_account
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
