package com.itheima.oauth.certification.handler;

import com.itheima.sys.core.enums.BusinessExceptionEnums;
import com.itheima.sys.core.resp.Rsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Oauth2抛出的异常处理
 * @author XinXingQian
 */
@Slf4j
@Component
@ControllerAdvice
public class Oauth2ExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public Rsp handleOauth2(OAuth2Exception e) {
        log.info("===========Oauth2抛出的异常处理=========");
        log.error(e.toString());
        return Rsp.error(BusinessExceptionEnums.OAUTH2_ERROR.getCode(),BusinessExceptionEnums.OAUTH2_ERROR.getMsg(),e);
    }
}
