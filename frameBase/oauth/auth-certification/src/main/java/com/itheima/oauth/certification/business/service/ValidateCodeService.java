package com.itheima.oauth.certification.business.service;

import com.itheima.sys.core.resp.Rsp;

/**
 * 手机验证码服务
 * @author XinXingQian
 */
public interface ValidateCodeService {
    /**
     * 发送验证码
     * @param phone
     * @return
     */
    Rsp<String> sendSmsCode(String phone);

    /**
     * 获取验证码
     * @param phone
     * @return
     */
    String getCode(String phone);

    /**
     * 删除验证码
     * @param phone
     * @return
     */
    Boolean remove(String phone);

    /**
     * 校验验证码
     * @param code 从前端接收到的验证码
     * @param phone 用户手机号
     * @return
     */
    Boolean validate(String code,String phone);

}
