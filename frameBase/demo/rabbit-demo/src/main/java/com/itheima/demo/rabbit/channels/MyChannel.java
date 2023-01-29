package com.itheima.demo.rabbit.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 自定义通道myChannel
 */
public interface MyChannel {
    /**
     * 输出通道名称
     */
    String OUTPUT = "msg-channel-output";
    /**
     * 输入通道名称
     */
    String INPUT = "msg-channel-input";

    @Input(INPUT)
    public SubscribableChannel input();

    @Output(OUTPUT)
    public MessageChannel output();



}
