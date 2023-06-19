--  客户端详情
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL COMMENT '用于唯一标识每一个客户端；同appKey',
  `resource_ids` varchar(255) NOT NULL COMMENT '客户端所能访问的资源id集合，多个资源时用逗号分割',
  `client_secret` varchar(255) NOT NULL COMMENT '客户端的访问密钥 同appSecret',
  `scope` varchar(255) NOT NULL COMMENT '指定客户端申请的权限范围，自定义',
  `authorized_grant_types` varchar(255) NOT NULL COMMENT '授权模式：\r\nauthorization_code 授权码\r\npassword 密码\r\nrefresh_token 刷新token\r\nimplicit 隐藏式\r\nclient_credentials 客户端模式\r\nsms_code 手机短信验证码模式',
  `web_server_redirect_uri` varchar(255) NOT NULL COMMENT '客户端的重定向URI',
  `authorities` varchar(255) NOT NULL COMMENT '客户端拥有的权限值，自定义',
  `access_token_validity` int(11) NOT NULL COMMENT '客户端access_token有效时间',
  `refresh_token_validity` int(11) NOT NULL COMMENT '客户端refresh_token有效时间',
  `additional_information` varchar(4096) DEFAULT NULL COMMENT '预留字段',
  `trusted` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否授信任 1授信0不授信',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间 (扩展)',
  `autoapprove` varchar(255) NOT NULL DEFAULT 'false' COMMENT '设置用户是否自动Approval操作\r\n默认值为 ''false''',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户端详情';