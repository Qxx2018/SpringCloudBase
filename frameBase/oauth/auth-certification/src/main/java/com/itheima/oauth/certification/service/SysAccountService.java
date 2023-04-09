package com.itheima.oauth.certification.service;

import com.itheima.common.service.BaseService;
import com.itheima.oauth.certification.models.SysAccountModel;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 登入账户服务接口
 * @author XinXingQian
 */
public interface SysAccountService extends BaseService<SysAccountModel>, UserDetailsService {

}
