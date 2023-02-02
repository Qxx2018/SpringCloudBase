package com.itheima.demo.rabbit.declaration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础 -交换机、路由、队列声明
 */
@Configuration
public class BaseMQDecConfig {
    /**
     * 交换机b
     */
    public static final String B_EXCHANGE = "b-exchange";
    /**
     * 队列b
     */
    public static final String B_QUEUE = "b-queue";
    /**
     * 队列b2
     */
    public static final String B2_QUEUE = "b2-queue";
    /**
     * 路由b
     */
    public static final String B_KEY = "b-key";
    /**
     * 路由b2
     */
    public static final String B2_KEY = "b2-key";
    /**
     * 死信交换机b
     */
    public static final String B_DEAD_EXCHANGE = "b-dead-exchange";
    /**
     * 死信队列b
     */
    public static final String B_DEAD_QUEUE = "b-dead-queue";
    /**
     * 死信路由b
     */
    public static final String B_DEAD_KEY = "b-dead-key";
    /**
     * 声明主题交换机B
     */
    @Bean(B_EXCHANGE)
    public TopicExchange bExchange() {
        return new TopicExchange(
                B_EXCHANGE,
                true,
                false
        );
    }
    /**
     * 声明队列B
     */
    @Bean(B_QUEUE)
    public Queue bQueue() {
        Map<String, Object> args = new HashMap<>(2);
        //消息的最大存活时间，单位毫秒， 当超过时间后消息会被丢弃
        //默认消息存活时间为永久存在
        //10S
        args.put("x-message-ttl",10000);
        //消息超出最大数量时，溢出行为： drop-head 或 reject-publish
        //（drop-head:头部丢弃， reject-publish拒绝生产者发布消息）
        args.put("x-overflow","reject-publish");
        //当队列满时，被拒绝的消息，或者消息过期时，将被重新发布到死信交换机上。
        args.put("x-dead-letter-exchange",B_DEAD_EXCHANGE);
        //死信路由
        args.put("x-dead-letter-routing-key",B_DEAD_KEY);
        //x-max-priority : 队列支持的消息最大优先级数，没设置时，队列不支持消息优先级
        return new Queue(B_QUEUE,true,false,false,args);
    }
    /**
     * 声明队列B2
     */
    @Bean(B2_QUEUE)
    public Queue b2Queue() {
        Map<String, Object> args = new HashMap<>(2);
        //消息的最大存活时间，单位毫秒， 当超过时间后消息会被丢弃
        //默认消息存活时间为永久存在
        args.put("x-message-ttl",10000);
        //消息超出最大数量时，溢出行为： drop-head 或 reject-publish
        //（drop-head:头部丢弃， reject-publish拒绝生产者发布消息）
        args.put("x-overflow","reject-publish");
        //当队列满时，被拒绝的消息，或者消息过期时，将被重新发布到死信交换机上。
        args.put("x-dead-letter-exchange",B_DEAD_EXCHANGE);
        //死信路由
        args.put("x-dead-letter-routing-key",B_DEAD_KEY);
        //x-max-priority : 队列支持的消息最大优先级数，没设置时，队列不支持消息优先级
        return new Queue(B2_QUEUE,true,false,false,args);
    }
    /**
     * 队列b和交换机b绑定，并指定路由
     */
    @Bean
    public Binding bindBQueToBExc(@Qualifier(B_QUEUE) Queue queue,@Qualifier(B_EXCHANGE) TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(B_KEY);
    }
    /**
     * 队列b2和交换机b绑定，并指定路由
     */
    @Bean
    public Binding bindB2QueToBExc(@Qualifier(B2_QUEUE) Queue queue,@Qualifier(B_EXCHANGE) TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(B2_KEY);
    }
    /**
     * 声明死信交换机B
     */
    @Bean(B_DEAD_EXCHANGE)
    public TopicExchange bDeadExchange() {
        return new TopicExchange(
                B_DEAD_EXCHANGE,
                true,
                false
        );
    }
    /**
     * 声明死信队列B
     */
    @Bean(B_DEAD_QUEUE)
    public Queue bDeadQueue() {
        Map<String, Object> args = new HashMap<>(2);
        return new Queue(B_DEAD_QUEUE,true,false,false,args);
    }
    /**
     * 死信队列b和死信交换机b绑定，并指定死信路由b
     */
    @Bean
    public Binding bindBDeadQueToBDeadExc(@Qualifier(B_DEAD_QUEUE) Queue queue,@Qualifier(B_DEAD_EXCHANGE) TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(B_DEAD_KEY);
    }
}
