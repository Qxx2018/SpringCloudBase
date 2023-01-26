package com.itheima.demo.rabbit.receiver;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.itheima.demo.rabbit.channel.MyChannel;
import com.itheima.demo.rabbit.dto.PayLoadDTO;
import com.itheima.demo.rabbit.exceptions.RabbitMQException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional(rollbackFor = {RabbitMQException.class})
@EnableBinding({MyChannel.class})
public class MyReceiver {
    /**
     * 测试消息监听
     */
    @StreamListener(value = MyChannel.INPUT,condition = "headers['mq-rabbit-msg-add']=='add-msg'")
    public void testHandlerMsg(String msg) {
        log.info("mq 消息监听，接受消息=>{}",msg);
        PayLoadDTO<String> payLoadDTO = JSONObject.parseObject(msg, new TypeReference<PayLoadDTO<String>>() {});
        log.info("消息队列事件"+payLoadDTO.getAction().toString());
        log.info("消息队列数据"+payLoadDTO.getData());
    }
}
