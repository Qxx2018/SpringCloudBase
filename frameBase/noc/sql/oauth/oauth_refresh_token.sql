--更新access_token
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(255) NOT NULL COMMENT '该字段的值是将refresh_token的值通过MD5加密后存储的.',
  `token` blob NOT NULL COMMENT '存储将OAuth2RefreshToken.java对象序列化后的二进制数据.',
  `authentication` blob NOT NULL,
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间(扩展)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='更新access_token';