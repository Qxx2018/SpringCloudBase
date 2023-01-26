package com.itheima.demo.rabbit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息队列事件
 */
@AllArgsConstructor
@Getter
public enum ActionEnum {
    /**
     * 新增
     */
    ADD("add","新增"),
    /**
     * 修改
     */
    MODIFY("modify","修改"),
    /**
     * 删除
     */
    DELETE("delete","删除")
    ;

    private final String code;

    private final String msg;


    @Override
    public String toString() {
        return this.code;
    }
}
