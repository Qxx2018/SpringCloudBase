package com.itheima.oauth.certification.business.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.itheima.common.utils.RedisUtil;
import com.itheima.common.vo.Rsp;
import com.itheima.oauth.certification.business.service.ImageValidateCodeService;
import com.itheima.oauth.certification.constants.AuthConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 图像验证码服务实现
 * @author XinXingQian
 */
@Service
@Slf4j
public class ImageValidateCodeServiceImpl implements ImageValidateCodeService {
    /**
     * 图像验证码过期时间 5分钟（300秒） 单位秒
     */
    private static final Long EXPIRE_TIME = 300L;
    @Resource
    private RedisUtil<String> redisUtil;
    /**
     * 生成验证码
     *
     * @param deviceId
     * @return
     */
    @Override
    public Rsp<String> createCode(String deviceId) {

        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        // 重新生成code
        captcha.createCode();
        redisUtil.hset(AuthConstants.IMG_LOGIN_CHECK_KEY,deviceId,captcha.getCode(),EXPIRE_TIME);
        return Rsp.ok(captcha.getImageBase64Data());
    }
}
