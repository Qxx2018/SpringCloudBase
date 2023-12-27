package com.itheima.oauth.certification.controller;


import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.itheima.common.constants.NumConstant;
import com.itheima.common.enums.BusinessExceptionEnums;
import com.itheima.common.utils.RedisUtil;
import com.itheima.common.vo.Rsp;
import com.itheima.oauth.certification.business.service.ImageValidateCodeService;
import com.itheima.oauth.certification.business.service.ValidateCodeService;
import com.itheima.oauth.certification.constants.AuthConstants;
import com.itheima.oauth.certification.dto.accredit.AuthorizationCodeModelLoginDTO;
import com.itheima.oauth.certification.dto.accredit.PasswordModelLoginDTO;
import com.itheima.oauth.certification.dto.accredit.SmsModelLoginDTO;
import com.itheima.oauth.certification.enums.AuthorizedGrantTypesEnum;
import com.itheima.oauth.certification.extension.authchannels.sms.SmsCodeAuthenticationToken;
import com.itheima.oauth.certification.utils.RequestUtil;
import com.itheima.oauth.certification.vo.Oauth2TokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
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
    @Resource
    private RedisUtil<String> redisUtil;
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

    /**本系统登入**/

    @PostMapping("/login/password")
    @ApiOperation(value = "OAuth2认证-账号密码登入")
    public Rsp<Oauth2TokenVO> passwordModelLogin(
            @ApiIgnore Principal principal,
            @ApiIgnore HttpServletRequest request,
            @RequestBody @Validated PasswordModelLoginDTO passwordModelLoginDTO
            ) throws HttpRequestMethodNotSupportedException {

        Object checkCodeObj = redisUtil.hget(AuthConstants.IMG_LOGIN_CHECK_KEY,passwordModelLoginDTO.getDeviceId());
        if (Objects.isNull(checkCodeObj) || !String.valueOf(checkCodeObj).equals(passwordModelLoginDTO.getCheckCode()) ) {
            return Rsp.error(BusinessExceptionEnums.IMG_CHECK_ERROR.getCode(),BusinessExceptionEnums.IMG_CHECK_ERROR.getMsg());
        }
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
        User user = new User(clientId,clientSecret,new ArrayList<>());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());

        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(token,parameters).getBody();
        Oauth2TokenVO oauth2Token = Oauth2TokenVO.builder().build();
        assert oAuth2AccessToken != null;
        oauth2Token.setExpiresIn(oAuth2AccessToken.getExpiresIn());
        oauth2Token.setRefreshToken(oAuth2AccessToken.getRefreshToken().getValue());
        oauth2Token.setToken(oAuth2AccessToken.getValue());
        return Rsp.ok(oauth2Token);


    }

    @PostMapping("/login/sms")
    @ApiOperation(value = "OAuth2认证-手机号登入")
    public Rsp<Oauth2TokenVO> smsModelLogin(
            @ApiIgnore Principal principal,
            @ApiIgnore HttpServletRequest request,
            @RequestBody @Validated SmsModelLoginDTO smsModelLoginDTO
    ) throws HttpRequestMethodNotSupportedException {
        //获取登入认证的客户端ID
        String[] headers =RequestUtil.clientDetails(request);
        String clientId = headers[NumConstant.NUM_0];
        String clientSecret = headers[NumConstant.NUM_1];
        log.info("OAuth认证授权 客户端ID:{}，请求参数：{}", clientId, JSONUtil.toJsonStr(smsModelLoginDTO));
        //校验客户端
        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(clientId);
        if (Objects.isNull(clientDetails)) {
            throw new UnapprovedClientAuthenticationException("客户端ID:"+clientId+"未查到");
        }
        if (!passwordEncoder.matches(clientSecret,clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("客户端的访问密钥不匹配");
        }
        Map<String,String> parameters = new HashMap<>();
        parameters.put("grant_type", AuthorizedGrantTypesEnum.SMS_CODE.getCode());
        parameters.put("client_id",clientId);
        parameters.put("client_secret",clientSecret);
        parameters.put("mobile",smsModelLoginDTO.getPhone());
        parameters.put("code",smsModelLoginDTO.getSmsCode());
        User user = new User(clientId,clientSecret,new ArrayList<>());
        SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken(user,null,new ArrayList<>());

        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(token,parameters).getBody();
        Oauth2TokenVO oauth2Token = Oauth2TokenVO.builder().build();
        assert oAuth2AccessToken != null;
        oauth2Token.setExpiresIn(oAuth2AccessToken.getExpiresIn());
        oauth2Token.setRefreshToken(oAuth2AccessToken.getRefreshToken().getValue());
        oauth2Token.setToken(oAuth2AccessToken.getValue());
        return Rsp.ok(oauth2Token);


    }
    /**访问第三方授权登入*/
    @GetMapping("/accessing/third/callback")
    @ApiOperation(value = "访问第三方授权回调")
    public Rsp<?> accessingThirdCallback(@ApiIgnore Principal principal,
                                         @ApiIgnore HttpServletRequest request,
                                         @RequestParam("code") String code) throws HttpRequestMethodNotSupportedException {

        //TODO 访问第三方oauth鉴权
        AuthorizationCodeModelLoginDTO codeModelLogin = new AuthorizationCodeModelLoginDTO();
        codeModelLogin.setCode(code);
        codeModelLogin.setRedirectUri(request.getRequestURL().toString());
        String result = HttpRequest
                .post("127.0.0.1:9803/oauth/login/authorizationCode")
                .body(JSONObject.toJSONString(codeModelLogin))
                .header("Authorization","Basic RDQyMzcxM0JGMUE3QTpxclJnbjhzTUpYRlhUQWw3SDB1blJSWWZSRjFWbTEwVg==")
                .execute().body();
        log.info("访问第三方授权回调RESULT:{}",result);
        return Rsp.ok();
    }

    /**第三方访问授权登入**/

    @PostMapping("/login/authorizationCode")
    @ApiOperation(value = "OAuth2认证-授权登入")
    public Rsp<Oauth2TokenVO> token(
            @ApiIgnore Principal principal,
            @ApiIgnore HttpServletRequest request,
            @RequestBody @Validated AuthorizationCodeModelLoginDTO authorizationCodeModelLoginDTO) throws HttpRequestMethodNotSupportedException {
        //获取登入认证的客户端ID
        String[] headers =RequestUtil.clientDetails(request);
        String clientId = headers[NumConstant.NUM_0];
        String clientSecret = headers[NumConstant.NUM_1];
        log.info("OAuth认证授权 客户端ID:{}，请求参数：{}", clientId, JSONUtil.toJsonStr(authorizationCodeModelLoginDTO));
        //校验客户端
        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(clientId);
        if (Objects.isNull(clientDetails)) {
            throw new UnapprovedClientAuthenticationException("客户端ID:"+clientId+"未查到");
        }
        if (!passwordEncoder.matches(clientSecret,clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("客户端的访问密钥不匹配");
        }
        Map<String,String> parameters = new HashMap<>();
        parameters.put("grant_type", AuthorizedGrantTypesEnum.AUTHORIZATION_CODE.getCode());
        parameters.put("client_id",clientId);
        parameters.put("client_secret",clientSecret);
        parameters.put("code",authorizationCodeModelLoginDTO.getCode());
        parameters.put("redirect_uri",authorizationCodeModelLoginDTO.getRedirectUri());
        User user = new User(clientId,clientSecret,new ArrayList<>());
        //PreAuthenticatedAuthenticationToken是一种Authentication实现，继承AbstractAuthenticationToken抽象类，用于预认证身份验证。
        //有些情况下，希望使用Spring Security进行授权，但是在访问应用程序之前，用户已经被某个外部系统可靠地验证过了，将这种情况称为预认证场景，
        //比如CSDN可以使用其他平台的账号进行登陆
        //————————————————
        PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(user,null,new ArrayList<>());
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(token,parameters).getBody();
        Oauth2TokenVO oauth2Token = Oauth2TokenVO.builder().build();
        assert oAuth2AccessToken != null;
        oauth2Token.setExpiresIn(oAuth2AccessToken.getExpiresIn());
        oauth2Token.setRefreshToken(oAuth2AccessToken.getRefreshToken().getValue());
        oauth2Token.setToken(oAuth2AccessToken.getValue());
        return Rsp.ok(oauth2Token);
    }





}
