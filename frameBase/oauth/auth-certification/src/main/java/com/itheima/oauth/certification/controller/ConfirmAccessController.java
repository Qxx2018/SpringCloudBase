package com.itheima.oauth.certification.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 自定义授权页面
 * @author wxy
 * @date 2017/4/4 0004
 */
@Api(tags = "自定义授权")
@Controller
@SessionAttributes("authorizationRequest")
@Slf4j
public class ConfirmAccessController {

    @ApiOperation(value = "授权页面")
    @GetMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {

        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView view = new ModelAndView();
        view.setViewName("base-grant");
        view.addObject("clientId", authorizationRequest.getClientId());
        view.addObject("response_type",authorizationRequest.getResponseTypes());
        view.addObject("redirect_uri",authorizationRequest.getRedirectUri());
        view.addObject("scopes",authorizationRequest.getScope());
        view.addObject("state",authorizationRequest.getState());
        return view;
    }
    @ApiOperation(value = "处理授权异常的跳转页面")
    @GetMapping("/oauth/error")
    public ModelAndView error(){
        ModelAndView view = new ModelAndView();
        view.setViewName("oauth-error");
        return view;
    }

}