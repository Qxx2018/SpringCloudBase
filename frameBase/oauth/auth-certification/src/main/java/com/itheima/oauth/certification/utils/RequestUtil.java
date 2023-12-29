package com.itheima.oauth.certification.utils;

import cn.hutool.core.util.StrUtil;
import com.itheima.sys.core.constants.NumConstant;
import com.itheima.sys.core.enums.SpecialCharEnum;
import com.itheima.sys.core.constants.RequestConstant;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author XinXingQian
 */
public class RequestUtil {
    /**
     * 从请求头中获取客户端信息client_id:client_secret
     * @param request
     * @throws UnapprovedClientAuthenticationException
     * @return
     */
    public static String[] clientDetails(HttpServletRequest request) {

        String basic = request.getHeader(RequestConstant.AUTHORIZATION);
        if (StrUtil.isBlank(basic) || !basic.startsWith(RequestConstant.BASIC)) {
            throw new UnapprovedClientAuthenticationException("请求头中客户端信息为空");
        }
        return clientDetails(basic);
    }

    /**
     * 从请求头中获取客户端信息client_id:client_secret
     * @param header
     * @return
     */
    public static String[] clientDetails(String header) {

        byte[] base64Token = header.substring(RequestConstant.BASIC.length()).getBytes(StandardCharsets.UTF_8);
        byte[] decoded;
        try {
            decoded = Base64.decodeBase64(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }
        String token = new String(decoded,StandardCharsets.UTF_8);
        int deLim = token.indexOf(SpecialCharEnum.COLON.getSpecialChar());
        if (deLim == NumConstant.Num_Neg_1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return token.split(SpecialCharEnum.COLON.getSpecialChar());
    }
}
