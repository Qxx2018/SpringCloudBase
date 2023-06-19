package com.itheima.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常类
 * @author XinXingQian
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 4522867983579260630L;

    private String code;


    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String code, String msg) {
        super(msg);
        this.code = code;
    }



}
