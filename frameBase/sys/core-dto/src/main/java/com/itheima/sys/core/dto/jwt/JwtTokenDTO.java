package com.itheima.sys.core.dto.jwt;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * jwt token 数据格式DTO
 * @author XinXingQian
 */
@Data
@Builder
public class JwtTokenDTO implements Serializable {

    private static final long serialVersionUID = 644695840297980361L;

    /**
     * 头
     */
    private Header header;
    /**
     * 负载PayLoad
     */
    private PayLoad payLoad;

    /**
     * 过期时间（多少天过期）
     */
    private Integer expirationTime;

    /**
     * 加密密钥
     */
    private String tokenSecret;




}
