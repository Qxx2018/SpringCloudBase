package com.itheima.oauth.certification.dto.accredit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * OAuth2认证-手机号登入参数
 * @author XinXingQian
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "OAuth2认证-手机号登入参数实体类")
public class SmsModelLoginDTO extends AccreditDTO{
    private static final long serialVersionUID = -3105854496973571666L;

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号 必填")
    private String phone;

    @ApiModelProperty("短信验证码")
    @NotBlank(message = "短信验证码 必填")
    private String smsCode;
}
