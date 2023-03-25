package com.itheima.oauth.controller;

import com.itheima.common.vo.Rsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登入控制器
 * @author XinXingQian
 */
@RestController
@Slf4j
@RequestMapping("oauth")
public class LoginController {

    @GetMapping("/login")
    public Rsp<String> login() {
        log.info("================登入....");
        return Rsp.ok("success");
    }
}
