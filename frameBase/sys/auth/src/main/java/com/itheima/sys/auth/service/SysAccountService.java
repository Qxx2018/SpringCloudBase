package com.itheima.sys.auth.service;

import com.itheima.common.coredata.modules.sys.auth.ao.SysAccountAO;
import com.itheima.sys.auth.entity.SysAccountEntity;

/**
 * 登入账户服务
 * @author 10445
 */
public interface SysAccountService extends BaseService<SysAccountEntity>{

    /**
     * 创建账户
     * @param ao 新增的账户信息
     * @return
     */
    Boolean createAccount(SysAccountAO ao);

}
