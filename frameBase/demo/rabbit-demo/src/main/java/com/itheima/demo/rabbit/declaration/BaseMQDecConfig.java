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
     * 队列b3
     */
    public static final String B3_QUEUE = "b3-queue";
    /**
     * 路由b
     */
    public static final String B_KEY = "b-key";
    /**
     * 路由b2
     */
    public static final String B2_KEY = "b2-key";
    /**
     * 路由b3
     */
    public static final String B3_KEY = "b3-key";
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
     * 备份交换机b
     */
    public static final String B_BACKUP_EXCHANGE = "b-backup-exchange";
    /**
     * 备份队列b
     */
    public static final String B_BACKUP_QUEUE = "b-backup-queue";
    /**
     * 报警队列b
     */
    public static final String B_WARNING_QUEUE = "b-warning-queue";
    /**
     * 声明主题交换机B
     */
    @Bean(B_EXCHANGE)
    public TopicExchange bExchange() {
        /**
         * 此交换机配置了配份交换机，以确保宕机后将消息转发到备份交换机
         * 或者接收到一条不可路由消息时，将会把这条消息转发到备份交换机中
         * 备份交换机比消息回退优先级高
         */
        Map<String, Object> arguments = new HashMap<>(1);
        arguments.put("alternate-exchange",B_BACKUP_EXCHANGE);
        return new TopicExchange(
                B_EXCHANGE,
                true,
                false,
                arguments
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
     * 声明队列B3
     */
    @Bean(B3_QUEUE)
    public Queue b3Queue() {
        Map<String, Object> args = new HashMap<>(2);
        //消息的最大存活时间，单位毫秒， 当超过时间后消息会被丢弃
        //默认消息存活时间为永久存在
        args.put("x-message-ttl",60000);
        //消息超出最大数量时，溢出行为： drop-head 或 reject-publish
        //（drop-head:头部丢弃， reject-publish拒绝生产者发布消息）
        args.put("x-overflow","reject-publish");
        //当队列满时，被拒绝的消息，或者消息过期时，将被重新发布到死信交换机上。
        args.put("x-dead-letter-exchange",B_DEAD_EXCHANGE);
        //死信路由
        args.put("x-dead-letter-routing-key",B_DEAD_KEY);
        //x-max-priority : 队列支持的消息最大优先级数，没设置时，队列不支持消息优先级
        return new Queue(B3_QUEUE,true,false,false,args);
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
     * 队列b3和交换机b绑定，并指定路由
     */
    @Bean
    public Binding bindB3QueToBExc(@Qualifier(B3_QUEUE) Queue queue,@Qualifier(B_EXCHANGE) TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(B3_KEY);
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

    /**
     * 声明备份交换机b
     * 属于Fanout广播类型:能把所有消息都投递到与其绑定的队列中
     */
    @Bean(B_BACKUP_EXCHANGE)
    public FanoutExchange bBackUpExchange() {
        return new FanoutExchange(
                B_BACKUP_EXCHANGE,
                true,
                false
        );
    }
    /**
     * 声明备份队列b
     */
    @Bean(B_BACKUP_QUEUE)
    public Queue bBackUpQueue() {
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
        return new Queue(B_BACKUP_QUEUE,true,false,false,args);
    }
    /**
     * 声明告警队列b
     */
    @Bean(B_WARNING_QUEUE)
    public Queue bWarningQueue() {
        return new Queue(B_WARNING_QUEUE,false,false,false);
    }
    /**
     * 备份队列b绑定到备份交换机b
     */
    @Bean
    public Binding bindBBackUpQueToBBackUpExc(@Qualifier(B_BACKUP_QUEUE) Queue queue,@Qualifier(B_BACKUP_EXCHANGE) FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }
    /**
     * 报警队列b绑定到备份交换机b
     */
    @Bean
    public Binding bindBWarningQueToBBackUpExc(@Qualifier(B_WARNING_QUEUE) Queue queue,@Qualifier(B_BACKUP_EXCHANGE) FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }


}
