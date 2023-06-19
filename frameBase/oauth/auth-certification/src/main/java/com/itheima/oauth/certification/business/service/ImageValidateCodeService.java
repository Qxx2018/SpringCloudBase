package com.itheima.oauth.certification.business.service;

import com.itheima.common.vo.Rsp;

/**
 * 图像验证码服务接口
 * @author XinXingQian
 */
public interface ImageValidateCodeService {
    /**
     * 生成验证码
     * @param deviceId
     * @return
     */
    Rsp<String> createCode(String deviceId);
}
