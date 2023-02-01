//package com.itheima.demo.rabbit.declaration;
//
//import org.springframework.amqp.core.*;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 基础 -交换机、路由、队列声明
// */
//@Configuration
//public class BaseMQDecConfig {
//    /**
//     * 交换机b
//     */
//    public static final String B_EXCHANGE = "b-exchange";
//    /**
//     * 队列b
//     */
//    public static final String B_QUEUE = "b-queue";
//    /**
//     * 路由b
//     */
//    public static final String B_KEY = "b-key";
//    /**
//     * 声明主题交换机B
//     */
//    @Bean(B_EXCHANGE)
//    public TopicExchange bExchange() {
//        return new TopicExchange(
//                B_EXCHANGE,
//                true,
//                true
//        );
//    }
//    /**
//     * 声明队列B
//     */
//    @Bean(B_QUEUE)
//    public Queue bQueue() {
//        Map<String, Object> args = new HashMap<>(2);
//        //消息的最大存活时间，单位毫秒， 当超过时间后消息会被丢弃
//        //默认消息存活时间为永久存在
//        //10S
//        args.put("x-message-ttl",10000);
//        //消息超出最大数量时，溢出行为： drop-head 或 reject-publish
//        //（drop-head:头部丢弃， reject-publish拒绝生产者发布消息）
//        args.put("x-overflow","reject-publish");
//        //当队列满时，被拒绝的消息，或者消息过期时，将被重新发布到死信交换机上。
//        //args.put("x-dead-letter-exchange",ExchangeConfig.A_DEAD_LETTER_EXCHANGE);
//        //死信路由
//        //args.put("x-dead-letter-routing-key",RouteConfig.A_DEAD_LETTER_ROUTING_KEY);
//        //x-max-priority : 队列支持的消息最大优先级数，没设置时，队列不支持消息优先级
//        return QueueBuilder.durable(B_QUEUE)
//                .autoDelete()
//                .exclusive()
//                .withArguments(args).build();
//    }
//    /**
//     * 队列b和交换机b绑定，并指定路由
//     */
//    @Bean
//    public Binding bindBQueToBExc(@Qualifier(B_QUEUE) Queue queue,@Qualifier(B_EXCHANGE) TopicExchange topicExchange) {
//        return BindingBuilder.bind(queue).to(topicExchange).with(B_KEY);
//    }
//}
