package com.itheima.demo.rabbit.producer;

import com.alibaba.fastjson.JSONObject;
import com.itheima.demo.rabbit.declaration.BaseMQDecConfig;
import com.itheima.demo.rabbit.dto.PayLoadDTO;
import com.itheima.demo.rabbit.enums.ActionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
@Component
@Slf4j
public class BProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;
    /**
     * 测试消息生产者B
     */
    public void testPushToMqBAdd() {
        String msg = JSONObject.toJSONString(PayLoadDTO.<String>builder()
                .action(ActionEnum.ADD)
                .data("添加数据").build());
        rabbitTemplate.convertAndSend(
                BaseMQDecConfig.B_EXCHANGE,
                BaseMQDecConfig.B_KEY,
                msg
        );
        log.info("mq 消息推送，消息=>{}", msg);
    }
    /**
     * 测试消息生产者B
     */
    public void testPushToMqBDeleted() {
        String msg = JSONObject.toJSONString(PayLoadDTO.<String>builder()
                .action(ActionEnum.DELETE)
                .data("删除数据").build());
        rabbitTemplate.convertAndSend(
                BaseMQDecConfig.B_EXCHANGE,
                BaseMQDecConfig.B_KEY,
                msg
        );
        log.info("mq 消息推送，消息=>{}", msg);
    }
    /**
     * 测试消息生产者B2
     */
    public void  testPushToMqB2Add() {
        String msg = JSONObject.toJSONString(PayLoadDTO.<String>builder()
                .action(ActionEnum.ADD)
                .data("添加数据").build());
        rabbitTemplate.convertAndSend(
                BaseMQDecConfig.B_EXCHANGE,
                BaseMQDecConfig.B2_KEY,
                msg
        );
        log.info("mq 消息推送，消息=>{}", msg);
    }
    /**
     * 测试消息生产者B2
     */
    public void  testPushToMqB3Add() {
        String msg = JSONObject.toJSONString(PayLoadDTO.<String>builder()
                .action(ActionEnum.ADD)
                .data("添加数据").build());
        rabbitTemplate.convertAndSend(
                BaseMQDecConfig.B_EXCHANGE,
                BaseMQDecConfig.B3_KEY,
                msg
        );
        log.info("mq 消息推送，消息=>{}", msg);
    }
}
