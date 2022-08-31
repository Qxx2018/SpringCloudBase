package com.itheima.sys.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.coredata.modules.sys.auth.ao.SysAccountAO;
import com.itheima.sys.auth.mapper.SysAccountMapper;
import com.itheima.sys.auth.entity.SysAccountEntity;
import com.itheima.sys.auth.service.SysAccountService;
import com.itheima.sys.auth.utils.AuthToolsFunc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 登入账户服务实现
 * @author 10445
 */
@Service
@Slf4j
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccountEntity> implements SysAccountService {

    /**
     * 创建账户
     *
     * @param ao 新增的账户信息
     * @return
     */
    @Override
    public Boolean createAccount(SysAccountAO ao) {
        ao.setPassword(AuthToolsFunc.encodePassword(ao.getPassword()));
        return Boolean.TRUE;
    }
}
