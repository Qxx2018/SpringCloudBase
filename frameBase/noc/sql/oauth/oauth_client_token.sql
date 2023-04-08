--在客户端系统中存储从服务端获取的token数据
CREATE TABLE `oauth_client_token` (
  `client_id` varchar(255) NOT NULL COMMENT '用于唯一标识每一个客户端；同appKey',
  `token_id` varchar(255) NOT NULL COMMENT '从服务器端获得到的access_token的值',
  `token` blob NOT NULL COMMENT '二进制字段， 存储的数据是OAuth2AccessToken.java对象序列化后的二进制数据.',
  `authentication_id` varchar(255) NOT NULL COMMENT '该字段具有唯一性 根据当前的user_name(如果有)、client_id、scope通过MD5加密生成',
  `user_name` varchar(255) NOT NULL COMMENT '登入时的用户名',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间 扩展',
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='在客户端系统中存储从服务端获取的token数据';