package com.itheima.oauth.certification.dto.accredit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * OAuth2认证-授权登入参数
 * @author XinXingQian
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "OAuth2认证-授权登入参数实体类")
public class AuthorizationCodeModelLoginDTO extends AccreditDTO{
    private static final long serialVersionUID = 4164458730663302605L;

    @ApiModelProperty("授权码")
    @NotBlank(message = "授权码 必填")
    private String code;

    @ApiModelProperty("回调地址")
    @NotBlank(message = "回调地址 必填")
    @JsonProperty("redirect_uri")
    private String redirectUri;
}
