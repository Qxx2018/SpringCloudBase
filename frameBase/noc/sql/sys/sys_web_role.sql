CREATE TABLE `sys_web_role` (
  `id` bigint(20) NOT NULL,
  `tenant_id` varchar(32) DEFAULT NULL COMMENT '租户',
  `role_code` varchar(20) NOT NULL COMMENT '角色编码',
  `role_type` varchar(20) DEFAULT NULL COMMENT '角色类型',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 1删除 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统-管理平台-角色';