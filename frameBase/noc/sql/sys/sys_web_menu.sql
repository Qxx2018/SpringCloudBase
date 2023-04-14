CREATE TABLE `sys_web_menu` (
  `id` bigint(20) NOT NULL,
  `tenant_id` varchar(32) DEFAULT NULL COMMENT '租户id',
  `parent_id` bigint(20) NOT NULL COMMENT '父级菜单id',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名',
  `menu_code` varchar(50) NOT NULL,
  `menu_level` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '菜单等级',
  `menu_sort` int(11) unsigned NOT NULL DEFAULT '1' COMMENT '同级下菜单排序',
  `menu_icon_id` bigint(20) DEFAULT NULL COMMENT '菜单图标id',
  `menu_url` varchar(255) DEFAULT NULL COMMENT '菜单url',
  `menu_tree_path` varchar(255) NOT NULL COMMENT '菜单层级树 a/b/c',
  `hidden` tinyint(4) NOT NULL DEFAULT '1' COMMENT '显示1 隐藏0',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` tinyint(4) NOT NULL DEFAULT '0' COMMENT '版本',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 1删除 0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统-管理平台-菜单';