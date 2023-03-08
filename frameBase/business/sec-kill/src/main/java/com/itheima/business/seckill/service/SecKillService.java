package com.itheima.business.seckill.service;

/**
 * 秒杀服务接口
 * @author XinXingQian
 */
public interface SecKillService {
    /**
     * 执行秒杀
     * @param orderId
     * @return
     */
    Boolean secKill(String orderId);
}
