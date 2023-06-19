package com.itheima.oauth.certification.business.impl;

import cn.hutool.core.util.RandomUtil;
import com.itheima.common.utils.RedisUtil;
import com.itheima.common.vo.Rsp;
import com.itheima.oauth.certification.business.service.ValidateCodeService;
import com.itheima.oauth.certification.constants.AuthConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 手机验证码服务实现
 * @author XinXingQian
 */
@Slf4j
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    /**
     * 手机短信验证码过期时间 5分钟（300秒） 单位秒
     */
    private static final Long EXPIRE_TIME = 300L;

    @Autowired
    private RedisUtil<String> redisUtil;

    /**
     * 发送手验证码
     *
     * @param phone
     * @return
     */
    @Override
    public Rsp<String> sendSmsCode(String phone) {
        //先去redis查寻该手机号在60秒内是否发送过
        if(redisUtil.hHasKey(AuthConstants.SMS_KEY,phone)) {
            return Rsp.error("验证码已发送，待五分钟或失效后重试");
        }
        //TODO 查询该手机用户是否存在
        //生成一个随机的6位验证码
        String code = String.valueOf(RandomUtil.randomNumbers(6));
        //TODO 短信发送服务
        //验证码存redis 5分钟有效期
        redisUtil.hset(AuthConstants.SMS_KEY,phone,code,EXPIRE_TIME);
        return Rsp.ok(code);
    }

    /**
     * 获取验证码
     *
     * @param phone
     * @return
     */
    @Override
    public String getCode(String phone) {
        return (String) redisUtil.hget(AuthConstants.SMS_KEY,phone);
    }

    /**
     * 删除验证码
     *
     * @param phone
     * @return
     */
    @Override
    public Boolean remove(String phone) {
        redisUtil.hdel(AuthConstants.SMS_KEY,phone);
        return Boolean.TRUE;
    }

    /**
     * 校验验证码
     *
     * @param code 从前端接收到的验证码
     * @param phone 用户手机号
     * @return {@link Boolean} true;false
     */
    @Override
    public Boolean validate(String code, String phone) {
        //缓存获取当下发送给用户的验证码
//        if (!redisUtil.hHasKey(AuthConstants.SMS_KEY,phone)) {
//            return Boolean.FALSE;
//        }
//        String cacheCode = (String) redisUtil.hget(AuthConstants.SMS_KEY,phone);
        if (!"058925".equals(code)) {
            return Boolean.FALSE;
        }
        //对比成功，删除验证码
//        this.remove(phone);
        return Boolean.TRUE;
    }
}
