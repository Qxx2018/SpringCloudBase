package com.itheima.demo.rabbit.declaration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 交换机配置
 */
@Configuration
public class ExchangeConfig {
    /**
     * 交换机名称a
     */
    public static final String A_EXCHANGE = "a-exchange";
    /**
     * 声明直连交换机的bean， 该bean名称为：aExchange
     * AbstractExchange(String name, boolean durable, boolean autoDelete)
     * this(name, true, false);
     * 默认情况下，交换机是持久化，且不会自动删除
     * @return
     */
    @Bean(A_EXCHANGE)
    public DirectExchange aExchange() {
        Map<String, Object> otherArguments = new HashMap<>(1);
        //指定备用交换机，当aExchange交换机不能用时，会将消息发送到aExchange_alternate交换机上
        //otherArguments.put("alternate-exchange", "aExchange_alternate");
        /**
         * durable : 持久化
         * autoDelete: 自动删除
         *  true：当没有消费者连接时，队列会被删除，期间生产者发送到队列的消息会丢失
         *  false：没有消费者连接时，队列不会被删除，期间生产者发送到队列的消息不会丢失
         */
        return new DirectExchange(
                A_EXCHANGE,
                true,
                false,
                otherArguments
        );
    }
}
