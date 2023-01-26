package com.itheima.demo.rabbit.dto;

import com.itheima.demo.rabbit.enums.ActionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * mq负载
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayLoadDTO<T> {
    /**
     * 消息队列事件
     */
    private ActionEnum action;
    /**
     * 消息数据
     */
    private T data;
}
