//package com.itheima.demo.rabbit2.declaration.configuration;
//
//import com.itheima.demo.rabbit2.exceptions.RabbitMQException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.stream.annotation.StreamRetryTemplate;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.retry.RetryCallback;
//import org.springframework.retry.RetryContext;
//import org.springframework.retry.RetryListener;
//import org.springframework.retry.backoff.ExponentialBackOffPolicy;
//import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy;
//import org.springframework.retry.policy.SimpleRetryPolicy;
//import org.springframework.retry.support.RetryTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 消息重试机制配置
// */
//@Configuration
//@Slf4j
//public class RetryConfiguration {
//    /**
//     * 重试机制模板
//     * @return
//     */
//    @StreamRetryTemplate
//    public RetryTemplate retryTemplate() {
//        RetryTemplate retryTemplate = new RetryTemplate();
//        //重试策略
//        retryTemplate.setRetryPolicy(retryPolicy());
//        //回退策略
//        retryTemplate.setBackOffPolicy(backOffPolicy());
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
//                log.info("=====已重试结束");
//            }
//
//            @Override
//            public <T, E extends Throwable> void onError(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
//                //异常调用
//                log.error("----第{}此调用",retryContext.getRetryCount());
//            }
//        });
//        return retryTemplate;
//    }
//
//    /**
//     * 定义退避策略BackOffPolicy
//     * ExponentialRandomBackOffPolicy 指数随机退避策略
//     * @return
//     */
//    private ExponentialBackOffPolicy backOffPolicy() {
//        ExponentialRandomBackOffPolicy backOffPolicy = new ExponentialRandomBackOffPolicy ();
//        //初始休眠时间1000ms
//        backOffPolicy.setInitialInterval(1000);
//        //最大休眠时间10000ms
//        backOffPolicy.setMaxInterval(10000);
//        //避退乘数，默认2.0
//        backOffPolicy.setMultiplier(1.5);
//        return backOffPolicy;
//    }
//
//    /**
//     * 定义重试策略RetryPolicy
//     * SimpleRetryPolicy 固定次数重试策略，默认重试最大次数为3次，RetryTemplate默认使用的
//     * @return
//     */
//    private SimpleRetryPolicy retryPolicy() {
//        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
//        //设置重试异常和是否重试
//        retryableExceptions.put(RabbitMQException.class,true);
//        //设置重试次数和要重试的异常
//        return new SimpleRetryPolicy(5,retryableExceptions);
//    }
//
//}
