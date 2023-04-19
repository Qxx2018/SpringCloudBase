CREATE TABLE `sys_web_resource` (
  `id` bigint(20) NOT NULL,
  `tenant_id` varchar(32) DEFAULT NULL COMMENT '租户',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  `resource_category` varchar(20) NOT NULL COMMENT '资源分类:ADD DELETE UPDATE QUERY',
  `resource_name` varchar(255) NOT NULL COMMENT '资源名称-业务操作名称',
  `resource_url` varchar(255) NOT NULL COMMENT '资源访问url-请求访问的api地址 a/b/c',
  `resource_code` varchar(50) NOT NULL COMMENT '资源编码-全表唯一',
  `resource_level` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '资源等级',
  `resource_sort` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '同级别下排序',
  `resource_icon_id` binary(20) DEFAULT NULL COMMENT '资源图标id',
  `hidden` tinyint(4) NOT NULL DEFAULT '1' COMMENT '显示1 隐藏0',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 1删除 0正常',
  PRIMARY KEY (`id`,`resource_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统-管理平台-菜单资源表';