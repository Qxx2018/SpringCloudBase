package com.itheima.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 业务异常枚举
 * @author XinXingQian
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, includeFieldNames = true)
public enum BusinessExceptionEnums {
    /**
     * 请求方法不支持
     */
    REQUEST_METHOD_NOT_SUPPORT_ERROR("300410","请求方法不支持"),
    /**
     * 请求媒体类型不支持
     */
    MEDIA_TYPE_NOT_SUPPORT("300420","请求媒体类型不支持"),
    /**
     * 非法参数异常
     */
    PARAM_ILLEGALITY_ERROR("600156","非法参数异常"),
    /**
     * 请求参数错误
     */
    REQUEST_PARAM_ERROR("600201","请求参数错误"),
    /**
     * 参数格式异常
     */
    PARAM_FORMAT_ERROR("600402","参数格式异常"),
    /**
     * 数据库异常
     */
    DATABASE_ERROR("700101","数据库异常"),
    /**
     * 短信校验码不正确
     */
    SMS_ERROR("900300","短信校验码不正确"),
    /**
     * 图像验证码错误
     */
    IMG_CHECK_ERROR("900302","图像验证码错误"),
    ;
    /**
     * code
     */
    private String code;
    /**
     * msg
     */
    private String msg;

}
