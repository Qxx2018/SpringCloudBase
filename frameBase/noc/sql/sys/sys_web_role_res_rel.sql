CREATE TABLE `sys_web_role_res_rel` (
  `id` bigint(20) NOT NULL,
  `tenant_id` varchar(32) DEFAULT NULL COMMENT '租户',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `resource_id` bigint(20) NOT NULL COMMENT '资源权限id-一个角色多个资源权限',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 1删除 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统-管理平台-角色-资源权限-关联-表';