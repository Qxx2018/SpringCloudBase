--授权码表
CREATE TABLE `oauth_code` (
  `code` varchar(255) NOT NULL COMMENT '存储服务端系统生成的code的值(未加密).',
  `authentication` blob NOT NULL,
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间 (扩展)',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='授权码表';