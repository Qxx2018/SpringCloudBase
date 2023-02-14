package com.itheima.demo.rabbit.producer;

import com.alibaba.fastjson.JSONObject;
import com.itheima.demo.rabbit.callback.BCallBack;
import com.itheima.demo.rabbit.declaration.BaseMQDecConfig;
import com.itheima.demo.rabbit.dto.PayLoadDTO;
import com.itheima.demo.rabbit.enums.ActionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.UUID;

@Component
@Slf4j
public class BProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private BCallBack bCallBack;

    /**
     * setConfirmCallback设置发布消息成功到交换器后的回调
     * setReturnCallback消息回退方法(当消息传递过程中不可达目的地时（及无法到达对应的队列）)
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(bCallBack);
        rabbitTemplate.setReturnCallback(bCallBack);
    }
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
                msg,
                //new CorrelationData 配合发布确认 指定唯一ID
                new CorrelationData(UUID.randomUUID().toString())
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
