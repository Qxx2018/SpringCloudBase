package com.itheima.oauth.certification.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 授权模式枚举
 * @author XinXingQian
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, includeFieldNames = true)
public enum AuthorizedGrantTypesEnum {

    /**
     * authorization_code
     */
    AUTHORIZATION_CODE("authorization_code","授权码模式"),
    /**
     * password
     */
    PASSWORD("password","密码模式"),
    /**
     * refresh_token
     */
    REFRESH_TOKEN("refresh_token","刷新token"),
    /**
     * implicit
     */
    IMPLICIT("implicit","隐藏式"),
    /**
     * client_credentials
     */
    CLIENT_CREDENTIALS("client_credentials","客户端模式"),
    /**
     * sms_code
     */
    SMS_CODE("sms_code","手机短信验证码模式"),
    ;
    /**
     * 码
     */
    private String code;
    /**
     * 描述
     */
    private String msg;
}
