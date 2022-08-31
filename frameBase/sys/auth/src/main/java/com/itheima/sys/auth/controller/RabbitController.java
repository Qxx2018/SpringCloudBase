package com.itheima.sys.auth.controller;

import com.itheima.common.systools.mq.DelayedQueueConfig;
import com.itheima.common.systools.mq.TtlQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qxx
 */
@RestController
@Slf4j
@RequestMapping("/rabbit")
public class RabbitController {

    @Autowired
    RabbitTemplate rabbitTemplate;
    /**
     * 使用TTL+DXL
     * 延时队列发送 消息的TTl
     */
    @GetMapping(value = "/delayQueue/{message}")
    public void delayQueue(@PathVariable String message) {
        log.info("发送一条消息队列{}",message);
        rabbitTemplate.convertAndSend(
                TtlQueueConfig.DELAY_EXCHANGE_A,
                TtlQueueConfig.DELAY_ROUTING_KEY_A,
                message
        );
    }
    /**
     * 使用延时插件
     * @param message
     * @param time ms
     */
    @GetMapping(value = "/ttl/sendDelayedMsg/{message}/{time}")
    public void delayQueue(@PathVariable String message, @PathVariable String time) {
        log.info("发送一条时长为{}的消息给延时队列X{}",time,message);
        rabbitTemplate.convertAndSend(
                DelayedQueueConfig.DELAYED_EXCHANGE_X,
                DelayedQueueConfig.DELAYED_ROUTING_KEY_X,
                message,
                msg -> {
                    msg.getMessageProperties().setDelay(Integer.valueOf(time));
                    return msg;
                }
        );
    }
}
