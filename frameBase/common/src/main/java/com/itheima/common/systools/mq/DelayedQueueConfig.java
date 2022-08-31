package com.itheima.common.systools.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * 基于插件的延迟队列配置类
 * @author 10445
 */
@Configuration
public class DelayedQueueConfig {
    /**
     * 交换机X
     */
    public static final String DELAYED_EXCHANGE_X = "delayedExchangeX";
    /**
     * 队列X
     */
    public static final String DELAYED_QUEUE_X = "delayedQueueX";
    /**
     * 路由routingKey X
     */
    public static final String DELAYED_ROUTING_KEY_X = "delayedRoutingKeyX";
    /**
     * 声明队列X
     */
    @Bean(DELAYED_QUEUE_X)
    public Queue delayedQueue() {
        return new Queue(DELAYED_QUEUE_X);
    }
    /**
     * 声明交换机X 基于延时插件的交换机
     */
    @Bean(DELAYED_EXCHANGE_X)
    public CustomExchange delayedExchange() {
        HashMap<String, Object> arguments = new HashMap<>();
        //设置延时类型
        arguments.put("x-delayed-type","direct");
        /**
         * 1、交换机名称
         * 2、交换机类型
         * 3、是否需要持久化
         * 4、是否需要自动删除
         * 5、其他参数
         */
        return new CustomExchange(
                DELAYED_EXCHANGE_X,
                "x-delayed-message",
                true,
                false,
                arguments
        );
    }
    /**
     * 绑定队列和延时交换机
     */
    @Bean
    public Binding delayedQueueBindingDelayedExchange(
            @Qualifier(DELAYED_QUEUE_X) Queue queue,
            @Qualifier(DELAYED_EXCHANGE_X) CustomExchange exchange
            ) {
        return BindingBuilder.bind(queue).to(exchange).with(DELAYED_ROUTING_KEY_X).noargs();
    }

}
