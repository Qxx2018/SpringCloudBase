package com.itheima.demo.rabbit.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义Aa通道
 */
public interface AaChannel {
    /**
     * 输出通道
     */
    String OUTPUT = "a-channel-output";
    /**
     * 输入通道名称
     */
    String INPUT = "a-channel-input";

    @Input(INPUT)
    public SubscribableChannel input();

    @Output(OUTPUT)
    public MessageChannel output();
}
