package com.itheima.oauth.certification.controller;


import com.itheima.common.vo.Rsp;
import com.itheima.oauth.certification.business.service.ImageValidateCodeService;
import com.itheima.oauth.certification.business.service.ValidateCodeService;
import com.itheima.oauth.certification.dto.accredit.PasswordModelLoginDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.security.Principal;

/**
 * 认证中心
 * @author XinXingQian
 */
@Api(tags = "认证中心")
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

    /**本系统Oauth**/
    @PostMapping("/password/login")
    @ApiOperation(value = "OAuth2认证-账号密码登入")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Authorization",value = "Example: Basic Y2xpZW50OnNlY3JldA==", required = true)
    })
    public Rsp<String> passwordModelLogin(
            @ApiIgnore Principal principal,
            @RequestBody PasswordModelLoginDTO passwordModelLoginDTO
            )
    {

        return Rsp.ok();
    }

    /**作为第三方系统的OAuth**/
}
