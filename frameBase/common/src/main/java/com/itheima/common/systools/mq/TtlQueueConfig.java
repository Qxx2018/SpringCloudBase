package com.itheima.common.systools.mq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 延时队列   队列TTL+DLX配置
 * @author 10445
 */
@Configuration
public class TtlQueueConfig {
    /**
     * 延时交换机A
     */
    public static final String DELAY_EXCHANGE_A = "delayExchangeA";
    /**
     * 延时路由A
     */
    public static final String DELAY_ROUTING_KEY_A = "delayRoutingA";
    /**
     * 延时队列A
     */
    public static final String DELAY_QUEUE_A = "delayQueueA";

    /**
     * 死信交换机A
     */
    public static final String DEAD_LETTER_EXCHANGE_A = "deadLetterExchangeA";

    /**
     * 死信路由A
     */
    public static final String DEAD_ROUTING_KEY_A = "deadRoutingA";

    /**
     * 死信队列A
     */
    public static final String DEAD_LETTER_QUEUE_A = "deadLetterQueueA";


    /**
     * 声明延时交换机A
     */
    @Bean(DELAY_EXCHANGE_A)
    public DirectExchange delayExchangeA() {
        return new DirectExchange(DELAY_EXCHANGE_A);
    }
    /**
     * 声明死信交换机A
     */
    @Bean(DEAD_LETTER_EXCHANGE_A)
    public DirectExchange deadLetterExchangeA() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE_A);
    }
    /**
     * 声明死信队列A
     */
    @Bean(DEAD_LETTER_QUEUE_A)
    public Queue deadLetterQueueA() {
        return new Queue(DEAD_LETTER_QUEUE_A);
    }
    /**
     * 声明延时队列A
     * 并绑定到对应的死信交换机 声明死信路由
     */
    @Bean(DELAY_QUEUE_A)
    public Queue delayQueueA() {
        Map<String, Object> args = new HashMap<>(2);
        //x-dead-letter-exchange当前队列绑定死信交换机
        args.put("x-dead-letter-exchange",DEAD_LETTER_EXCHANGE_A);
        //x-dead-letter-routing-key当前队列的死信路由key
        args.put("x-dead-letter-routing-key",DEAD_ROUTING_KEY_A);
        //声明队列的ttl 延时10秒 参数是毫秒
        args.put("x-message-ttl",10000);
        return QueueBuilder.durable(DELAY_QUEUE_A).withArguments(args).build();
    }

    /**
     * 延时队列A与延时交换机A绑定 并指定延时队列路由
     */
    @Bean
    public Binding delayBindingA(@Qualifier(DELAY_QUEUE_A) Queue queue,
                                 @Qualifier(DELAY_EXCHANGE_A) DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(DELAY_ROUTING_KEY_A);
    }
    /**
     * 死信队列A与死信交换机A绑定 并指定死信队列路由
     */
    @Bean
    public Binding deadLetterBingingA(@Qualifier(DEAD_LETTER_QUEUE_A) Queue queue,
                                      @Qualifier(DEAD_LETTER_EXCHANGE_A) DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_ROUTING_KEY_A);
    }


}
