package com.itheima.oauth.certification.dto.accredit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * OAuth2认证-账号密码登入参数
 * @author XinXingQian
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "OAuth2认证-账号密码登入参数实体类")
public class PasswordModelLoginDTO extends AccreditDTO{

    private static final long serialVersionUID = 33366203221445476L;

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "登入账号 必填")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "登入密码 必填")
    private String password;

    @ApiModelProperty(value = "图像校验码")
    @NotBlank(message = "图像校验码 必填")
    private String checkCode;

    @ApiModelProperty(value = "设备码(获取图像校验码的设备码)")
    @NotBlank(message = "设备码 必填")
    private String deviceId;
}
