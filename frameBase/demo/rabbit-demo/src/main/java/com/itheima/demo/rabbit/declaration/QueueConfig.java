package com.itheima.demo.rabbit.declaration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 队列配置
 */
@Configuration
public class QueueConfig {
    /**
     * 队列名称a
     */
    public static final String A_QUEUE = "a_queue";
    /**
     * 死信队列a
     */
    public static final String A_DEAD_QUEUE = "a_dead_queue";

    /**
     * 声明队列的bean, 该bean的名称a_queue
     * QueueBuilder 构建
     *      .durable() 持久化
     *      .withArguments() 其他参数
     *      .exclusive() 排他队列
     *         只针对首次申明它的 connection 连接可见。并且在连接断开时自动删除。
     *         当前 connection下的 channel 信道都可以访问。其他连接无法再申明相同名称的队列。
     */
    @Bean(A_QUEUE)
    public Queue aQueue() {
        Map<String, Object> args = new HashMap<>(2);
        //消息的最大存活时间，单位毫秒， 当超过时间后消息会被丢弃
        //默认消息存活时间为永久存在
        args.put("x-message-ttl",10000);
        //消息超出最大数量时，溢出行为： drop-head 或 reject-publish
        //（drop-head:头部丢弃， reject-publish拒绝生产者发布消息）
        args.put("x-overflow","reject-publish");
        //当队列满时，被拒绝的消息，或者消息过期时，将被重新发布到死信交换机上。
        args.put("x-dead-letter-exchange",ExchangeConfig.A_DEAD_LETTER_EXCHANGE);
        //死信路由
        args.put("x-dead-letter-routing-key",RouteConfig.A_DEAD_LETTER_ROUTING_KEY);
        //x-max-priority : 队列支持的消息最大优先级数，没设置时，队列不支持消息优先级

        return QueueBuilder.durable(A_QUEUE).withArguments(args).exclusive().build();
    }
    @Bean(A_DEAD_QUEUE)
    public Queue aDeadQueue() {
        return new Queue(A_DEAD_QUEUE,true,true,false);
    }
}
