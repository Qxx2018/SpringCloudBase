package com.itheima.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 10445
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, includeFieldNames = true)
public enum RspCode {
    SUCCESS(0, "请求成功"),
    FAIL(-1, "业务处理失败"),
    ;

    private Integer code;
    private String msg;

    /**
     * 通过code返回对应的错误信息
     *
     * @param code
     * @return
     */
    public static String getMsg(Integer code) {
        for (RspCode apiCodeEnum : RspCode.values()) {
            if (code.equals(apiCodeEnum.getCode())) {
                return apiCodeEnum.getMsg();
            }
        }
        return FAIL.getMsg();
    }
}
