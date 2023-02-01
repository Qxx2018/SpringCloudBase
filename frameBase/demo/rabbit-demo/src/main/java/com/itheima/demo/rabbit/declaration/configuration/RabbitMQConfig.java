//package com.itheima.demo.rabbit.declaration.configuration;
//
//import com.itheima.demo.rabbit.exceptions.RabbitMQException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.AcknowledgeMode;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.retry.RetryCallback;
//import org.springframework.retry.RetryContext;
//import org.springframework.retry.RetryListener;
//import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy;
//import org.springframework.retry.policy.SimpleRetryPolicy;
//import org.springframework.retry.support.RetryTemplate;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * rabbitmq配置声明
// * 重新定义yaml配置中的信息
// */
//@Configuration
//@Slf4j
//public class RabbitMQConfig {
//    @Resource
//    ConnectionFactory rabbitConnectionFactory;
//
//    /**
//     * 重新定义消费者的容器工厂
//     */
//    @Bean
//    public SimpleRabbitListenerContainerFactory listenerContainerFactory() {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(rabbitConnectionFactory);
//        //消息应答方式
//        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        //消费者最小数量
//        factory.setConcurrentConsumers(3);
//        //消费者最大数量
//        factory.setMaxConcurrentConsumers(10);
//        //一个消费者最多可处理的nack消息,消费者拒绝或处理异常的无法处理的消息）数量,如果有事务的话，必须大于等于transaction数量.
//        factory.setPrefetchCount(1);
//        //被拒绝的消息是否重新入队；默认是true
//        factory.setDefaultRequeueRejected(true);
//        //重试机制模板
//        factory.setRetryTemplate(retryTemplate());
//        return factory;
//    }
//    /**
//     * 定义重试机制
//     * 基于spring retry实现
//     * RetryTemplate: 封装了Retry基本操作，是进入spring-retry框架的整体流程入口,通过RetryTemplate可以指定监听、回退策略、重试策略等。
//     * RetryCallback：该接口封装了业务代码，且failback后，会再次调用RetryCallback接口
//     * RetryPolicy：重试策略，描述将以什么样的方式调用RetryCallback接口
//     * BackOffPolicy ：回退策略，当出现错误时延迟多少时间继续调用
//     *
//     * 作者：嘻洋洋
//     * 链接：https://www.jianshu.com/p/3dc4f9bbe605
//     * 来源：简书
//     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
//     */
//    @Bean
//    public RetryTemplate retryTemplate() {
//        RetryTemplate retryTemplate = new RetryTemplate();
//        //设置重试的监听（不是必须的）
//        retryTemplate.registerListener(new RetryListener() {
//            @Override
//            public <T, E extends Throwable> boolean open(RetryContext retryContext, RetryCallback<T, E> retryCallback) {
//                //执行之前调用 返回false会终止执行
//                return true;
//            }
//
//            @Override
//            public <T, E extends Throwable> void close(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
//                //重试结束后调用
//                log.info("重试结束后调用");
//            }
//
//            @Override
//            public <T, E extends Throwable> void onError(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
//                //异常调用
//                log.error("----第{}此调用",retryContext.getRetryCount());
//            }
//        });
//        //回退策略
//        retryTemplate.setBackOffPolicy(backOffPolicy());
//        //重试策略
//        retryTemplate.setRetryPolicy(retryPolicy());
//
//        return retryTemplate;
//    }
//
//    /**
//     * 定义退避策略BackOffPolicy
//     * ExponentialRandomBackOffPolicy 指数随机退避策略
//     * 配置之后在RetryTemplate中指定
//     * @return
//     */
//    @Bean
//    public ExponentialRandomBackOffPolicy backOffPolicy() {
//        ExponentialRandomBackOffPolicy  backOffPolicy = new ExponentialRandomBackOffPolicy ();
//        //初始休眠时间500ms
//        backOffPolicy.setInitialInterval(500);
//        //最大休眠时间30000ms
//        backOffPolicy.setMaxInterval(30000);
//        return backOffPolicy;
//    }
//
//    /**
//     * 定义重试策略RetryPolicy
//     * SimpleRetryPolicy 固定次数重试策略，默认重试最大次数为3次，RetryTemplate默认使用的
//     * 配置之后在RetryTemplate中指定
//     * @return
//     */
//    @Bean
//    public SimpleRetryPolicy retryPolicy() {
//        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
//        //设置重试异常和是否重试
//        retryableExceptions.put(RabbitMQException.class,true);
//        //设置重试次数和要重试的异常
//        return new SimpleRetryPolicy(5,retryableExceptions);
//    }
//}
