package com.itheima.oauth.certification.controller;


import cn.hutool.json.JSONUtil;
import com.itheima.common.constants.NumConstant;
import com.itheima.common.vo.Rsp;
import com.itheima.oauth.certification.business.service.ImageValidateCodeService;
import com.itheima.oauth.certification.business.service.ValidateCodeService;
import com.itheima.oauth.certification.dto.accredit.PasswordModelLoginDTO;
import com.itheima.oauth.certification.enums.AuthorizedGrantTypesEnum;
import com.itheima.oauth.certification.utils.RequestUtil;
import com.itheima.oauth.certification.vo.Oauth2TokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    private PasswordEncoder passwordEncoder;
    @Resource
    private TokenEndpoint tokenEndpoint;
    @Resource
    private ValidateCodeService validateCodeService;
    @Resource
    private ImageValidateCodeService imageValidateCodeService;
    @Resource
    private JdbcClientDetailsService jdbcClientDetailsService;
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
    public Rsp<Oauth2TokenVO> passwordModelLogin(
            @ApiIgnore Principal principal,
            @ApiIgnore HttpServletRequest request,
            @RequestBody PasswordModelLoginDTO passwordModelLoginDTO
            ) throws HttpRequestMethodNotSupportedException {
        //获取登入认证的客户端ID
        String[] headers =RequestUtil.clientDetails(request);
        String clientId = headers[NumConstant.NUM_0];
        String clientSecret = headers[NumConstant.NUM_1];
        log.info("OAuth认证授权 客户端ID:{}，请求参数：{}", clientId, JSONUtil.toJsonStr(passwordModelLoginDTO));
        //校验客户端
        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(clientId);
        if (Objects.isNull(clientDetails)) {
            throw new UnapprovedClientAuthenticationException("客户端ID:"+clientId+"未查到");
        }
        if (!passwordEncoder.matches(clientSecret,clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("客户端的访问密钥不匹配");
        }
        Map<String,String> parameters = new HashMap<>();
        parameters.put("grant_type", AuthorizedGrantTypesEnum.PASSWORD.getCode());
        parameters.put("client_id",clientId);
        parameters.put("client_secret",clientSecret);
        parameters.put("username",passwordModelLoginDTO.getUsername());
        parameters.put("password",passwordModelLoginDTO.getPassword());
        try {
            OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal,parameters).getBody();
            Oauth2TokenVO oauth2Token = Oauth2TokenVO.builder().build();
            oauth2Token.setExpiresIn(oAuth2AccessToken.getExpiresIn());
            oauth2Token.setRefreshToken(oAuth2AccessToken.getRefreshToken().getValue());
            oauth2Token.setToken(oAuth2AccessToken.getValue());
            return Rsp.ok(oauth2Token);
        }
        catch (Exception e) {
            log.error(e.toString());
            throw new UnapprovedClientAuthenticationException("账号密码认证授权失败");
        }

    }

    /**作为第三方系统的OAuth**/
}
