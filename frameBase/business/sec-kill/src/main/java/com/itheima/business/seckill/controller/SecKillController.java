package com.itheima.business.seckill.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/sec-kill")
public class SecKillController {

    @PostMapping(value = "/do")
    @SentinelResource(value = "secKill")
    public String secKill(@RequestBody Object o) {
        return "success";
    }


}
