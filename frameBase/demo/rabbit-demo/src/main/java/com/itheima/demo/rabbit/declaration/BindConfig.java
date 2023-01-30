package com.itheima.demo.rabbit.declaration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindConfig {
    /**
     * 队列a和交换机a绑定，并指定路由
     */
    @Bean
    public Binding aBindExcWithQue(@Qualifier(QueueConfig.A_QUEUE) Queue queue,
                                   @Qualifier(ExchangeConfig.A_EXCHANGE) DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(RouteConfig.A_ROUTING_KEY);
    }
    /**
     * 死信队列a和死信交换机a绑定，并指定路由
     */
    @Bean
    public Binding aDeadLetterBindExcWithQue(@Qualifier(QueueConfig.A_DEAD_QUEUE) Queue queue,
                                             @Qualifier(ExchangeConfig.A_DEAD_LETTER_EXCHANGE) DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(RouteConfig.A_DEAD_LETTER_ROUTING_KEY);
    }
}
