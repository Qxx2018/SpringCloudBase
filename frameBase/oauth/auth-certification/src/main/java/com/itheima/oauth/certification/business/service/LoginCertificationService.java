package com.itheima.oauth.certification.business.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 登录认证服务接口
 * @author XinXingQian
 */
public interface LoginCertificationService extends UserDetailsService {
    /**
     * Locates the user based on the phone
     * @param phone
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException;

}
