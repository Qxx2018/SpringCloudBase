CREATE TABLE `sys_web_user` (
  `id` bigint(20) NOT NULL,
  `account_id` bigint(20) DEFAULT NULL COMMENT '对应的账号id',
  `phone` char(11) NOT NULL COMMENT '手机号',
  `real_name` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '昵称',
  `gender` char(1) DEFAULT NULL COMMENT '性别M男F女',
  `face_pic` bigint(20) DEFAULT NULL COMMENT '头像',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `created_by` varchar(32) DEFAULT NULL,
  `updated_by` varchar(32) DEFAULT NULL,
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 1删除 0正常',
  PRIMARY KEY (`id`,`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统-管理平台-用户';