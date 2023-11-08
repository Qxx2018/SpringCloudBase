package com.itheima.oauth.certification.controller;

import com.itheima.common.utils.RedisUtil;
import com.itheima.common.vo.Rsp;
import com.itheima.oauth.certification.business.service.ImageValidateCodeService;
import com.itheima.oauth.certification.business.service.TokenEndpointService;
import com.itheima.oauth.certification.business.service.ValidateCodeService;
import com.itheima.oauth.certification.constants.AuthConstants;
import com.itheima.oauth.certification.dto.accredit.PasswordModelLoginDTO;
import com.itheima.oauth.certification.vo.Oauth2TokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Objects;

/**
 * 登入控制器
 * @author XinXingQian
 */
@Api(tags = "登入控制器")
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Resource
    private ValidateCodeService validateCodeService;
    @Resource
    private ImageValidateCodeService imageValidateCodeService;
    @Resource
    private TokenEndpointService tokenEndpointService;
    @Resource
    private RedisUtil<String> redisUtil;
    /**
     * 生成图像验证码
     * @param deviceId
     * @param response
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "生成图像验证码")
    @GetMapping("/getImgCode")
    public Rsp<String> getImgCode(
            @ApiParam(name = "deviceId",value = "设备码",required = true)
            @RequestParam(value = "deviceId")
            @NotBlank(message = "设备码不能为空")
            String deviceId,
            @ApiIgnore
            HttpServletResponse response) throws IOException {
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
    @ApiOperation(value = "发送手机短信验证码")
    @GetMapping("/sendSmsCode")
    public Rsp<String> sendSmsCode(
            @ApiParam(name = "phone",value = "手机号",required = true)
            @RequestParam(value = "phone") @NotBlank(message = "手机号必填") String phone) {
        return validateCodeService.sendSmsCode(phone);
    }
    /**
     * 用户名密码图像验证码登入
     */
    @ApiOperation(value = "用户名密码图像验证码登入")
    @PostMapping("/basics/token")
    public Rsp<Oauth2TokenVO> getTokenByBasics(
            @Validated @RequestBody PasswordModelLoginDTO dto,
            @ApiIgnore HttpServletRequest request, @ApiIgnore HttpServletResponse response) {
        Object imgLoginCheckCodeObj = redisUtil.hget(AuthConstants.IMG_LOGIN_CHECK_KEY,dto.getDeviceId());
        if (Objects.isNull(imgLoginCheckCodeObj) || !String.valueOf(imgLoginCheckCodeObj).equals(dto.getCheckCode())) {
            return Rsp.error("验证码错误");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword());
        tokenEndpointService.createToken(request,response,token);
        return Rsp.ok();
    }

}
