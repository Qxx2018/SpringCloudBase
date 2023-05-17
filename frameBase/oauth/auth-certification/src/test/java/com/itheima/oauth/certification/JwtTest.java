package com.itheima.oauth.certification;


import com.itheima.common.utils.Rsa2Util;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;

@SpringBootTest(classes = {OauthCertificationApplication.class,JwtTest.class})
@RunWith(SpringRunner.class)
@Slf4j
public class JwtTest {
    /**
     * 从jws中获取密钥对
     */
    @Test
    public void keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "]\"#^a@l5dFy8XGi".toCharArray());
        KeyPair keyPair = factory.getKeyPair("jwt", "]\"#^a@l5dFy8XGi".toCharArray());
        String privateKey = Rsa2Util.getPrivateKey(keyPair);
        String publicKey = Rsa2Util.getPublicKey(keyPair);
        System.out.println(privateKey);
        System.out.println(publicKey);
    }
}
