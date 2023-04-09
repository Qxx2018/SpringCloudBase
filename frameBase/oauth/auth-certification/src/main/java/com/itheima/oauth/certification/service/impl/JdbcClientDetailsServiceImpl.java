package com.itheima.oauth.certification.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Override, JDBC implementation of the client details service.
 * @author XinXingQian
 */
@Service
@Slf4j
public class JdbcClientDetailsServiceImpl extends JdbcClientDetailsService {

    @Resource
    private PasswordEncoder bCryptPasswordEncoder;

    public JdbcClientDetailsServiceImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        super.setPasswordEncoder(bCryptPasswordEncoder);
        ClientDetails clientDetails = super.loadClientByClientId(clientId);
        log.info("加载客户端信息:clientId=>{},ClientDetails=>{}",clientId,clientDetails);
        return clientDetails;
    }
}
