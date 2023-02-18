package com.itheima.demo.rabbit2.producer;

import com.alibaba.fastjson.JSONObject;
import com.itheima.demo.rabbit2.channels.MyChannel;
import com.itheima.demo.rabbit2.dto.PayLoadDTO;
import com.itheima.demo.rabbit2.enums.ActionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息生产者
 */
@Slf4j
@Component
@EnableBinding(MyChannel.class)
public class MyProducer {

    @Resource
    private MyChannel myChannel;

    /**
     * 测试消息生产 通道1
     */
    public void testPushToMq() {

        PayLoadDTO<String> payLoadDTO =
                PayLoadDTO.<String>builder()
                        .action(ActionEnum.ADD)
                        .data("添加数据"+ System.currentTimeMillis()).build();
        Message<PayLoadDTO<String>> message = MessageBuilder
                .withPayload(payLoadDTO)
                .setHeader("mq-rabbit-msg-add", "add-msg")
                .build();
        log.info("mq 消息推送，消息=>{}", JSONObject.toJSONString(payLoadDTO));
        myChannel.output().send(message);

    }
    /**
     * 测试消息生产 通道2
     */
    public void testPushToMq2() {
        PayLoadDTO<String> payLoadDTO =
                PayLoadDTO.<String>builder()
                        .action(ActionEnum.ADD)
                        .data("添加数据").build();
        Message<PayLoadDTO<String>> message = MessageBuilder
                .withPayload(payLoadDTO)
                .setHeader("mq-rabbit-msg-add", "add-msg")
                .build();
        log.info("mq 消息推送，消息=>{}", JSONObject.toJSONString(payLoadDTO));
        myChannel.output2().send(message);

    }

}
