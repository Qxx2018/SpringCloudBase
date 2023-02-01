//package com.itheima.demo.rabbit.receiver;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.TypeReference;
//import com.itheima.demo.rabbit.declaration.BaseMQDecConfig;
//import com.itheima.demo.rabbit.dto.PayLoadDTO;
//import com.itheima.demo.rabbit.exceptions.RabbitMQException;
//import com.rabbitmq.client.Channel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Slf4j
//@Component
//@Transactional(rollbackFor = {RabbitMQException.class})
//public class BReceiver {
//    /**
//     * 测试消息监听
//     */
//    @RabbitListener(queues = BaseMQDecConfig.B_QUEUE)
//    public void testHandlerMsgB(org.springframework.amqp.core.Message message, Channel channel) throws RabbitMQException {
//        String msg = new String(message.getBody());
//        log.info("mq 消息监听，接受消息=>{}",msg);
//        PayLoadDTO<String> payLoadDTO = JSONObject.parseObject(msg, new TypeReference<PayLoadDTO<String>>() {});
//        log.info("消息队列事件"+payLoadDTO.getAction().toString());
//        log.info("消息队列数据"+payLoadDTO.getData());
//        throw new RabbitMQException("消息消费异常");
//    }
//}
