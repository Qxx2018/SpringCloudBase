package com.itheima.oauth.certification.business.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.itheima.oauth.certification.business.service.LoginCertificationService;
import com.itheima.oauth.certification.business.service.PermissionInformationService;
import com.itheima.oauth.certification.dto.PermissionDTO;
import com.itheima.oauth.certification.dto.UserDetailsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录认证服务实现
 * @author XinXingQian
 */
@Service
@Slf4j
public class LoginCertificationServiceImpl implements LoginCertificationService {
    @Resource
    private PermissionInformationService permissionInformationService;

    /**
     * Locates the user to get GrantedAuthority
     * 定位用户获取资源权限
     * @param permission
     * @return
     */
    private UserDetails locatesUser(PermissionDTO permission) {
        UserDetailsDTO userDetails = UserDetailsDTO.builder().build();
        PermissionDTO.Account account = permission.getAccount();
        BeanUtil.copyProperties(account,userDetails);
        List<PermissionDTO.Resource> resourceList = permission.getResources();
        if (CollUtil.isNotEmpty(resourceList)) {
            log.info("当前登录账户{},持有的资源权限{}",account.getAccountId(),resourceList.toString());
            List<GrantedAuthority> auths = resourceList.stream().map(r -> new SimpleGrantedAuthority(r.getResourceCode())).collect(Collectors.toList());
            userDetails.setGrantedAuthorities(auths);
        }
        return userDetails;
    }

    /**
     * loadUserByUsername根据用户名定位用户
     * 在登录的时候会触发该方法
     * https://blog.csdn.net/weixin_44802953/article/details/109154822
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

        PermissionDTO permission = permissionInformationService.getPermissionByAccount(username);
        return this.locatesUser(permission);
    }

    /**
     * Locates the user based on the phone
     * 根据手机号定位用户
     * @param phone
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        PermissionDTO permission = permissionInformationService.getPermissionByPhone(phone);
        return this.locatesUser(permission);
    }
}
