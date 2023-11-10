package com.itheima.common.handler;

import com.itheima.common.enums.BusinessExceptionEnums;
import com.itheima.common.exception.BusinessException;
import com.itheima.common.vo.Rsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * 控制器统一异常处理
 * @author XinXingQian
 */
@Slf4j
@Component
@ControllerAdvice
public class ControllerUnifyExceptionHandler {

    /**
     * 其他异常处理
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Exception.class, BusinessException.class})
    @ResponseBody
    public Rsp otherExceptionHandler(HttpServletRequest request, Exception e) {
        log.info("===========其他异常处理=========");
        log.error(e.toString());
        if (e instanceof BusinessException) {
            return Rsp.error(((BusinessException) e).getCode(),e.getMessage(),e);
        }
        return Rsp.error(e.getMessage(),e);
    }

    /**
     * 数据库异常处理
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public Rsp sqlExceptionHandler(HttpServletRequest request, SQLException e) {
        log.info("===========数据库异常处理=========");
        log.error(e.toString());
        //TODO 推送mq--记录每次异常
        return Rsp.error(BusinessExceptionEnums.DATABASE_ERROR.getCode(),BusinessExceptionEnums.DATABASE_ERROR.getMsg(),e);
    }

    /**
     * 请求参数异常处理
     * BindException：处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     * MethodArgumentNotValidException：处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是ConstraintViolationException
     * ConstraintViolationException：处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是ConstraintViolationException
     * HttpMessageNotReadableException：参数格式异常
     * IllegalArgumentException：非法参数异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({
            BindException.class,
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            HttpMessageNotReadableException.class,
            IllegalArgumentException.class
    })
    @ResponseBody
    public Rsp requestParamExceptionHandler(HttpServletRequest request, Exception e) {
        log.info("===========请求参数异常处理=============");
        log.error(e.toString());
        //TODO 推送mq--记录每次异常
        String errorMsg = "";
        if (e instanceof BindException) {
            errorMsg = Objects.requireNonNull(((BindException) e).getBindingResult().getFieldError()).getDefaultMessage();
            return Rsp.error(BusinessExceptionEnums.REQUEST_PARAM_ERROR.getCode(), BusinessExceptionEnums.REQUEST_PARAM_ERROR.getMsg(),e);
        }
        if (e instanceof MethodArgumentNotValidException) {
            errorMsg = Objects.requireNonNull(((MethodArgumentNotValidException) e).getBindingResult().getFieldError()).getDefaultMessage();
            return Rsp.error(BusinessExceptionEnums.REQUEST_PARAM_ERROR.getCode(), BusinessExceptionEnums.REQUEST_PARAM_ERROR.getMsg(),e);
        }
        if (e instanceof ConstraintViolationException) {
            return Rsp.error(BusinessExceptionEnums.REQUEST_PARAM_ERROR.getCode(), BusinessExceptionEnums.REQUEST_PARAM_ERROR.getMsg(),e);
        }
        if (e instanceof HttpMessageNotReadableException) {
            return Rsp.error(BusinessExceptionEnums.PARAM_FORMAT_ERROR.getCode(), BusinessExceptionEnums.PARAM_FORMAT_ERROR.getMsg(),e);
        }
        if (e instanceof IllegalArgumentException) {
            return Rsp.error(BusinessExceptionEnums.PARAM_ILLEGALITY_ERROR.getCode(), BusinessExceptionEnums.PARAM_ILLEGALITY_ERROR.getMsg(),e);
        }
        return Rsp.error(e.getMessage(),e);
    }
    /**
     * 请求方法不支持
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Rsp httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        log.info("=====================请求方法不支持============================");
        log.error(e.toString());
        return Rsp.error(BusinessExceptionEnums.REQUEST_METHOD_NOT_SUPPORT_ERROR.getCode(),BusinessExceptionEnums.REQUEST_METHOD_NOT_SUPPORT_ERROR.getMsg(),e);
    }
    /**
     * 请求媒体类型不支持
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public Rsp httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, HttpMediaTypeNotSupportedException e) {
        log.info("=====================请求媒体类型不支持============================");
        log.error(e.toString());
        return Rsp.error(BusinessExceptionEnums.MEDIA_TYPE_NOT_SUPPORT.getCode(),BusinessExceptionEnums.MEDIA_TYPE_NOT_SUPPORT.getMsg(),e);
    }
}
