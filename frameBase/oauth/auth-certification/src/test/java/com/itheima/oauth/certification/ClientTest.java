package com.itheima.oauth.certification;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {OauthCertificationApplication.class,ClientTest.class})
@RunWith(SpringRunner.class)
@Slf4j
public class ClientTest {
    @Test
    public void passwordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(
                BCryptPasswordEncoder.BCryptVersion.$2Y,
                16);
        String secret = passwordEncoder.encode("qrRgn8sMJXFXTAl7H0unRRYfRF1Vm10V");
        System.out.println(secret);
    }
}
