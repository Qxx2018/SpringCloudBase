package com.itheima.sys.auth.controller;

import com.itheima.common.coredata.rsp.Rsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10445
 */
@RestController
@Slf4j
@RequestMapping("/Test")
public class TestController {
    @RequestMapping(value = "/t1",method = RequestMethod.GET)
    public Rsp<Boolean> t() {
        log.info("11111");
        return Rsp.ok();
    }
}
