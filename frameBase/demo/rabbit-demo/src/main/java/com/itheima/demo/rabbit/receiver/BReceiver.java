package com.itheima.demo.rabbit.receiver;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.itheima.demo.rabbit.declaration.BaseMQDecConfig;
import com.itheima.demo.rabbit.dto.PayLoadDTO;
import com.itheima.demo.rabbit.enums.ActionEnum;
import com.itheima.demo.rabbit.exceptions.RabbitMQException;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Component
@Transactional(rollbackFor = {RabbitMQException.class})
public class BReceiver {
    /**
     *消费执行次数
     */
    private Integer count = 1;
    /**
     * 测试消息监听
     * 消息手动ack机制
     */
    @RabbitListener(queues = BaseMQDecConfig.B_QUEUE)
    public void testHandlerMsgB(org.springframework.amqp.core.Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            String msg = new String(message.getBody());
            log.info("mq 消息监听，接受消息=>{}",msg);
            PayLoadDTO<String> payLoadDTO = JSONObject.parseObject(msg, new TypeReference<PayLoadDTO<String>>() {});
            log.info("消息队列事件=>{}",payLoadDTO.getAction().toString());
            log.info("消息队列数据=>{}",payLoadDTO.getData());
            if (ActionEnum.DELETE.getCode().equals(payLoadDTO.getAction().getCode()))  {
                //TODO 过滤自定义的无法处理的消息,进行消息拒绝
                //‘删除消息’进行消息的拒绝
                /**
                 * basicReject 消息的拒绝
                 *   第2个参数决定是否重新投递，true是消息重新投递，false消息丢失
                 */
                log.error("消息拒绝=>{}","不满足消费条件");
                channel.basicReject(tag,false);
            }
            else {
                //TODO 进行消息处理
                log.info("-------消息处理");
                //模拟消费异常
                //throw new RabbitMQException("模拟消费异常");

            }
            channel.basicAck(tag,false);
        }
        catch (Exception e) {
            //TODO 消费异常后对消息的拒绝
            log.error("消费异常=>{},{}",e.getMessage(),"消息拒绝");
            channel.basicReject(tag,false);
        }


    }

    /**
     * 测试消息2监听
     * 消息重试机制
     */
    @RabbitListener(queues = BaseMQDecConfig.B2_QUEUE)
    public void testHandlerMsgB2(org.springframework.amqp.core.Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws RabbitMQException, IOException {
        log.info("消息消费第{}次执行",count);
        //代码中不能使用try/catch捕获异常，否则重试机制失效,需要在catch中throw异常触发重试机制
        try {
            String msg = new String(message.getBody());
            log.info("mq 消息监听，接受消息=>{}",msg);
            PayLoadDTO<String> payLoadDTO = JSONObject.parseObject(msg, new TypeReference<PayLoadDTO<String>>() {});
            log.info("消息队列事件=>{}",payLoadDTO.getAction().toString());
            log.info("消息队列数据=>{}",payLoadDTO.getData());

            //TODO 制造异常
            int i =1/0;
            log.info("消息手动应答");
            channel.basicAck(tag,false);

        }
        catch (Exception e) {
            //TODO 在catch中throw异常触发重试机制
            log.error("消费异常=>{},{}",e.getMessage(),"触发重试机制");
            log.info("在catch中throw异常触发重试机制");
            throw e;
        }
        finally {
            if (count == 5) {
                log.info("重试次数到达阈值，消息拒绝，推入死信队列做不差机制");
                channel.basicReject(tag,false);
            }
            count++;
        }

    }

    /**
     * 测试消息3监听
     * 消息重试机制
     */
    @RabbitListener(queues = BaseMQDecConfig.B3_QUEUE)
    public void testHandlerMsgB3(org.springframework.amqp.core.Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws RabbitMQException, IOException {
        String msg = new String(message.getBody());
        log.info("mq 消息监听，接受消息=>{}",msg);
        PayLoadDTO<String> payLoadDTO = JSONObject.parseObject(msg, new TypeReference<PayLoadDTO<String>>() {});
        log.info("消息队列事件=>{}",payLoadDTO.getAction().toString());
        log.info("消息队列数据=>{}",payLoadDTO.getData());
        try {
            int i =1/0;
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }










    }
}
