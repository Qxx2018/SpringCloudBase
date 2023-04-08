--用户授权表
CREATE TABLE `oauth_approvals` (
  `userId` varchar(255) NOT NULL COMMENT '授权的用户id',
  `clientId` varchar(255) NOT NULL COMMENT '客户端id',
  `scope` varchar(255) NOT NULL COMMENT '客户端申请的权限范围',
  `status` varchar(10) NOT NULL,
  `expiresAt` datetime NOT NULL COMMENT '到期时间点',
  `lastModifiedAt` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次调整时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户授权表';