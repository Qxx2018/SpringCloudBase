package com.itheima.sys.auth.controller;

import com.itheima.common.coredata.rsp.Rsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping(value = "/t1")
    public Rsp<Boolean> t() {
        log.info("11111");
        return Rsp.ok();
    }
}
