package com.itheima.demo.rabbit.callback;

import com.itheima.demo.rabbit.declaration.BaseMQDecConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * B交换机回调接口
 * 包含消息发布确认和消息回退
 */
@Component
@Slf4j
public class BCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    /**
     * 发布确认回调方法
     * 发布消息成功到交换器后会触发回调方法告之生产者
     * @param correlationData 消息相关数据
     * @param ack 交换机是否收到消息
     * @param cause 未收到的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        //TODO 交换机是否收到消息的信息
        log.info("=========触发 发布确认的回调");
        if (Objects.nonNull(correlationData)) {
            log.info("CorrelationData=>{}",correlationData.toString());
            String id = correlationData.getId();
            if (ack) {
                log.info("交换机{}已经收到 id 为:{}的消息", BaseMQDecConfig.B_EXCHANGE,id);
            }
            else {
                //TODO 对交换机未收到消息的处理
                log.error("交换机{}还未收到 id 为:{}的消息，由于原因：{}", BaseMQDecConfig.B_EXCHANGE,id,cause);
            }
        }
        else {
            log.error("CorrelationData=>{IS NULL}");
        }

    }

    /**
     * 消息回退方法
     * 当消息传递过程中不可达目的地时（及无法到达对应的队列）将消息返回给生产者 进行回退
     * @param message 消息
     * @param replyCode 回复状态码
     * @param replyText 回退原因
     * @param exchange 交换机
     * @param routingKey 路由key
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        //TODO 消息回退，对此类消息的处理
        log.info("=========触发 消息回退的回调");
        log.error("消息{}，被交换机{}退回，路由Key是{}，退回原因是{}",
                new String(message.getBody()),
                exchange,
                routingKey,
                replyText
        );
    }
}
