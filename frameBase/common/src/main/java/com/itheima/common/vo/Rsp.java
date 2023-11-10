package com.itheima.common.vo;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.itheima.common.enums.RspCode;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 10445
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true, includeFieldNames = true)
public class Rsp<T> implements Serializable {

    private static final long serialVersionUID = 8468077357195575790L;

    private String code;

    private String msg;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long timestamp;

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date time;

    private T data;

    private BusinessInfo businessInfo;

    private Exception exception;

    public static <T> Rsp<T> error(String resultMsg) {
        return new Rsp<T>().code(RspCode.FAIL.getCode()).msg(RspCode.FAIL.getMsg())
                .businessInfo(BusinessInfo.builder().resultMsg(resultMsg).build());
    }

    public static <T> Rsp<T> error(String resultMsg,Exception e) {
        return new Rsp<T>().code(RspCode.FAIL.getCode()).msg(RspCode.FAIL.getMsg())
                .businessInfo(BusinessInfo.builder().resultMsg(resultMsg).build())
                .exception(e);
    }

    public static <T> Rsp<T> error(Exception e) {
        return new Rsp<T>().code(RspCode.FAIL.getCode()).msg(RspCode.FAIL.getMsg())
                .exception(e);
    }

    public static <T> Rsp<T> error(String resultCode, String resultMsg) {
        return new Rsp<T>().code(RspCode.FAIL.getCode()).msg(RspCode.FAIL.getMsg())
                .businessInfo(BusinessInfo.builder().resultCode(resultCode).resultMsg(resultMsg).build());
    }

    public static <T> Rsp<T> error(RspCode rspCode, String resultCode, String resultMsg) {
        return new Rsp<T>().code(rspCode.getCode()).msg(rspCode.getMsg())
                .businessInfo(BusinessInfo.builder().resultCode(resultCode).resultMsg(resultMsg).build());
    }

    public static <T> Rsp<T> error(String resultCode, String resultMsg, T data) {
        return new Rsp<T>().code(RspCode.FAIL.getCode()).msg(RspCode.FAIL.getMsg())
                .businessInfo(BusinessInfo.builder().resultCode(resultCode).resultMsg(resultMsg).build());
    }

    public static <T> Rsp<T> error(String resultCode, String resultMsg, Exception e) {
        return new Rsp<T>().code(RspCode.FAIL.getCode()).msg(RspCode.FAIL.getMsg())
                .businessInfo(BusinessInfo.builder().resultCode(resultCode).resultMsg(resultMsg).build())
                .exception(e);
    }

    public static <T> Rsp<T> error(String resultCode, String resultMsg, T data, Exception e) {
        return new Rsp<T>().code(RspCode.FAIL.getCode()).msg(RspCode.FAIL.getMsg())
                .businessInfo(BusinessInfo.builder().resultCode(resultCode).resultMsg(resultMsg).build())
                .data(data).exception(e);
    }

    public static <T> Rsp<T> ok() {
        return new Rsp<T>().code(RspCode.SUCCESS.getCode()).msg(RspCode.SUCCESS.getMsg());
    }

    public static <T> Rsp<T> ok(T data) {
        return new Rsp<T>().data(data).code(RspCode.SUCCESS.getCode()).msg(RspCode.SUCCESS.getMsg());
    }

    public Rsp() {
        this.time = DateUtil.date();
        this.timestamp = this.time.getTime();
        
    }

    public Rsp<T> code(String code) {
        this.code = code;
        return this;
    }

    public Rsp<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Rsp<T> data(T data) {
        this.data = data;
        return this;
    }

    public Rsp<T> exception(Exception e) {
        this.exception = e;
        return this;
    }

    public Rsp<T> businessInfo(BusinessInfo businessInfo) {
        this.businessInfo = businessInfo;
        return this;
    }

    public boolean isOK() {
        if (null != this.code && this.code.equals(RspCode.SUCCESS.getCode())) {
            return true;
        }
        return false;
    }

    /**
     * 异常码
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessInfo {

        private String resultCode;

        private String resultMsg;
    }
}
