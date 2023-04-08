--生成access_token
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(255) NOT NULL COMMENT '该字段的值是将access_token的值通过MD5加密后存储的.',
  `token` blob NOT NULL COMMENT '存储将OAuth2AccessToken.java对象序列化后的二进制数据, 是真实的AccessToken的数据值.',
  `authentication_id` varchar(256) NOT NULL COMMENT '该字段具有唯一性 根据当前的user_name(如果有)、client_id、scope通过MD5加密生成',
  `user_name` varchar(255) NOT NULL COMMENT '登录时的用户名, 若客户端没有用户名(如grant_type="client_credentials"),则该值等于client_id',
  `authentication` blob NOT NULL COMMENT '存储将OAuth2Authentication.java对象序列化后的二进制数据.',
  `refresh_token` varchar(255) NOT NULL COMMENT '该字段的值是将refresh_token的值通过MD5加密后存储的.',
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间 (扩展)',
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='生成access_token';