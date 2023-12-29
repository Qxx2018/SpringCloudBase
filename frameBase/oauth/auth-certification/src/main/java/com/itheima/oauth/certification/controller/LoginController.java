package com.itheima.oauth.certification.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author XinXingQian
 */
@Api(tags = "自定义登入")
@Controller
@Slf4j
public class LoginController {

    @ApiOperation(value = "oauth2自定义登入页面")
    @GetMapping("/login")
    public ModelAndView error(){
        ModelAndView view = new ModelAndView();
        view.setViewName("login");
        return view;
    }

}
