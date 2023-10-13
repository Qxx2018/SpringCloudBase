package com.itheima.oauth.certification;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;

@SpringBootTest(classes = {OauthCertificationApplication.class,ClientTest.class})
@RunWith(SpringRunner.class)
@Slf4j
public class ClientTest {
    /**
     * client_secret加密
     */
    @Test
    public void passwordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(
                BCryptPasswordEncoder.BCryptVersion.$2Y,
                16);
        String secret = passwordEncoder.encode("qrRgn8sMJXFXTAl7H0unRRYfRF1Vm10V");
        //$2y$16$/6Xge2Cb21wGzqXzCmkoMuhRo8tRrJE305wLJToS1OyaEbA4sBq4u
        System.out.println(secret);
    }

    /**
     * 获取Oauth2客户端信息（client_id、client_secret）
     * Basic auth 生成
     * 放在请求头（Request Headers）中的Authorization字段，且经过加密，例如 Basic Y2xpZW50OnNlY3JldA== 明文等于 client:secret
     */
    @Test
    public void encodeBasicAuth() {
        String clientId = "D423713BF1A7A";
        String clientSecret = "qrRgn8sMJXFXTAl7H0unRRYfRF1Vm10V";
        byte[] b = (clientId+":"+clientSecret).getBytes(StandardCharsets.UTF_8);
        String basicString = new String(Base64.encodeBase64(b),StandardCharsets.UTF_8) ;
        //RDQyMzcxM0JGMUE3QTpxclJnbjhzTUpYRlhUQWw3SDB1blJSWWZSRjFWbTEwVg==
        System.out.println(basicString);
    }

    /**
     * 解析放在请求头（Request Headers）中的Authorization字段
     * 取出客户端信息（client_id、client_secret）
     */
    @Test
    public void decodeBasicAuth() {
        String basic = "Basic RDQyMzcxM0JGMUE3QTpxclJnbjhzTUpYRlhUQWw3SDB1blJSWWZSRjFWbTEwVg==";
        if (StrUtil.isNotBlank(basic) && basic.startsWith("Basic ")) {
            basic = basic.replace("Basic ","");
            String authString = new String(Base64.decodeBase64(basic.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
            //client:secret => D423713BF1A7A:qrRgn8sMJXFXTAl7H0unRRYfRF1Vm10V
            System.out.println(authString);
        }
    }
}
