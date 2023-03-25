package com.itheima.common.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.itheima.sys.core.dto.jwt.JwtTokenDTO;
import com.itheima.sys.core.dto.jwt.PayLoad;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * Jwt工具
 * @author XinXingQian
 */
@Slf4j
public class JwtUtils {
    /**
     * 生成虚拟的JWT token
     * @return token字符串
     */
    public static String sign(JwtTokenDTO jwtTokenDto) {
        //加密密钥
        String tokenSecret = jwtTokenDto.getTokenSecret();
        //多少天过期
        Integer expirationTime = jwtTokenDto.getExpirationTime();
        //负载PayLoadDo
        PayLoad payLoadDo = jwtTokenDto.getPayLoad();
        //账户号
        String account = payLoadDo.getAccount();
        //登入密码
        String password = payLoadDo.getPassword();
        //角色编码
        List<String> roleCodeList = payLoadDo.getRoleCodeList();
        //资源权限
        List<String> resourceAuthorityCodeList = payLoadDo.getResourceAuthorityCodeList();
        //私钥和加密算法
        Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
        //设置过期时间
        Date date = DateUtil.offsetDay(DateUtil.date(),expirationTime);
        return JWT.create()
                .withClaim("account",account)
                .withClaim("password", password)
                .withArrayClaim("roleCode", roleCodeList.toArray(new String[roleCodeList.size()]))
                .withArrayClaim("resourceAuthorityCode",resourceAuthorityCodeList.toArray(new String[resourceAuthorityCodeList.size()]))
                .withExpiresAt(date)
                .sign(algorithm);
    }
    /**
     * 解析token
     * @param token token字符串
     * @return {@link PayLoad}
     */
    public static PayLoad analy(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            List<String> resourceAuthorityCodeList = jwt.getClaim("resourceAuthorityCode").asList(String.class);
            List<String> roleCodeList = jwt.getClaim("roleCode").asList(String.class);
            String account = jwt.getClaim("account").asString();
            String password = jwt.getClaim("password").asString();

            return PayLoad.builder()
                    .password(password)
                    .account(account)
                    .resourceAuthorityCodeList(resourceAuthorityCodeList)
                    .roleCodeList(roleCodeList).build();
        }
        catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return PayLoad.builder().build();
    }
    /**
     * 验证token
     * @param token 待验证的token
     * @param tokenSecret 加密密钥
     * @return true 通过; false 未通过
     */
    public static Boolean verify(String token, String tokenSecret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            //未验证通过会抛出异常
            verifier.verify(token);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("验证token未通过:"+e.toString());
        }
        return Boolean.FALSE;
    }

}
