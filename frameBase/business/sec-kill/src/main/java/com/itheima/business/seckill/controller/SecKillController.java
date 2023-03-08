package com.itheima.business.seckill.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.itheima.business.seckill.service.SecKillService;
import com.itheima.common.vo.Rsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/sec-kill")
public class SecKillController {

    @Resource
    private SecKillService secKillService;

    @PostMapping(value = "/do")
    @SentinelResource(value = "secKill")
    public Rsp<String> secKill(@RequestParam String orderId) {
        secKillService.secKill(orderId);
        return Rsp.ok();
    }


}
