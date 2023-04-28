package com.itheima.oauth.certification;

import cn.hutool.core.util.IdUtil;
import com.itheima.oauth.certification.business.service.PermissionInformationService;
import com.itheima.oauth.certification.dto.PermissionDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = {OauthCertificationApplication.class,OauthTest.class})
@RunWith(SpringRunner.class)
@Slf4j
public class OauthTest {
    @Resource
    private PermissionInformationService permissionInformationService;
    /**
     * 生成uuid
     */
    @Test
    public void uuid() {
        //简单使用
        long id = IdUtil.getSnowflakeNextId();
        System.out.println(id);
    }
    /**
     * 生成登入用户密码
     */
    @Test
    public void loginPassword() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(
                BCryptPasswordEncoder.BCryptVersion.$2Y,
                16);
        String secret = passwordEncoder.encode("1044582360");
        System.out.println(secret);
    }
    /**
     * 获取认证用户的权限信息
     */
    @Test
    public void getPermissionByAccount() {
        PermissionDTO permission = permissionInformationService.getPermissionByAccount("1044582360");
        System.out.println();
    }
}
