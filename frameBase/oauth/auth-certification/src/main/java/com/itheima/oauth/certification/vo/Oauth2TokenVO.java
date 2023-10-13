package com.itheima.oauth.certification.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Oauth2TokenVO
 * @author XinXingQian
 */
@ApiModel("Oauth2TokenVO")
@Data
@Builder
public class Oauth2TokenVO {

    @ApiModelProperty("访问令牌")
    private String token;

    @ApiModelProperty("刷新令牌")
    private String refreshToken;

    @ApiModelProperty("有效时间")
    private Integer expiresIn;
}
