package com.itheima.oauth.certification.dto.accredit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * OAuth2认证-账号密码登入参数
 * @author XinXingQian
 */
@Data
@ApiModel(value = "OAuth2认证-账号密码登入参数实体类")
public class PasswordModelLoginDTO {

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

}
