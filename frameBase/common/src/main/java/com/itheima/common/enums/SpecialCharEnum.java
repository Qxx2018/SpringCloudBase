package com.itheima.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 特殊字符枚举
 * @author XinXingQian
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, includeFieldNames = true)
public enum SpecialCharEnum {
    /**
     * # 井号
     */
    POUND_SIGN("#","井号"),
    /**
     * : 冒号
     */
    COLON(":","冒号"),
    ;
    /**
     * 符号
     */
    private String specialChar;
    /**
     * 字符描述
     */
    private String characterDescription;

}
