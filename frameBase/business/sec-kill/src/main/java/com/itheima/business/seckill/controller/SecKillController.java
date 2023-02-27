package com.itheima.business.seckill.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/sec-kill")
public class SecKillController {

    @PostMapping(value = "/do")
    @SentinelResource(value = "secKill", blockHandler = "blockHandlerFunc", fallback = "fallbackFunc")
    public String secKill() {
        log.info("=============秒杀业务");
        return "success";
    }

    public String blockHandlerFunc(BlockException e) {
        e.printStackTrace();
        log.warn("=========被限流了blockHandler{}",e.getMessage());
        return "被限流了";
    }

    public String fallbackFunc(Throwable e) {
        e.printStackTrace();
        log.warn("=========被限流了fallback{}",e.getMessage());
        return "被限流了";
    }


}
