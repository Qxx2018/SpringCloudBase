package com.itheima.sys.auth.controller;

import com.itheima.common.coredata.modules.sys.auth.ao.SysAccountAO;
import com.itheima.common.coredata.modules.sys.auth.ao.SysMenuAO;
import com.itheima.common.coredata.rsp.Rsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10445
 */
@RestController
@Slf4j
@RequestMapping("/ssa")
public class SysAuthController {
    /**
     * 创建菜单
     * @param ao 系统菜单信息
     * @return
     */
    @RequestMapping(value = "/menu/create", method = RequestMethod.POST)
    public Rsp<Boolean> createMenu(@RequestBody @Validated SysMenuAO ao) {
        return Rsp.ok(Boolean.TRUE);
    }

    @RequestMapping(value = "/account/create", method = RequestMethod.POST)
    public Rsp<Boolean> createAccount(@RequestBody @Validated SysAccountAO ao) {

        return Rsp.ok(Boolean.TRUE);
    }

}
