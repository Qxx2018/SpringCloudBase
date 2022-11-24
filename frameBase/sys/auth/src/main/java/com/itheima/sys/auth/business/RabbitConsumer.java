package com.itheima.sys.auth.business;

import com.itheima.sys.auth.config.mq.DelayedQueueConfig;
import com.itheima.sys.auth.config.mq.TtlQueueConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消费者
 * @author qxx
 */
@Component
@Slf4j
public class RabbitConsumer {
    /**
     * 消费者直接监听死信队列 死信队列中的消息就是延时消息
     */
    @RabbitListener(queues = TtlQueueConfig.DEAD_LETTER_QUEUE_A)
    public void receiveA(Message message, Channel channel) throws IOException {
        System.out.println(message.toString());
        String msg = new String(message.getBody());
        log.info("死信队列A收到消息{}",msg);
        //channel.basicAck去手动回复消息处理状态，
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
    /**
     * 监听队列X消息
     */
    @RabbitListener(queues = {DelayedQueueConfig.DELAYED_QUEUE_X})
    public void receiveDelayQueueX(Message message,Channel channel) throws IOException {
        System.out.println(message.toString());
        String msg = new String(message.getBody());
        log.info("队列X收到消息{}",msg);
        //channel.basicAck去手动回复消息处理状态，
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
