package com.itheima.oauth.certification.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.oauth.certification.mapper.SysAccountMapper;
import com.itheima.oauth.certification.models.SysAccountModel;
import com.itheima.oauth.certification.service.SysAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 登入账户服务实现
 * @author XinXingQian
 */
@Slf4j
@Service
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccountModel> implements SysAccountService {
    /**
     * loadUserByUsername在登录的时候会触发该方法
     * 根据用户名定位用户
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysAccountModel sysAccountModel = new SysAccountModel();
        return sysAccountModel;
    }
}
