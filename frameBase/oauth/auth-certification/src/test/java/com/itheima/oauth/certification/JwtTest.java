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
    /**
     * 私钥解密
     */
    @Test
    public void decryptByPrivateKey() throws Exception {

        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50SWQiOjE2NTE4Mzg0ODg4MDAyNTE5MDQsImF1ZCI6WyJhbGwiXSwiY2xpZW50SWQiOm51bGwsImxvZ2luVGltZSI6MTY4NDMxODgyNTMyMiwidXNlcl9uYW1lIjpudWxsLCJzY29wZSI6WyJhbGwiXSwidGVuYW50SWQiOm51bGwsImV4cCI6MTY4NDM5MDgyNSwiYXV0aG9yaXRpZXMiOlsic291cmNlX3VzZXJfY2VudGVyIl0sImp0aSI6ImFmNmRlMjVlLTIxODItNDhkZC1hZGIyLWVhNjBhMjcxYTc1YSIsImNsaWVudF9pZCI6IkQ0MjM3MTNCRjFBN0EifQ.FmB6zag2DEoeVNPt5NeOuLVhtNlhC9hGC4mj4eLDcZYYynChAESkcG842446Z7Ww7v1wGwX0-EmIEJv_UpA1dTuXDMCDC7IQHrkt5g_Vfb631A4eh9IKM_1oLrmCvNNYXqdn98hJeWanB14IH9zycxqkCG_KU3wlE9_M9_iADug";

    }
}
