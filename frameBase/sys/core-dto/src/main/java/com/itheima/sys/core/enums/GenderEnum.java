package com.itheima.sys.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 性别
 * @author XinXingQian
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, includeFieldNames = true)
public enum  GenderEnum {
    /**
     * M 男
     */
    M("M","男"),
    /**
     * F 女
     */
    F("F","女"),
    ;
    /**
     * 符号
     */
    private String code;
    /**
     * 字符描述
     */
    private String msg;
}
