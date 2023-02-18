package com.itheima.demo.rabbit2.receiver;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.itheima.demo.rabbit2.channels.MyChannel;
import com.itheima.demo.rabbit2.dto.PayLoadDTO;
import com.itheima.demo.rabbit2.exceptions.RabbitMQException;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@EnableBinding(MyChannel.class)
public class MyReceiver {
    private Integer count = 1;
    /**
     * 测试消息监听1
     */
    @StreamListener(value = MyChannel.INPUT,condition = "headers['mq-rabbit-msg-add']=='add-msg'")
    public void testHandlerMsg(Message<String> msg, @Header(AmqpHeaders.CHANNEL) Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) Long tag) throws IOException, RabbitMQException {
        log.info("mq 消息监听，接受消息=>{}",msg.toString());
        PayLoadDTO<String> payLoadDTO = JSONObject.parseObject(msg.getPayload(), new TypeReference<PayLoadDTO<String>>() {});
        log.info("消息队列事件"+payLoadDTO.getAction().toString());
        log.info("消息队列数据"+payLoadDTO.getData());
        try {
            int i = 1/0;
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    /**
     * 测试消息监听2
     */
    @StreamListener(value = MyChannel.INPUT2,condition = "headers['mq-rabbit-msg-add']=='add-msg'")
    public void testHandlerMsg(Message<String> msg) throws IOException, RabbitMQException {
        log.info("mq 消息监听，接受消息=>{}",msg.toString());
        PayLoadDTO<String> payLoadDTO = JSONObject.parseObject(msg.getPayload(), new TypeReference<PayLoadDTO<String>>() {});
        log.info("消息队列事件"+payLoadDTO.getAction().toString());
        log.info("消息队列数据"+payLoadDTO.getData());

        int i = 1/0;

    }

}
