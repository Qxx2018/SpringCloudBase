package com.itheima.business.seckill.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.itheima.business.seckill.service.SecKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 秒杀服务实现
 * @author XinXingQian
 */
@Service
@Slf4j
public class SecKillServiceImpl implements SecKillService {
    /**
     * 执行秒杀
     *
     * @param orderId
     * @return
     */
    @Override
    @SentinelResource(value = "secKill",blockHandler = "secKillBlockHandler")
    public Boolean secKill(String orderId) {
        log.info("=========执行秒杀");
        return Boolean.TRUE;
    }
    public String blockHandlerTest(String orderId, BlockException ex) {
        log.error("=====被限流了");
        log.error("blockHandler："+orderId, ex);
        return "限流了, 请稍后再试";
    }

}
