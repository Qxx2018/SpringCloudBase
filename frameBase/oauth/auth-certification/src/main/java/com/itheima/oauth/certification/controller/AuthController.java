package com.itheima.oauth.certification.controller;


import com.itheima.common.vo.Rsp;
import com.itheima.oauth.certification.business.service.ImageValidateCodeService;
import com.itheima.oauth.certification.business.service.ValidateCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

/**
 * 认证中心
 * @author XinXingQian
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
@Slf4j
public class AuthController {
    @Resource
    private ValidateCodeService validateCodeService;
    @Resource
    private ImageValidateCodeService imageValidateCodeService;
    /**
     * 生成图像验证码
     * @param deviceId
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/getImgCode")
    public Rsp<String> getImgCode(@RequestParam(value = "deviceId") @NotBlank(message = "机器码不能为空") String deviceId, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        return imageValidateCodeService.createCode(deviceId);
    }

    /**
     * 发送手机短信验证码
     * @param phone
     * @return
     */
    @GetMapping("/sendSmsCode")
    public Rsp<String> sendSmsCode(@RequestParam(value = "phone") @NotBlank(message = "手机号不填") String phone) {
        return validateCodeService.sendSmsCode(phone);
    }
}
